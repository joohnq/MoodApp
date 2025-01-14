package com.joohnq.health_journal.domain.use_case

import com.joohnq.core.ui.DatetimeProvider
import com.joohnq.health_journal.domain.entity.HealthJournalRecord
import com.joohnq.mood.domain.entity.Mood
import com.varabyte.truthish.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class OrganizeByDateHealthJournalUseCaseTest {
    companion object {
        private val lastYear = LocalDateTime(2024, 1, 10, 0, 0, 0)
        private val standard = LocalDateTime(2025, 1, 10, 0, 0, 0)
        private val today = LocalDateTime(2025, 1, 10, 0, 0, 0)
        private val yesterday = LocalDateTime(2025, 1, 9, 0, 0, 0)
        val items = listOf(
            HealthJournalRecord(
                mood = Mood.Overjoyed,
                date = today
            ),
            HealthJournalRecord(
                mood = Mood.Happy,
                date = today
            ),
            HealthJournalRecord(
                mood = Mood.Neutral,
                date = today
            ),
            HealthJournalRecord(
                mood = Mood.Sad,
                date = yesterday
            ),
            HealthJournalRecord(
                mood = Mood.Sad,
                date = yesterday
            ),
            HealthJournalRecord(
                mood = Mood.Sad,
                date = lastYear
            ),
        )
    }

    private lateinit var useCase: OrganizeByDateHealthJournalUseCase

    @BeforeTest
    fun setUp() {
        useCase = OrganizeByDateHealthJournalUseCase(DatetimeProvider)
    }

    @Test
    fun `test OrganizeByDateHealthJournalUseCase`() =
        runBlocking {
            val res = useCase.invoke(healthJournals = items)
            assertThat(res).isEqualTo(
                mapOf(
                    today to listOf(
                        HealthJournalRecord(
                            mood = Mood.Overjoyed,
                            date = today
                        ),
                        HealthJournalRecord(
                            mood = Mood.Happy,
                            date = today
                        ),
                        HealthJournalRecord(
                            mood = Mood.Neutral,
                            date = today
                        ),
                    ),
                    yesterday to listOf(
                        HealthJournalRecord(
                            mood = Mood.Sad,
                            date = yesterday
                        ),
                        HealthJournalRecord(
                            mood = Mood.Sad,
                            date = yesterday
                        ),
                    ),
                    lastYear to listOf(
                        HealthJournalRecord(
                            mood = Mood.Sad,
                            date = lastYear
                        ),
                    ),
                )
            )
        }
}