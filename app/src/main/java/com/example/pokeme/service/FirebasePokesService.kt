package com.example.pokeme.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.pokeme.App
import com.example.pokeme.R
import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.data.repository.AuthRepository
import com.example.pokeme.utils.Const
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class FirebasePokesService : FirebaseMessagingService() {
    @Inject
    lateinit var messageRepo: MessageRepository
    @Inject
    lateinit var authRepo: AuthRepository

    override fun onCreate() {
        super.onCreate()
        (application as App).appComponent.inject(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val email = authRepo.currentAccount?.email ?: token
        messageRepo.registerToken(email, token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title ?: "Title"
        val body = remoteMessage.notification?.body ?: "Body"
        val channel = NotificationChannel(
            Const.POKES_NOTIFICATION_CHANNEL_ID,
            "PokeMessage",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val builder = Notification.Builder(this, Const.POKES_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.comment)
            .setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    companion object {
        const val DEBUG_TAG = "POKE_SERVICE"
    }
}