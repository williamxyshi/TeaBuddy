package com.willjane.teabuddy


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.willjane.teabuddy.R
import com.willjane.teabuddy.fragments.DashboardFragment
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var  dashboardFragment: DashboardFragment

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpVM()

        dashboardFragment = DashboardFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentView, dashboardFragment).commit()

        vm.refreshTeaList()


    }

    private fun setUpVM(){
        Log.d(TAG, "setting up VM")
        vm = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        vm = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        vm.currentActionPage.observe(this, androidx.lifecycle.Observer {
            when(it){
                MainActivityViewModel.DASHBOARD_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.home)
                }
                MainActivityViewModel.TEA_INFO->{
                }
            }
        })
        vm.teaListUpdated.observe(this, androidx.lifecycle.Observer {
            if(it){
                Log.d(TAG, "vm.teaList: ${vm.teaList}")
            }
        })
    }

    companion object{
        const val TAG = "MainActivity"
    }


}
