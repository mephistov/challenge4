package com.nicolascastilla.domain.usecases

import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.repositories.ReminderRepository
import com.nicolascastilla.domain.usecases.interfaces.ReminderUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderUseCaseImpl @Inject constructor(
    private val repository: ReminderRepository
) : ReminderUseCase {

    override suspend fun addReminder(reminder:ReminderEntity):Long {
        return repository.addRemander(reminder)
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> {
        return repository.getAllReminders()
    }

    override suspend fun getReminderById(id: Long): ReminderEntity {
        return repository.getRemanderById(id)
    }

    override suspend fun updateReminder(reminder: ReminderEntity) {
        repository.updateStatus(reminder)
    }
}