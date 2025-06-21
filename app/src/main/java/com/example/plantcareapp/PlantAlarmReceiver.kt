package com.example.plantcareapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PlantAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val plantName = intent?.getStringExtra("plantName") ?: return
        context?.let {
            NotificationHelper.createNotification(it, plantName)
        }
    }
}
