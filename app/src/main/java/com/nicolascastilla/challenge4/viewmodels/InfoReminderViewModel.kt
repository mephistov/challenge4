package com.nicolascastilla.challenge4.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.usecases.interfaces.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfoReminderViewModel @Inject constructor(
    private val reminderUseCase: ReminderUseCase
): ViewModel() {

    val myData =  MutableLiveData<ReminderEntity> ()


    fun getRemainderById(id:Long){
        viewModelScope.launch {
            viewModelScope.runCatching {
                withContext(Dispatchers.IO) {
                    reminderUseCase.getReminderById(id)
                }

            }.onSuccess {
                it.status = "Interacted"
                myData.postValue(it)
                reminderUseCase.updateReminder(it)

            }.onFailure {
                Log.e("VIEWMODEL","error: ${it.message}")
            }
        }


    }
}