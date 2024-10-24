package com.joohnq.moodapp.viewmodel

import com.joohnq.moodapp.model.dao.StatsRecordDAO
import com.joohnq.moodapp.model.entities.Mood
import com.joohnq.moodapp.model.entities.StatsRecord
import com.joohnq.moodapp.model.entities.SleepQuality
import com.joohnq.moodapp.model.entities.StressLevel
import com.joohnq.moodapp.view.state.UiState
import com.varabyte.truthish.assertThat
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoodsViewModelTest {
    private lateinit var moodsViewModel: MoodsViewModel
    private lateinit var statsRecordDAO: StatsRecordDAO
    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        statsRecordDAO = mock()
        moodsViewModel = MoodsViewModel(statsRecordDAO, testDispatcher)
    }


    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    /**
     * Test SetCurrentMood
     * test if the parameter value mood has been added to _currentMood successfully
     */
    @Test
    fun testSetCurrentMood() {
        // Given
        val mood: Mood = Mood.Sad

        // When
        moodsViewModel.setCurrentMood(mood)

        val currentMood = moodsViewModel.currentMood.value

        // Then
        assertThat(currentMood?.mood).isNotNull()
        assertThat(currentMood?.mood).isEqualTo(mood)
    }

    /**
     * Test SetCurrentMoodSleepQuality
     * test if the parameter value sleep quality has been added to _currentMood successfully
     */
    @Test
    fun setCurrentMoodSleepQuality() {
        // Given
        val sleepQuality: SleepQuality = SleepQuality.Fair

        // When
        moodsViewModel.setCurrentMoodSleepQuality(sleepQuality)

        val currentMood = moodsViewModel.currentMood.value

        // Then
        assertThat(currentMood?.sleepQuality).isNotNull()
        assertThat(currentMood?.sleepQuality).isEqualTo(sleepQuality)
    }

    /**
     * Test SetCurrentMoodDescription
     * test if the parameter value description has been added to _currentMood successfully
     */
    @Test
    fun setCurrentMoodDescription() {
        // Given
        val description = "Lorem Description"

        // When
        moodsViewModel.setCurrentMoodDescription(description)

        val currentMood = moodsViewModel.currentMood.value

        // Then
        assertThat(currentMood?.description).isNotNull()
        assertThat(currentMood?.description).isEqualTo(description)
    }

    /**
     * Test SetCurrentMoodStressLevel
     * test if the parameter value stress level has been added to _currentMood successfully
     */
    @Test
    fun setCurrentMoodStressLevel() {
        // Given
        val stressLevel: StressLevel = StressLevel.Three

        // When
        moodsViewModel.setCurrentMoodStressLevel(stressLevel)

        val currentMood = moodsViewModel.currentMood.value

        // Then
        assertThat(currentMood?.stressLevel).isNotNull()
        assertThat(currentMood?.stressLevel).isEqualTo(stressLevel)
    }

    /**
     * Test InsertCurrentMood
     * test insertCurrentMood with a not null mood in moodDb, should return true
     */
    @Test
    fun `test insertCurrentMood with a not null mood should return true`() = runTest {
        // Given
        val statsRecord = StatsRecord(mood = Mood.Happy)
        statsRecordDAO = mock<StatsRecordDAO> {
            everySuspend { insertMood(any()) } returns Unit
        }
        moodsViewModel = MoodsViewModel(statsRecordDAO, testDispatcher)
        moodsViewModel.setCurrentMoodForTesting(statsRecord)

        // When
        val res = moodsViewModel.insertCurrentMood()

        // Then
        assertThat(moodsViewModel.currentMood.value?.mood).isNotNull()
        assertThat(res).isEqualTo(true)

        verifySuspend { statsRecordDAO.insertMood(statsRecord) }
    }

    /**
     * Test InsertCurrentMood
     * test insertCurrentMood with a null current mood, should return false
     */
    @Test
    fun `test insertCurrentMood with a null mood should return false`() = runTest {
        // Given
        statsRecordDAO = mock<StatsRecordDAO> {
            everySuspend { insertMood(any()) } returns Unit
        }
        moodsViewModel = MoodsViewModel(statsRecordDAO, testDispatcher)

        // When
        val res = moodsViewModel.insertCurrentMood()

        // Then
        assertThat(moodsViewModel.currentMood.value).isNull()
        assertThat(res).isFalse()
    }

    /**
     * Test GetMoods
     * test GetMoods with a valid return value, so the mood value should be updated in each stage, Idle, Loading, Success
     */
    @Test
    fun `test getMoods with a success list response the mood value should be Idle - Loading so Success`() =
        runTest {
            // Given
            val mockMoods = listOf(
                StatsRecord(mood = Mood.Sad),
                StatsRecord(mood = Mood.Happy)
            )
            statsRecordDAO = mock<StatsRecordDAO> {
                everySuspend { getMoods() } returns flowOf(mockMoods)
            }

            moodsViewModel = MoodsViewModel(
                statsRecordDAO,
                testDispatcher
            )

            //When
            val states = mutableListOf<UiState<List<StatsRecord>>>()
            val job = launch(UnconfinedTestDispatcher(testScheduler)) {
                moodsViewModel.moods.toList(states)
            }

            moodsViewModel.getMoods()

            testScheduler.advanceUntilIdle()

            // Then
            assertThat(states.size).isEqualTo(3)
            assertThat(states[0]).isEqualTo(UiState.Idle)
            assertThat(states[1]).isEqualTo(UiState.Loading)
            assertThat(states[2]).isEqualTo(UiState.Success(mockMoods))

            verifySuspend { statsRecordDAO.getMoods() }

            job.cancel()
        }

    /**
     * Test GetMoods
     * test GetMoods with a invalid return value, so the mood value should be updated in each stage, Idle, Loading, Failure
     */
    @Test
    fun `test getMoods with a failure response the mood value should be Idle - Loading so Failure`() =
        runTest {
            // Given
            val exception = "Something went wrong"
            statsRecordDAO = mock<StatsRecordDAO> {
                everySuspend { getMoods() } returns flow { throw Exception(exception) }
            }

            moodsViewModel = MoodsViewModel(
                statsRecordDAO,
                testDispatcher
            )

            // When
            val states = mutableListOf<UiState<List<StatsRecord>>>()
            val job = launch(UnconfinedTestDispatcher(testScheduler)) {
                moodsViewModel.moods.toList(states)
            }

            moodsViewModel.getMoods()

            testScheduler.advanceUntilIdle()

            // Then
            assertThat(states.size).isEqualTo(3)
            assertThat(states[0]).isEqualTo(UiState.Idle)
            assertThat(states[1]).isEqualTo(UiState.Loading)
            assertThat(states[2]).isEqualTo(UiState.Error(exception))
            assertThat((states[2] as UiState.Error).message).isEqualTo(exception)

            verifySuspend { statsRecordDAO.getMoods() }

            job.cancel()
        }

    /**
     * Test ResetCurrentMood
     * test if the currentMood value has been reset to null
     */
    @Test
    fun resetCurrentMood() {
        // Given
        moodsViewModel.resetCurrentMood()

        // When
        val currentMood = moodsViewModel.currentMood.value

        // Then
        assertThat(currentMood?.description).isNull()
    }
}
