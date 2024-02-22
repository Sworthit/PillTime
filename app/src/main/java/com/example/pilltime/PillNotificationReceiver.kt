package com.example.pilltime

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pilltime.domain.Pill
import dagger.hilt.android.AndroidEntryPoint

const val PILL_INTENT = "pill_intent"
const val PILL_NOTIFICATION = "pill_notification"
const val PILL_HASHCODE = "pill_hashcode"


@AndroidEntryPoint
class PillNotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val pillHashCode = intent?.getIntExtra(PILL_HASHCODE, 1)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)  {
                intent?.getParcelableExtra(PILL_INTENT, Pill::class.java)?.let { pill ->
                    showNotification(it, pill, pillHashCode!!)
                }
            } else {
                intent?.getParcelableExtra<Pill>(PILL_INTENT)?.let { pill ->
                    showNotification(it, pill, pillHashCode!!)
                }
            }
        }
    }

    private fun showNotification(context: Context, pill: Pill, pillHashCode: Int) {
        val activityIntent = Intent(context, MainActivity::class.java)
        activityIntent.putExtra(PILL_NOTIFICATION, true)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(
            context,
            PillNotificationService.PILL_CHANNEL_ID
        )
            .setSmallIcon(androidx.core.R.drawable.notification_bg_normal)
            .setContentTitle("Time for medication")
            .setContentText(pill.name)
            .setContentIntent(activityPendingIntent)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(pillHashCode, notification)
    }
}