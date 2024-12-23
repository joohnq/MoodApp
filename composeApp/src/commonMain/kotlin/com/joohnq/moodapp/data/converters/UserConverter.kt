package com.joohnq.moodapp.data.converters

import androidx.room.TypeConverter
import com.joohnq.moodapp.domain.MedicationsSupplements
import com.joohnq.moodapp.domain.PhysicalSymptoms
import com.joohnq.moodapp.domain.ProfessionalHelp

class UserConverter {
    @TypeConverter
    fun fromMedicationsSupplements(value: MedicationsSupplements): Int =
        MedicationsSupplements.fromValue(value)

    @TypeConverter
    fun toMedicationsSupplements(value: Int): MedicationsSupplements =
        MedicationsSupplements.toValue(value)

    @TypeConverter
    fun fromPhysicalSymptoms(value: PhysicalSymptoms): Int = PhysicalSymptoms.fromValue(value)

    @TypeConverter
    fun toPhysicalSymptoms(value: Int): PhysicalSymptoms = PhysicalSymptoms.toValue(value)

    @TypeConverter
    fun fromProfessionalHelp(value: ProfessionalHelp): Int = ProfessionalHelp.fromValue(value)

    @TypeConverter
    fun toProfessionalHelp(value: Int): ProfessionalHelp = ProfessionalHelp.toValue(value)
}