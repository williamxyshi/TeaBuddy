package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willjane.teabuddy.R
import com.willjane.teabuddy.adapters.EncyclopediaListAdapter
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class EncyclopediaFragment: Fragment() {

    private lateinit var  vm:  MainActivityViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "loading encyclopedia fragment")
        val rootView = inflater.inflate(R.layout.fragment_encyclopedia, container, false) as ViewGroup

        initialize()

        recyclerView = rootView.findViewById(R.id.encyclopediaRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = EncyclopediaListAdapter(vm, requireContext())


        return rootView
    }
    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.ENCYCLOPEDIA_PAGE

    }

    companion object{
        const val TAG = "TeaInfoFragment"
    }
}