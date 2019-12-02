package com.example.teabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.teabuddy.fragments.DashboardFragment
import com.example.teabuddy.fragments.LandingPageFragment
import com.example.teabuddy.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var  landingPageFragment: LandingPageFragment
    private lateinit var  dashboardFragment: DashboardFragment

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpVM()

        landingPageFragment = LandingPageFragment()
        dashboardFragment = DashboardFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentView, landingPageFragment).commit()
    }

    private fun setUpVM(){
        vm = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        vm.currentActionPage.observe(this, androidx.lifecycle.Observer {

            //set page of fragment

        })
    }








}
