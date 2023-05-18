package com.nicolascastilla.challenge4.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nicolascastilla.challenge4.R
import com.nicolascastilla.challenge4.views.activities.ReminderInfoActivity
import com.nicolascastilla.domain.entities.ReminderEntity
import com.nicolascastilla.domain.repositories.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class OnAlarmReciver: BroadcastReceiver() {

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0
    var remainderData : ReminderEntity = ReminderEntity()

    @Inject
    lateinit var repository: ReminderRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        //llamar notificacion mostrar
        Log.e("RECIVER", "reciver : " + intent.toString());
        val name = intent?.getStringExtra("ALARM_NAME")
        val posponedAlarm = if(intent == null) false else intent.getBooleanExtra("POSPONED",false)
        name?.let {
            remainderData = ReminderEntity(
                id= intent.getLongExtra("ALARM_ID",0),
                name = it,
                description = intent.getStringExtra("ALARM_DESC")!!,
                type = intent.getStringExtra("ALARM_TYPE")!!,
                date = 0.0,
                image = "",
                humaDate = intent.getStringExtra("ALARM_TIME")!!,
                status = "visualizado"
            )
        }

        creatNotification(context,remainderData.id,posponedAlarm)
        runBlocking {
            repository.updateStatus(remainderData)
        }

    }

    fun miniFactoryIntent(context: Context?,actionIntent :String):Intent{
        return Intent(context,IntentBoradcastReciver::class.java).apply {
            action = actionIntent
            putExtra("ALARM_ID",remainderData.id)
            putExtra("ALARM_NAME",remainderData.name)
            putExtra("ALARM_DESC",remainderData.description)
            putExtra("ALARM_TYPE",remainderData.type)
            putExtra("ALARM_TIME",remainderData.humaDate)
            putExtra("POSPONED",true)
        }
    }

    fun setListeners(remoteViews: RemoteViews,context: Context?){
        val intentFive = miniFactoryIntent(context,"5")
        val intentTen = miniFactoryIntent(context,"10")
        val intentFifteen = miniFactoryIntent(context,"15")

        val pendingIntentFive = PendingIntent.getBroadcast(context, 124, intentFive,  PendingIntent.FLAG_IMMUTABLE)
        val pendingIntentTen = PendingIntent.getBroadcast(context, 124, intentTen,  PendingIntent.FLAG_IMMUTABLE)
        val pendingIntentFifteen = PendingIntent.getBroadcast(context, 124, intentFifteen,  PendingIntent.FLAG_IMMUTABLE)


        remoteViews.setOnClickPendingIntent(R.id.button,pendingIntentFive);
        remoteViews.setOnClickPendingIntent(R.id.button2,pendingIntentTen);
        remoteViews.setOnClickPendingIntent(R.id.button3,pendingIntentFifteen);

    }

    fun creatNotification(context: Context?, id: Long, posponedAlarm: Boolean){

        val notificationLayout = RemoteViews("com.nicolascastilla.challenge4", R.layout.simple_notification)
        var notificationLayoutExpanded = RemoteViews("com.nicolascastilla.challenge4", R.layout.custom_notification)
        if(posponedAlarm)
            notificationLayoutExpanded = RemoteViews("com.nicolascastilla.challenge4", R.layout.posponed)

        notificationLayout.setTextViewText(R.id.textView3, remainderData.name)
        notificationLayout.setTextViewText(R.id.textView4,remainderData.description)

        notificationLayoutExpanded.setTextViewText(R.id.textView,remainderData.name)
        notificationLayoutExpanded.setTextViewText(R.id.textView2,remainderData.description)
        notificationLayoutExpanded.setTextViewText(R.id.textView5,remainderData.type)
        notificationLayoutExpanded.setTextViewText(R.id.textView7,remainderData.image)
        setListeners(notificationLayoutExpanded,context)

        val resultIntent = Intent(context, ReminderInfoActivity::class.java).apply {
            putExtra("REMAINDER_ID",id)
            setAction("$id")
            flags= Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent,PendingIntent.FLAG_IMMUTABLE)
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "default"
        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            //.setContentTitle("Ghallenge4 Notification")
            //.setContentText("ya casi casi")
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            with(NotificationManagerCompat.from(context)){
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                notify(0,builder.build())
            }
        }else{
            notificationManager.notify(0, builder.build())
        }




    }


}