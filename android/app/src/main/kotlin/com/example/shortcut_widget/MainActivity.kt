package com.example.shortcut_widget

import android.content.Intent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity


class MainActivity: FlutterActivity() {
    override fun onNewIntent(@NonNull intent: Intent) {
        val action: String? = intent.action

        // Example action
        val routeIntent = action != null && action == "push_a_route"
        val engine = flutterEngine
        if (routeIntent && engine != null) {
            // Pushing a new route when new intent received
            engine.navigationChannel.pushRoute("/BottomsheetWidget")
        }
    }
}
