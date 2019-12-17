package com.willjane.teabuddy

import android.app.Application
import android.content.Context
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.firestore.FirebaseFirestore
import com.willjane.teabuddy.utils.Helpers

class TeaBuddyApplication : Application() {
    override fun onCreate() {
        Log.d(TAG,"initializing realm")
        super.onCreate()

        /**
         * initializing Realm for TEABUDDY
         */
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("TeaBuddy.realm").build()
        Realm.setDefaultConfiguration(config)
        Helpers.init(applicationContext)
    }

    companion object{
        private const val TAG = "TeaBuddyApplication"


    }
}