package com.willjane.teabuddy


import android.content.ClipData
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.willjane.teabuddy.R
import com.willjane.teabuddy.fragments.DashboardFragment
import com.willjane.teabuddy.fragments.EncyclopediaFragment
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var  dashboardFragment: DashboardFragment
    private lateinit var  encyclopediaFragment: EncyclopediaFragment

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpVM()
        setUpNavigationBar()

        dashboardFragment = DashboardFragment()
        encyclopediaFragment = EncyclopediaFragment()

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

    private fun setUpNavigationBar(){

        navigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.bottom_navigation_dashboard-> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentView, dashboardFragment).commit()
                        addToBackStack(null)
                    }
                    true
                }
                R.id.bottom_navigation_encyclopedia-> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentView, encyclopediaFragment).commit()
                        addToBackStack(null)
                    }
                    true
                }
                R.id.bottom_navigation_teatimer->{

                    true
                }
                R.id.bottom_navigation_user->{

                    true
                }


                else -> {

                    false
                }



            }

        }


    }

    companion object{
        const val TAG = "MainActivity"
    }


}
