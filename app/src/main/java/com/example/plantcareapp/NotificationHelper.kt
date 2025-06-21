package com.example.plantcareapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {

    private const val CHANNEL_ID = "plantcare_channel"

    fun createNotification(context: Context, plantName: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Plant Care Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Water Me!")
            .setContentText("$plantName is thirsty 💧")
            .setSmallIcon(R.drawable.water_jug)
            .setAutoCancel(true)
            .build()

        manager.notify(plantName.hashCode(), notification)
    }
}
