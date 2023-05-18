package com.nicolascastilla.challenge4.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.nicolascastilla.domain.entities.ReminderEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntentBoradcastReciver:BroadcastReceiver() {


   // @Inject
    //lateinit var alarmManager: ChallengeAlarmManager TODO-> no funciona quien sabe por que :(

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val manager = ChallengeAlarmManager(context!!)

            val remandair = ReminderEntity(
                id = it.getLongExtra("ALARM_ID",0),
                name = if( it.getStringExtra("ALARM_NAME") != null) it.getStringExtra("ALARM_NAME")!! else "",
                description = if( it.getStringExtra("ALARM_DESC") != null) it.getStringExtra("ALARM_DESC")!! else "",
                date = 0.0,
                type = if( it.getStringExtra("ALARM_TYPE") != null) it.getStringExtra("ALARM_TYPE")!! else "",
                humaDate = if( it.getStringExtra("ALARM_TIME") != null) it.getStringExtra("ALARM_TIME")!! else "",
            )
            if (it.action == "5") {
                Toast.makeText(context, "Time 5 ", Toast.LENGTH_SHORT).show()
                manager.setRemainder(5,remandair)
            } else if (it.action == "10") {
                Toast.makeText(context, "Time 10 !!${it.getStringExtra("ALARM_NAME")}", Toast.LENGTH_SHORT).show()
                manager.setRemainder(10,remandair)
            }
            else if (it.action == "15") {
                Toast.makeText(context, "Tiem 15 !!${it.getStringExtra("ALARM_NAME")}", Toast.LENGTH_SHORT).show()
                manager.setRemainder(15,remandair)
            }
        }

    }
}