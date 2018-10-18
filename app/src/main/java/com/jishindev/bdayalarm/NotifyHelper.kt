package com.jishindev.bdayalarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.AlarmManagerCompat
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


const val TIME_TO_SURPRISE = "25-DEC-2018 00:15:00"
lateinit var ringtoneAlarm:Ringtone

fun Context.setSurprise(dateTime: String = TIME_TO_SURPRISE) {
    Log.d("NotifyHelper", "setSurprise() called")
    val intent = Intent(this, AlarmBR::class.java)
    val pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 1, intent, 0)
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val calender = Calendar.getInstance()
    calender.time = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US).parse(dateTime)

    //val calOneMinFuture = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis()+(60*1000) }
    AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, /*calOneMinFuture*/calender.timeInMillis, pendingIntent)
    Toast.makeText(this, "Bday surprise set to ${SimpleDateFormat("dd:MMM:yyyy HH:mm:ss", Locale.US).format(/*calOneMinFuture*/calender.time)}".also {
        Log.d("NotifyHelper", "setSurprise: $it")
    }, Toast.LENGTH_LONG).show()

    // enable boot receiver
    val receiver = ComponentName(this, BootBR::class.java)
    packageManager.setComponentEnabledSetting(receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)
}

fun Context.showSurprise() {

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("http://github.com") //  <<== Url to be opened upon notification click
    val pIntent = PendingIntent.getActivity(this, 0, intent, 0)
    val message = "Hey A, B has planned something for you. Let's go?" // <<== Notification content
    val notification = NotificationCompat.Builder(this, "default")
             //.setTicker("BDay")
             .setContentTitle("Surprise")
            .setColor(Color.RED)
            .setContentIntent(pIntent)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            //.setColorized(true)
            .setAutoCancel(true)
            .setOngoing(true)
            .build()

    val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    nm.notify(1, notification)
    Log.d("NotifyHelper", "onReceive: Surpriseeeee!!!")
    //Toast.makeText(this, "Surpriseeeee!!!", Toast.LENGTH_LONG).show()

    val alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    ringtoneAlarm = RingtoneManager.getRingtone(applicationContext, alarmTone)
    ringtoneAlarm.play()
}

fun Context.createNotificationChannel() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "default"
        val description = "All default notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(name, name, importance)
        channel.description = description

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}