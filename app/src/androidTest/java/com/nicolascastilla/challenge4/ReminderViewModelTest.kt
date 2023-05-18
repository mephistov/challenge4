package com.nicolascastilla.challenge4

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nicolascastilla.challenge4.utils.ChallengeAlarmManager
import com.nicolascastilla.challenge4.viewmodels.ReminderViewModel
import com.nicolascastilla.domain.entities.AlarmSetup
import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.usecases.interfaces.ReminderUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ReminderViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var reminderUseCase: ReminderUseCase

    @Mock
    lateinit var alarmManager: ChallengeAlarmManager

    private lateinit var viewModel: ReminderViewModel

    @Before
    fun setUp() {
        viewModel = ReminderViewModel(reminderUseCase, alarmManager)
    }

    @Test
    fun getAllReminders_should_return_list_of_reminders() = runBlockingTest {
        // Given
        val reminders = listOf(
            ReminderEntity(1, "Reminder 1", "Description 1", "Type 1", 0.0, "12345.2", "Date 1"),
            ReminderEntity(2, "Reminder 2", "Description 2", "Type 2", 0.0, "12345.3", "Date 2")
        )
        given(reminderUseCase.getAllReminders()).willReturn(flowOf(reminders))

        // When
        viewModel.getAllRemainders()

        // Then
        viewModel.myItems.collect {
            assertThat(it).isEqualTo(reminders)
        }
    }

    @Test
    fun addReminder_should_add_reminder() = runBlockingTest {
        // Given
        val alarmSetup = AlarmSetup("Date", "Time")
        val reminder = ReminderEntity(
            id = 0,
            name = "Name",
            description = "Description",
            type = "Type",
            image = "Image",
            date = 12345.2,
            humaDate = "${alarmSetup.dateValue} ${alarmSetup.timeValue}"
        )
        given(reminderUseCase.addReminder(reminder)).willReturn(1)

        // When
        viewModel.addRemainder(alarmSetup)

        // Then
        verify(alarmManager).setRemainder(alarmSetup, reminder)
    }
}