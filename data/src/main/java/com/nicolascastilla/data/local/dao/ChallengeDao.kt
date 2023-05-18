package com.nicolascastilla.data.local.dao
import androidx.room.*
import com.nicolascastilla.data.local.entities.ReminderModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(remainder: ReminderModel):Long

    @Delete
    suspend fun delete(remainder: ReminderModel)

    @Query("SELECT * FROM remainder_table")
    fun getAllReminders(): Flow<List<ReminderModel>>


    @Query("SELECT * FROM remainder_table WHERE id = :id")
    fun getRemainderById(id:Long): ReminderModel

    @Update
    suspend fun updateRemainder(remainder: ReminderModel)

}