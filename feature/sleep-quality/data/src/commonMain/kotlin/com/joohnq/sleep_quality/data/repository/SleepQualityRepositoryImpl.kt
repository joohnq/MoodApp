package com.joohnq.sleep_quality.data.repository

import com.joohnq.core.database.converters.LocalDateTimeConverter
import com.joohnq.core.database.executeTryCatchResult
import com.joohnq.sleep_quality.database.SleepQualityDatabaseSql
import com.joohnq.sleep_quality.domain.converter.SleepQualityRecordConverter
import com.joohnq.sleep_quality.domain.entity.SleepQualityRecord
import com.joohnq.sleep_quality.domain.repository.SleepQualityRepository

class SleepQualityRepositoryImpl(
    private val database: SleepQualityDatabaseSql,
) : SleepQualityRepository {
    private val query = database.sleepQualityRecordQueries
    override suspend fun getSleepQualities(): Result<List<SleepQualityRecord>> =
        executeTryCatchResult {
            query.getSleepQualities { id, sleepQuality, startSleeping, endSleeping, sleepInfluences, createdAt ->
                SleepQualityRecord(
                    id = id.toInt(),
                    sleepQuality = SleepQualityRecordConverter.toSleepQuality(sleepQuality),
                    startSleeping = startSleeping,
                    endSleeping = endSleeping,
                    sleepInfluences = SleepQualityRecordConverter.toInfluences(sleepInfluences),
                    createdAt = LocalDateTimeConverter.toLocalDateTime(createdAt)
                )
            }.executeAsList()
        }

    override suspend fun addSleepQuality(
        sleepQualityRecord: SleepQualityRecord,
    ): Result<Boolean> =
        executeTryCatchResult {
            query.addSleepQuality(
                sleepQuality = SleepQualityRecordConverter.fromSleepQuality(sleepQualityRecord.sleepQuality),
                startSleeping = sleepQualityRecord.startSleeping,
                endSleeping = sleepQualityRecord.endSleeping,
                sleepInfluencess = SleepQualityRecordConverter.fromInfluences(sleepQualityRecord.sleepInfluences)
            )
            true
        }
}