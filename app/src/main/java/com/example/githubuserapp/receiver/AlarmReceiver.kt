package com.example.githubuserapp.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.githubuserapp.R
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Reminder"
        private const val TYPE_TITLE = "RepeatingAlarm"
        private const val NOTIF_ID = 1
        private const val TIME_FORMAT = "HH:mm"
        private const val EXTRA_MSG = "extra_msg"
        private const val EXTRA_TYPE = "extra_type"
        private const val REPEATING_ID = 101

    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MSG)
        if (message != null) {
            showNotifications(context, TYPE_TITLE, message)
        }
    }

    private fun showNotifications(context: Context, title: String, message: String) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(context.resources.getString(R.string.settings_reminder))
            .setContentText(message)
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(chanel)
        }

        val notif = builder.build()
        notificationManager.notify(REPEATING_ID, notif)
    }

    fun setAlarmRepeater(context: Context, type: String, time: String, message: String) {
        if (isTimeFormatInvalid(time, TIME_FORMAT)) return

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(EXTRA_MSG, message)
            intent.putExtra(EXTRA_TYPE, type)
            val timeSplit = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]))
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]))
            calendar.set(Calendar.SECOND, 0)

            val pendingIntent = PendingIntent.getBroadcast(context, REPEATING_ID, intent, 0)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            Toast.makeText(context, "Repeating Alarm Set Up", Toast.LENGTH_SHORT).show()
    }

    private fun isTimeFormatInvalid(time: String, timeFormat: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(time)
            false
        } catch (e: Exception) {
            true
        }
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REPEATING_ID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Repeating Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }


}