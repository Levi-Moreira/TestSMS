package com.levimoreira.testsms

import android.app.Application
import android.support.multidex.MultiDexApplication

import com.google.firebase.FirebaseApp

/**
 * Created by levi on 28/02/18.
 */

class SampleApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
