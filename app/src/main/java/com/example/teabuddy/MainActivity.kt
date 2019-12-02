package com.example.teabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.teabuddy.fragments.LandingPageFragment

class MainActivity : AppCompatActivity() {

    private lateinit var  landingPageFragment: LandingPageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        landingPageFragment = LandingPageFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentView, landingPageFragment).commit()

    }






}
