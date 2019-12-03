package com.example.teabuddy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.teabuddy.fragments.DashboardFragment

/**
 * Iniitial landing page of the app
 */
class LandingPageActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        Log.d(TAG, "launching landing page activity")

        initialize()
    }

    /**
     * initialize needed variables
     */
    private fun initialize(){
        button = findViewById(R.id.startButton)

        //start button pressed, start main activity
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    companion object{
        const val TAG = "LandingPageActivity"
    }

}