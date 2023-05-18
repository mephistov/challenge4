package com.nicolascastilla.data

import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.mapper.toEntities
import com.nicolascastilla.data.local.mapper.toEntity
import com.nicolascastilla.data.local.mapper.toModel
import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.repositories.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val dataBase: ChallengeDao
): ReminderRepository {
    override suspend fun addRemander(reminderEntity: ReminderEntity):Long {
        return dataBase.insertReminder(reminderEntity.toModel())
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> {
       return  dataBase.getAllReminders().map { remaderModel ->
           remaderModel.toEntities()
       }
    }

    override suspend fun removeRemander(remainderData: ReminderEntity?) {
        if (remainderData != null) {
            dataBase.delete(remainderData.toModel())
        }
    }

    override suspend fun getRemanderById(id: Long): ReminderEntity {
        return dataBase.getRemainderById(id).toEntity()
    }

    override suspend fun updateStatus(remainderData: ReminderEntity?) {
        remainderData?.let { dataBase.updateRemainder(it.toModel()) }
    }
}