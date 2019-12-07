package com.willjane.teabuddy

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class TeaBuddyApplication : Application() {
    override fun onCreate() {
        Log.d(TAG,"initializing realm")
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("TeaBuddy.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    companion object{
        private const val TAG = "TeaBuddyApplication"
    }
}