package com.example.healthyeats

import android.app.Application
import com.google.firebase.FirebaseApp

class HealthyEatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}