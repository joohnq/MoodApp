package com.joohnq.moodapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joohnq.moodapp.data.DatabaseConstants
import com.joohnq.moodapp.domain.MedicationsSupplements
import com.joohnq.moodapp.domain.PhysicalSymptoms
import com.joohnq.moodapp.domain.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM ${DatabaseConstants.USER_DATABASE} WHERE id = 1")
    suspend fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initUser(
        user: User = User.init(),
    )

    @Query("UPDATE ${DatabaseConstants.USER_DATABASE} SET name = :name WHERE id = 1")
    suspend fun updateUserName(name: String)

    @Query("UPDATE ${DatabaseConstants.USER_DATABASE} SET ${DatabaseConstants.SOUGHT_HELP} = :soughtHelp WHERE id = 1")
    suspend fun updateSoughtHelp(soughtHelp: Boolean)

    @Query("UPDATE ${DatabaseConstants.USER_DATABASE} SET ${DatabaseConstants.PHYSICAL_SYMPTOMS} = :physicalSymptoms WHERE id = 1")
    suspend fun updatePhysicalSymptoms(physicalSymptoms: PhysicalSymptoms)

    @Query("UPDATE ${DatabaseConstants.USER_DATABASE} SET ${DatabaseConstants.MEDICATIONS_SUPPLEMENTS} = :medicationsSupplements WHERE id = 1")
    suspend fun updateMedicationsSupplements(medicationsSupplements: MedicationsSupplements)
}