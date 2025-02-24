package com.joohnq.sleep_quality.data.repository

import com.joohnq.database.SqliteOperationResult
import com.joohnq.database.converters.LocalDateTimeConverter
import com.joohnq.database.executeTryCatchResult
import com.joohnq.database.sqliteExceptionMapper
import com.joohnq.domain.mapper.toFormattedTimeString
import com.joohnq.domain.mapper.toTime
import com.joohnq.sleep_quality.database.SleepQualityDatabaseSql
import com.joohnq.sleep_quality.domain.converter.SleepQualityRecordConverter
import com.joohnq.sleep_quality.domain.entity.SleepQualityRecord
import com.joohnq.sleep_quality.domain.exception.SleepQualityException
import com.joohnq.sleep_quality.domain.repository.SleepQualityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

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
                    startSleeping = startSleeping.toTime(),
                    endSleeping = endSleeping.toTime(),
                    sleepInfluences = SleepQualityRecordConverter.toInfluences(sleepInfluences),
                    createdAt = LocalDateTimeConverter.toLocalDate(createdAt)
                )
            }.executeAsList()
        }

    override suspend fun addSleepQuality(
        record: SleepQualityRecord,
    ): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                query.addSleepQuality(
                    sleepQuality = SleepQualityRecordConverter.fromSleepQuality(record.sleepQuality),
                    startSleeping = record.startSleeping.toFormattedTimeString(),
                    endSleeping = record.endSleeping.toFormattedTimeString(),
                    sleepInfluencess = SleepQualityRecordConverter.fromInfluences(record.sleepInfluences)
                )
                Result.success(true)
            } catch (e: Exception) {
                val res = sqliteExceptionMapper.map(e)
                when (res.opResult) {
                    SqliteOperationResult.CONSTRAINT -> Result.failure(SleepQualityException.AlreadyBeenAddedToday)
                    else -> Result.failure(Exception(res.cause))
                }
            }
        }

    override suspend fun deleteSleepQuality(id: Int): Result<Boolean> =
        executeTryCatchResult {
            query.deleteSleepQuality(id = id.toLong())
            true
        }
}