package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.willjane.teabuddy.utils.DAO.TeaUserRealmDAO
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_user.*

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

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flipUserFragment()
        //have to observe here after view is createdf
        vm.currentUser.observe(activity!!, Observer {
            if(it != null ){
                flipUserFragment()
            } else {
                flipUserFragment()
            }
        })
    }

    private fun flipUserFragment(){
        if(vm.isSignedIn){
            signInButtonContainer.visibility = View.GONE
            Glide.with(context?:return).load(vm.currentUser.value?.photoUrl).into(userPic)
            userName.text = vm.currentUser.value?.name
            userEmail.text = vm.currentUser.value?.email
        } else {
            userPic.visibility = View.GONE
            userName.visibility = View.GONE
            userEmail.visibility = View.GONE
            signInButtonContainer.visibility = View.VISIBLE
            signOutButton.visibility = View.GONE
            signInButton.text = resources.getString(R.string.signIn)
        }

        signOutButton.setOnClickListener {
            vm.signUserOut(context?:return@setOnClickListener)
        }

        signInButton.setOnClickListener {
            vm.launchLoginActivity.value = true
        }
    }

    private fun setUpVM() {
        Log.d(TAG, "setting up VM")
        vm = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.USER_PAGE
    }

    override fun onResume() {
        super.onResume()
        //TODO: refresh user
    }

    companion object{
        private const val TAG = "UserFragment"
    }
}