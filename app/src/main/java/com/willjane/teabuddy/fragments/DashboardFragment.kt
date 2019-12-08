package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willjane.teabuddy.R
import com.willjane.teabuddy.adapters.TeaListAdapter
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class DashboardFragment : Fragment() {

    private lateinit var vm: MainActivityViewModel

    private lateinit var favRecyclerView: RecyclerView

    private lateinit var recRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_dashboard, container, false) as ViewGroup

        initialize()

        favRecyclerView = rootView.findViewById(R.id.favouritesRecyclerView)
        vm.refreshTeaList()
        favRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        favRecyclerView.adapter =
            TeaListAdapter(vm, context ?: return null, true)

        recRecyclerView = rootView.findViewById(R.id.recRecyclerView)
        recRecyclerView.layoutManager = GridLayoutManager(context, 3)
        recRecyclerView.adapter = TeaListAdapter(vm, context ?: return null, false)

        return rootView
    }

    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.DASHBOARD_PAGE

        vm.teaListUpdated.observe(this, Observer {
            if(it){
                favRecyclerView.adapter?.notifyDataSetChanged()
                recRecyclerView.adapter?.notifyDataSetChanged()


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