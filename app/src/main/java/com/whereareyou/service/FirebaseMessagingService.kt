package com.whereareyou.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("onMessageReceived", "${message.messageId}")
        Log.e("onMessageReceived", "${message.data}")
        Log.e("onMessageReceived", "${message.messageType}")
        Log.e("onMessageReceived", "${message.from}")
        Log.e("onMessageReceived", "${message.collapseKey}")
        Log.e("onMessageReceived", "${message.notification?.title}")
        Log.e("onMessageReceived", "${message.notification?.body}")
        Log.e("onMessageReceived", "${message.notification?.imageUrl}")
        Log.e("onMessageReceived", "${message.originalPriority}")
        Log.e("onMessageReceived", "${message.priority}")
        Log.e("onMessageReceived", "${message.rawData}")
        Log.e("onMessageReceived", "${message.senderId}")
        Log.e("onMessageReceived", "${message.sentTime}")
        Log.e("onMessageReceived", "${message.to}")
        Log.e("onMessageReceived", "${message.ttl}")
    }
}