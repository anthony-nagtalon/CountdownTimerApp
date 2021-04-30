package com.example.basiccountdowntimer

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class NotificationService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val timeRemaining = intent?.getStringExtra("timeRemaining")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
        0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "timerServiceChannel")
                .setContentTitle("Timer Notification Service")
                .setContentText(timeRemaining)
                .setSmallIcon(R.drawable.ic_baseline_alarm)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}