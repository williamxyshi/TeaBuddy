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
import java.lang.ref.WeakReference

class TeaBuddyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        context = WeakReference(applicationContext)

        /**
         * initializing Realm for TEABUDDY
         */
        Log.d(TAG,"initializing realm")
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("TeaBuddy.realm").build()
        Realm.setDefaultConfiguration(config)



        Helpers.init()

    }

    companion object{
        private const val TAG = "TeaBuddyApplication"

        private lateinit var context: WeakReference<Context>

        fun getContext(): WeakReference<Context> {
            return context
        }
    }
}