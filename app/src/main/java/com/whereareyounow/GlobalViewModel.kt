package com.whereareyounow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("GlobalViewModel-token", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result
                Log.e("GlobalViewModel-token FCM", token)

                val msg = token.toString()
//                Toast.makeText(application, msg, Toast.LENGTH_SHORT).show()
            }
        )
    }
}