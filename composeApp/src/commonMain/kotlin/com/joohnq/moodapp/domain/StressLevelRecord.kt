package com.joohnq.moodapp.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.joohnq.moodapp.util.helper.DatetimeManager
import com.joohnq.moodapp.data.DatabaseConstants
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Entity(tableName = DatabaseConstants.STRESS_LEVEL_RECORD_DATABASE)
@Serializable
data class StressLevelRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = DatabaseConstants.STRESS_LEVEL)
    val stressLevel: StressLevel,
    val stressors: List<Stressor>,
    val date: LocalDateTime
) {
    companion object {
        fun init(): StressLevelRecord = StressLevelRecord(
            id = 0,
            stressLevel = StressLevel.Three,
            stressors = emptyList(),
            date = DatetimeManager.getCurrentDateTime()
        )

        fun StressLevel.toStressLevelRecord(): StressLevelRecord =
            StressLevelRecord(
                id = 0,
                stressLevel = this,
                stressors = emptyList(),
                date = DatetimeManager.getCurrentDateTime()
            )
    }
}