package com.example.countdown.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.countdown.data.Counter

class CounterService: Service() {

    private val binder = LocalBinder()
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class LocalBinder: Binder() {
        fun getService(): Counter {
            return Counter(minutes = 5)
        }
    }
}