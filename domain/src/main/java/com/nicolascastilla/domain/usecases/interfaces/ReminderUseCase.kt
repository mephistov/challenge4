package com.nicolascastilla.domain.usecases.interfaces

import com.nicolascastilla.domain.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderUseCase {

    suspend fun addReminder(reminder: ReminderEntity):Long

    fun getAllReminders():Flow<List<ReminderEntity>>
    suspend fun getReminderById(id: Long):ReminderEntity

    suspend fun updateReminder(reminder: ReminderEntity)
}