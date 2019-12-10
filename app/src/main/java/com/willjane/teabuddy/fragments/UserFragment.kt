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
import com.firebase.ui.auth.AuthUI
import com.willjane.teabuddy.MainActivity
import com.willjane.teabuddy.R
import com.willjane.teabuddy.adapters.TeaListAdapter
import com.willjane.teabuddy.utils.DAO.TeaUserAuthDAO
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class UserFragment: Fragment() {

    private lateinit var  signInButton: Button
    private lateinit var  vm: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user, container, false) as ViewGroup

        setUpVM()

        signInButton = rootView.findViewById(R.id.signInButton)
        signInButton.setOnClickListener {
            if(vm.isSignedIn){
                TeaUserAuthDAO.signUserOut(context?:return@setOnClickListener)
            } else {
                vm.launchLoginActivity.value = true
            }
        }

        if(vm.isSignedIn){
            signInButton.text = resources.getString(R.string.signOut)
        } else {
            signInButton.text = resources.getString(R.string.signIn)
        }

        return rootView
    }
    private fun setUpVM() {
        Log.d(MainActivity.TAG, "setting up VM")
        vm = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.USER_PAGE
    }
}