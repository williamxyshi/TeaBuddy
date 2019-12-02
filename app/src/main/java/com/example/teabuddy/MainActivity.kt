package com.example.teabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.teabuddy.fragments.DashboardFragment
import com.example.teabuddy.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var  dashboardFragment: DashboardFragment

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "launching main activity")

        setUpVM()

        dashboardFragment = DashboardFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentView, dashboardFragment).commit()
    }

    private fun setUpVM(){
        Log.d(TAG, "setting up VM")
        vm = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        vm = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        vm.currentActionPage.observe(this, androidx.lifecycle.Observer {
            when(it){
                MainActivityViewModel.DASHBOARD_PAGE->{
                }
                MainActivityViewModel.TEA_INFO->{
                }
            }
        })
    }

    companion object{
        const val TAG = "MainActivity"
    }


}
