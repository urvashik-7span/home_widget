package com.example.shortcut_widget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import java.net.URLEncoder

object HomeWidgetLaunchIntent {

    const val HOME_WIDGET_LAUNCH_ACTION = "com.example.shortcut_widget.action.LAUNCH"

    fun <T> getActivity(context: Context, activityClass: Class<T>, uri: Uri? = null): PendingIntent where T : Activity {
        val intent = Intent(context, activityClass)
        intent.data = uri
        intent.action = "push_a_route"

        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= 23) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }

        return PendingIntent.getActivity(context, 0, intent, flags)
    }
}


object HomeWidgetBackgroundIntent {
    const val HOME_WIDGET_BACKGROUND_ACTION = "com.example.shortcut_widget.action.BACKGROUND"

    fun getBroadcast(context: Context, uri: Uri? = null): PendingIntent {
        val intent = Intent(context, HomeWidgetBackgroundReceiver::class.java)
        intent.data = uri
        intent.action = HOME_WIDGET_BACKGROUND_ACTION

        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= 23) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }
        return PendingIntent.getBroadcast(context, 0, intent, flags)
    }
}
object openWA{

    @SuppressLint("CommitPrefEdits")
    fun openWhatsApp(number: String, msg: String, context: Context) : PendingIntent{
        val url = Uri.parse("https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode("", "UTF-8"))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("ERROR WHATSAPP", e.toString())
        }
        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= 23) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }
        return PendingIntent.getActivity(context, 0, intent, flags)
    }
}