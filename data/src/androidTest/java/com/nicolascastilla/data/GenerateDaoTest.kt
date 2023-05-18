package com.nicolascastilla.data
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nicolascastilla.data.local.ChallengeDataBase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.entities.ReminderModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GenerateDaoTest {

    private lateinit var dao: ChallengeDao
    private lateinit var db: ChallengeDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ChallengeDataBase::class.java).build()
        dao = db.getChalengeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertRemainder() = runBlocking {
        val remainder = ReminderModel(
            id = 1,
            name = "unitTestName",
            description = "unit test description",
            type = "test tyupe",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm",
            status = "test estatus"
        )
        dao.insertReminder(remainder)
        val remainders = dao.getAllReminders().first()
        assertEquals(1, remainders.size)
        assertEquals(remainder.name, remainders[0].name)
        assertEquals(remainder.description, remainders[0].description)
    }

    @Test
    fun delete() = runBlocking {
        val remainder = ReminderModel(
            id = 1,
            name = "unitTestName",
            description = "unit test description",
            type = "test tyupe",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm",
            status = "test estatus"
        )
        dao.insertReminder(remainder)
        dao.delete(remainder)
        val remainders = dao.getAllReminders().first()
        assertEquals(0, remainders.size)
    }

    @Test
    fun getAllRemainders() = runBlocking {
        val remainder1 = ReminderModel(
            id = 1,
            name = "unitTestName",
            description = "unit test description",
            type = "test tyupe",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm",
            status = "test estatus"
        )
        val remainder2 = ReminderModel(
            id = 2,
            name = "unitTestName2",
            description = "unit test description2",
            type = "test tyupe2",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm2",
            status = "test estatus2"
        )
        dao.insertReminder(remainder1)
        dao.insertReminder(remainder2)
        val remainders = dao.getAllReminders().first()
        assertEquals(2, remainders.size)
        assertEquals(remainder1.name, remainders[0].name)
        assertEquals(remainder1.description, remainders[0].description)
        assertEquals(remainder2.name, remainders[1].name)
        assertEquals(remainder2.description, remainders[1].description)
    }

    @Test
    fun getRemainderById() = runBlocking {
        val remainder1 = ReminderModel(
            id = 1,
            name = "unitTestName",
            description = "unit test description",
            type = "test tyupe",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm",
            status = "test estatus"
        )
        val remainder2 = ReminderModel(
            id = 2,
            name = "unitTestName2",
            description = "unit test description2",
            type = "test tyupe2",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm2",
            status = "test estatus2"
        )
        dao.insertReminder(remainder1)
        dao.insertReminder(remainder2)
        val result = dao.getRemainderById(remainder2.id)
        assertEquals(result.name, remainder2.name)
        assertEquals(result.description, remainder2.description)
    }

    @Test
    fun updateRemainder() = runBlocking {
        val remainder = ReminderModel(
            id = 1,
            name = "unitTestName",
            description = "unit test description",
            type = "test tyupe",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm",
            status = "test estatus"
        )
        dao.insertReminder(remainder)

        val updatedRemainder =
            ReminderModel(
                remainder.id,
                name = "unitTestName Updates",
                description = "unit test descriptionUpdates",
                type = "test tyupeUpdates",
                date = 0.0,
                image = "",
                huma_date = "dd/mm/yyy hh:mmUpdates",
                status = "test estatusUpdates"
            )
        dao.updateRemainder(updatedRemainder)

        val result = dao.getRemainderById(updatedRemainder.id)

        assertEquals(result.name, updatedRemainder.name)
        assertEquals(result.description, updatedRemainder.description)

    }

}