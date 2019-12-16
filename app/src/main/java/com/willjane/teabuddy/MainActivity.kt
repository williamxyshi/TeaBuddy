package com.willjane.teabuddy


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.willjane.teabuddy.fragments.*
import com.willjane.teabuddy.fragments.DashboardFragment
import com.willjane.teabuddy.fragments.EncyclopediaFragment
import com.willjane.teabuddy.fragments.TeaInformationFragment
import com.willjane.teabuddy.fragments.TeaTimerFragment
import com.willjane.teabuddy.utils.DAO.TeaRealmDAO
import com.willjane.teabuddy.utils.DAO.TeaUserAuthDAO
import com.willjane.teabuddy.utils.DAO.TeaUserRealmDAO
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.utils.models.TeaBuddyUser
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var  dashboardFragment: DashboardFragment
    private lateinit var  encyclopediaFragment: EncyclopediaFragment
    private  lateinit var  teaTimerFragment: TeaTimerFragment
    private  lateinit var  userFragment: UserFragment
    private  lateinit var  worldFragment: WorldFragment

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpVM()
        setUpNavigationBar()

        dashboardFragment = DashboardFragment()
        encyclopediaFragment = EncyclopediaFragment()
        teaTimerFragment = TeaTimerFragment()
        userFragment = UserFragment()
        worldFragment = WorldFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentView, dashboardFragment).commit()

        vm.refreshTeaList()
    }

    private fun startAuthTask(){
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_teacup)
                .setTheme(R.style.AppTheme)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val firebaseUser = FirebaseAuth.getInstance().currentUser

                val user = vm.firebaseUserToTeaBuddyUser(firebaseUser)

                vm.currentUser.value = user
                Log.d(TAG, "logged in ")
                // ...
            } else {
                Log.d(TAG, "logged in  failed: ${response?.error?.errorCode} ")

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private fun setUpVM(){
        Log.d(TAG, "setting up VM")
        vm = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        if(TeaUserAuthDAO.isUserSignedIn()){
            vm.currentUser.value = TeaUserRealmDAO.getUser()
        }
        vm.refreshPostsList()

        vm.currentActionPage.observe(this, androidx.lifecycle.Observer {
            when(it){
                MainActivityViewModel.DASHBOARD_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.home)
                }
                MainActivityViewModel.ENCYCLOPEDIA_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.encyclopedia)
                }
                MainActivityViewModel.TEA_TIMER->{
                    supportActionBar?.title = resources.getString(R.string.teatimer)
                }
                MainActivityViewModel.TEA_INFO_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.teaInfo)
                }
                MainActivityViewModel.USER_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.user)
                }
                MainActivityViewModel.TEA_WORLD_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.tea_buddy_world)
                }
            }
        })
        vm.teaListUpdated.observe(this, androidx.lifecycle.Observer {
            if(it){
                Log.d(TAG, "vm.teaList: ${vm.fireStoreTeaList}")
                TeaRealmDAO.updateTeaList(vm.fireStoreTeaList)

                Log.d(TAG, "realm test: ${TeaRealmDAO.getTeaList()}")
            }
        })
        vm.currentTea.observe(this, androidx.lifecycle.Observer {
            if(it != null){
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentView, TeaInformationFragment(it, teaTimerFragment)).commit()
                    addToBackStack(null)
                }
            }

        })
        vm.currentTeaTime.observe(this, androidx.lifecycle.Observer {
            if(it != null){
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentView, teaTimerFragment).commit()
                    addToBackStack(null)
                }
            }

        })
        vm.launchLoginActivity.observe(this, androidx.lifecycle.Observer{
            if(it == true){
                startAuthTask()
            }
        } )

        vm.currentUser.observe(this, androidx.lifecycle.Observer{
            if(it!= null){
                Log.d(TAG, "current user is: ${it.name}, email: ${it.email}")

                if(it.isNew) {
                    vm.teaFirestoreDAO.addUser(it)
                    it.isNew = false
                } else {
                    Log.d(TAG, "not a new user")
                    //TODO does this make sense?
//                    vm.teaFirestoreDAO.updateUser(it)
                }
                TeaUserRealmDAO.updateTeaBuddyUser(it)
            }
        })
    }

    fun launchTeaInfoFragment(tea: Tea){

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
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentView, teaTimerFragment).commit()
                        addToBackStack(null)
                    }
                    true
                }
                R.id.bottom_navigation_community->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentView, worldFragment).commit()
                        addToBackStack(null)
                    }
                    true
                }
                R.id.bottom_navigation_user->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentView, userFragment).commit()
                        addToBackStack(null)
                    }
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

        private const val RC_SIGN_IN = 123
    }


}
