package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.willjane.teabuddy.MainActivity
import com.willjane.teabuddy.R
import com.willjane.teabuddy.adapters.TeaListAdapter
import com.willjane.teabuddy.utils.DAO.TeaUserAuthDAO
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_tea_world.*
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * Community of TeaBuddy - view, share and interact with other TeaBuddies
 */
class WorldFragment: Fragment() {

    private lateinit var  vm: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_tea_world, container, false) as ViewGroup

        setUpVM()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.refreshPostsList()

        if(vm.isSignedIn){
            notSignedIn.visibility = View.GONE


        } else{
            notSignedIn.visibility = View.VISIBLE
        }
    }

    private fun setUpVM() {
        Log.d(TAG, "setting up VM")
        vm = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.TEA_WORLD_PAGE
    }

    override fun onResume() {
        super.onResume()
        vm.refreshPostsList()

    }
    companion object{
        private const val TAG = "TeaWorldFragment"
    }
}