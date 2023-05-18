package com.nicolascastilla.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.nicolascastilla.data.local.ChallengeDataBase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.domain.entities.ReminderEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReminderRepositoryImplTest {
    private lateinit var database: ChallengeDataBase
    private lateinit var dao: ChallengeDao
    private lateinit var repository: ReminderRepositoryImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ChallengeDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.getChalengeDao()
        repository = ReminderRepositoryImpl(dao)
    }

    @After
    fun tearDown() {
        database.close()
    }
    @Test
    fun addRemander() = runBlocking {
        val reminderEntity = ReminderEntity(1, "Title", "Description", "Date")
        val id = repository.addRemander(reminderEntity)
        assertEquals(1, id)
    }

    @Test
    fun getAllRemainders() = runBlocking {
        val reminderEntity1 = ReminderEntity(1, "Title1", "Description1", "Date1")
        val reminderEntity2 = ReminderEntity(2, "Title2", "Description2", "Date2")
        repository.addRemander(reminderEntity1)
        repository.addRemander(reminderEntity2)

        val remainders = repository.getAllReminders().first()
        assertEquals(listOf(reminderEntity1, reminderEntity2), remainders)
    }

    @Test
    fun removeRemander() = runBlocking {
        val reminderEntity = ReminderEntity(1, "Title", "Description", "Date")
        repository.addRemander(reminderEntity)

        repository.removeRemander(reminderEntity)

        val remainders = repository.getAllReminders().first()
        assertEquals(emptyList<ReminderEntity>(), remainders)
    }

    @Test
    fun getRemanderById() = runBlocking {
        val reminderEntity = ReminderEntity(1, "Title", "Description", "Date")
        repository.addRemander(reminderEntity)

        val remainderById = repository.getRemanderById(1)

        assertEquals(remainderById, reminderEntity)
    }

    @Test
    fun updateStatus() = runBlocking {
        val reminderEntity = ReminderEntity(1, "Title", "Description", "Date")
        repository.addRemander(reminderEntity)

        val updatedRemainder = ReminderEntity(1, "Title", "Description", "New Date")
        repository.updateStatus(updatedRemainder)

        val remainderById = repository.getRemanderById(1)

        assertEquals(updatedRemainder, remainderById)
    }
}