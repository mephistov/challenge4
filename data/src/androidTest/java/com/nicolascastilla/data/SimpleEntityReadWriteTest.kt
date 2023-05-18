package com.nicolascastilla.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nicolascastilla.data.local.ChallengeDataBase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.entities.ReminderModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest  {

    private lateinit var userDao: ChallengeDao
    private lateinit var db: ChallengeDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ChallengeDataBase::class.java).build()
        userDao = db.getChalengeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndRead() {
        val user = ReminderModel(
            id = 1,
            name = "unitTestName",
            description = "unit test description",
            type = "test tyupe",
            date = 0.0,
            image = "",
            huma_date = "dd/mm/yyy hh:mm",
            status = "test estatus"
        )
        runBlocking (Dispatchers.IO){
            val userId = userDao.insertReminder(user)
            val byName = userDao.getRemainderById(userId)
            assertEquals(byName,user)
        }



    }
  /*  @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.nicolascastilla.data.test", appContext.packageName)
    }*/
}