package com.willjane.teabuddy


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.auth.User
import com.willjane.teabuddy.fragments.*
import com.willjane.teabuddy.utils.DAO.TeaRealmDAO
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var  dashboardFragment: DashboardFragment
    private lateinit var  encyclopediaFragment: EncyclopediaFragment
    private  lateinit var  teaTimerFragment: TeaTimerFragment
    private  lateinit var  userFragment: UserFragment

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
                MainActivityViewModel.ENCYCLOPEDIA_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.encyclopedia)
                }
                MainActivityViewModel.TEA_TIMER->{
                    supportActionBar?.title = resources.getString(R.string.teatimer)
                }
                MainActivityViewModel.TEA_INFO_PAGE->{
                    supportActionBar?.title = resources.getString(R.string.teaInfo)
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
                    replace(R.id.fragmentView, TeaInformationFragment(it)).commit()
                    addToBackStack(null)
                }
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
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentView, teaTimerFragment).commit()
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
    }


}
