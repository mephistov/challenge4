package com.nicolascastilla.challenge4.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.nicolascastilla.domain.entities.AlarmSetup
import com.nicolascastilla.domain.entities.ReminderEntity
import java.util.*
import javax.inject.Inject


class ChallengeAlarmManager @Inject constructor(
    private val context: Context,
) {

    fun setRemainder(alarmData: AlarmSetup, remander: ReminderEntity){
        val intent = Intent().apply {
            setClass(context,OnAlarmReciver::class.java)
            putExtra("ALARM_ID",remander.id)
            putExtra("ALARM_NAME",remander.name)
            putExtra("ALARM_DESC",remander.description)
            putExtra("ALARM_TYPE",remander.type)
            putExtra("ALARM_TIME",alarmData.dateValue+" "+alarmData.timeValue)
            putExtra("POSPONED",false)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            remander.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val myAlarmDate: Calendar = Calendar.getInstance()
        myAlarmDate.setTimeInMillis(System.currentTimeMillis())
        myAlarmDate.set(alarmData.year, alarmData.month-1, alarmData.day, alarmData.hour, alarmData.min, 0)
        Log.e("MANAGER", "AlarmX set in : " + myAlarmDate.getTime().toString());

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            myAlarmDate.getTimeInMillis(),
            pendingIntent
        )

    }
    fun setRemainder(timePosponed:Int, remander: ReminderEntity){

        val intent = Intent().apply {
            setClass(context,OnAlarmReciver::class.java)
            putExtra("ALARM_ID",remander.id)
            putExtra("ALARM_NAME",remander.name)
            putExtra("ALARM_DESC",remander.description)
            putExtra("ALARM_TYPE",remander.type)
            putExtra("ALARM_TIME",remander.humaDate)
            putExtra("POSPONED",true)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            remander.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val myAlarmDate: Calendar = Calendar.getInstance()
        myAlarmDate.setTimeInMillis(System.currentTimeMillis()+(timePosponed*60000))

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            myAlarmDate.getTimeInMillis(),
            pendingIntent
        )
    }
}