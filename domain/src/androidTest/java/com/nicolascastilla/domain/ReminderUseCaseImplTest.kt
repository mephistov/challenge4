package com.nicolascastilla.domain

import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.repositories.ReminderRepository
import com.nicolascastilla.domain.usecases.ReminderUseCaseImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Strict::class)
class ReminderUseCaseImplTest {
    @Mock
    lateinit var repository: ReminderRepository

    lateinit var remainderUseCaseImpl: ReminderUseCaseImpl

    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)
        remainderUseCaseImpl = ReminderUseCaseImpl(repository)
    }

    @Test
    fun addRemander_should_return_correct_id() = runBlocking {
        val reminderEntity = ReminderEntity(1, "title", "description", "date")
        val id = 1L

        `when`(repository.addRemander(reminderEntity)).thenReturn(id)

        assertEquals(id, remainderUseCaseImpl.addReminder(reminderEntity))
    }

    @Test
    fun getAllRemainders_should_return_correct_list() = runBlocking {
        val reminderEntityLists = listOf(
            ReminderEntity(1, "title1", "description1", "date1"),
            ReminderEntity(2, "title2", "description2", "date2")
        )

        `when`(repository.getAllReminders()).thenReturn(flowOf(reminderEntityLists))

        assertEquals(reminderEntityLists, remainderUseCaseImpl.getAllReminders().first())
    }

    @Test
    fun getRemainderById_should_return_correct_entity() = runBlocking {
        val reminderEntity = ReminderEntity(1, "title", "description", "date")
        val id = 1L

        `when`(repository.getRemanderById(id)).thenReturn(reminderEntity)

        assertEquals(reminderEntity, remainderUseCaseImpl.getReminderById(id))
    }

    @Test
    fun updateRemainder_should_call_repository_updateStatus() = runBlocking {
        val reminderEntity = ReminderEntity(1, "title", "description", "date")

        remainderUseCaseImpl.updateReminder(reminderEntity)

        // Verify that the repository's updateStatus method was called with the correct parameter.
        verify(repository).updateStatus(reminderEntity)
    }
}