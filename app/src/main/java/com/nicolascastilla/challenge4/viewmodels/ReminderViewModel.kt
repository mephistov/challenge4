package com.nicolascastilla.challenge4.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.challenge4.utils.ChallengeAlarmManager
import com.nicolascastilla.domain.entities.AlarmSetup
import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.usecases.interfaces.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderUseCase: ReminderUseCase,
    private val alarmManager: ChallengeAlarmManager
    ): ViewModel() {
    val myItems: Flow<List<ReminderEntity>> = reminderUseCase.getAllReminders()

    private val _textName = MutableStateFlow("")
    val textName: StateFlow<String> = _textName.asStateFlow()

    private val _textDescription = MutableStateFlow("")
    val textDescription: StateFlow<String> = _textDescription.asStateFlow()

    private val _type = MutableStateFlow("Professional")
    val type: StateFlow<String> = _type.asStateFlow()

    fun getAllRemainders(){
        viewModelScope.launch {
            viewModelScope.runCatching {
                reminderUseCase.getAllReminders()
            }.onSuccess {

            }
        }

    }

    fun onTextChanged(newText: String) {
        _textName.value = newText
    }

    fun onDescriptionChanged(newText: String) {
        _textDescription.value = newText
    }
    fun onTypeChanged(newText: String){
        _type.value = newText
    }
    fun addRemainder(alarmSetup: AlarmSetup){
        viewModelScope.launch {
            viewModelScope.runCatching {
                val remander = ReminderEntity(
                    id = 0,
                    name = _textName.value,
                    description = _textDescription.value,
                    type = "to asign",
                    image = "to assing",
                    date = 12345.2,
                    humaDate = alarmSetup.dateValue+" "+alarmSetup.timeValue
                )
                val idRem = reminderUseCase.addReminder(remander)
                remander.id = idRem
                alarmManager.setRemainder(alarmSetup,remander)
            }.onSuccess {
                Log.e("TEST"," SUCCES: ${it.toString()}")
            }.onFailure {
                Log.e("TEST"," FAILURE: ${it.message}")
            }
        }

    }



}