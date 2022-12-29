package com.example.shortcut_widget


import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.widget.RemoteViews
import es.antonborri.home_widget.*


class AppWidgetProvider : HomeWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray, widgetData: SharedPreferences) {
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {

                // Open App on Widget Click

                val pendingIntent = HomeWidgetLaunchIntent.getActivity(context,
                    MainActivity::class.java)
                setOnClickPendingIntent(R.id.widget_root, pendingIntent)

                val counter = widgetData.getString("_number", "")
                Log.i("onMethodCall", "onMethodCall: $counter")
                print("onMethodCall: $counter")
                var counterText = "$counter"
//
//                if (counter == 0) {
//                    counterText = "You have not pressed the counter button"
//                }

                setTextViewText(R.id.tv_counter, counterText)
                // Pending intent to update counter on button click
              //  val backgroundIntent = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://updatecounter"))


                //open app intent
                // val pendingIntent2 = HomeWidgetLaunchIntent.getActivity(context, MainActivity::class.java)

               // val whatsappIntent = openWA.openWhatsApp(counterText,"",context )
                setOnClickPendingIntent(R.id.bt_update, openWA.openWhatsApp(counterText,"",context ))

                val backgroundIntent1 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,1"))
                setOnClickPendingIntent(R.id.bt_one, backgroundIntent1)

                val backgroundIntent2 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,2"))
                setOnClickPendingIntent(R.id.bt_two, backgroundIntent2)

                val backgroundIntent3 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,3"))
                setOnClickPendingIntent(R.id.bt_three, backgroundIntent3)

                val backgroundIntent4 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,4"))
                setOnClickPendingIntent(R.id.bt_four, backgroundIntent4)

                val backgroundIntent5 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,5"))
                setOnClickPendingIntent(R.id.bt_five, backgroundIntent5)

                val backgroundIntent6 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,6"))
                setOnClickPendingIntent(R.id.bt_six, backgroundIntent6)

                val backgroundIntent7 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,7"))
                setOnClickPendingIntent(R.id.bt_seven, backgroundIntent7)

                val backgroundIntent8 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,8"))
                setOnClickPendingIntent(R.id.bt_eight, backgroundIntent8)

                val backgroundIntent9 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,9"))
                setOnClickPendingIntent(R.id.bt_nine, backgroundIntent9)

                val backgroundIntent0 = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,0"))
                setOnClickPendingIntent(R.id.bt_zero, backgroundIntent0)

                val backgroundIntentPlus = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,+"))
                setOnClickPendingIntent(R.id.bt_plus, backgroundIntentPlus)

                val backgroundIntentRemove = HomeWidgetBackgroundIntent.getBroadcast(context, Uri.parse("myAppWidget://onNumberBtnClick,-"))
                setOnClickPendingIntent(R.id.bt_erase, backgroundIntentRemove)

            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

}