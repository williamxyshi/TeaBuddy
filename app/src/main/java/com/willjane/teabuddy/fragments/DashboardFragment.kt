package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willjane.teabuddy.R
import com.willjane.teabuddy.TeaListAdapter
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class DashboardFragment : Fragment() {

    private lateinit var vm: MainActivityViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_dashboard, container, false) as ViewGroup

        initialize()

        recyclerView = rootView.findViewById(R.id.favouritesRecyclerView)
        vm.refreshTeaList()
        recyclerView.adapter = TeaListAdapter(vm)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return rootView
    }

    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.DASHBOARD_PAGE

        vm.teaListUpdated.observe(this, Observer {
            if(it){
                recyclerView.adapter?.notifyDataSetChanged()


            }
        })


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