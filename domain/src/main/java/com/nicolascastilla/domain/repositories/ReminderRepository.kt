package com.nicolascastilla.domain.repositories

import com.nicolascastilla.domain.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun addRemander(reminderEntity: ReminderEntity):Long
    fun getAllReminders(): Flow<List<ReminderEntity>>
    suspend fun removeRemander(remainderData: ReminderEntity?)
    suspend fun getRemanderById(id: Long): ReminderEntity
    suspend fun updateStatus(remainderData: ReminderEntity?)
}