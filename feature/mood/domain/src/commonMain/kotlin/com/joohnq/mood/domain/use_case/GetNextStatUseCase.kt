package com.joohnq.mood.domain.use_case

import com.joohnq.mood.domain.entity.StatsRecord
import org.koin.core.annotation.Factory

@Factory
class GetNextStatUseCase {
    operator fun invoke(statsRecord: StatsRecord, statsRecords: List<StatsRecord>): StatsRecord? =
        statsRecords.filter { it.date > statsRecord.date }
            .minByOrNull { it.date }
}