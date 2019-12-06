package com.example.teabuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.teabuddy.R
import com.example.teabuddy.viewmodels.MainActivityViewModel

class DashboardFragment : Fragment() {

    private lateinit var vm: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_dashboard, container, false) as ViewGroup

        initialize()

        return rootView
    }

    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.DASHBOARD_PAGE
    }

    override fun onResume() {
        super.onResume()
        //sets home bar
        if(::vm.isInitialized){
            vm.currentActionPage.value = MainActivityViewModel.DASHBOARD_PAGE
        }
    }

    companion object{
        const val TAG = "DashboardFragment"
    }
}