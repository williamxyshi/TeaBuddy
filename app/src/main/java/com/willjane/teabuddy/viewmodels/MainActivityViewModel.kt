package com.willjane.teabuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.facebook.internal.Mutable
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseUser
import com.willjane.teabuddy.utils.DAO.TeaFirestoreDao
import com.willjane.teabuddy.utils.DAO.TeaRealmDAO
import com.willjane.teabuddy.utils.DAO.TeaUserAuthDAO
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.utils.models.TeaBuddyUser
import io.realm.Realm

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    var currentUser: MutableLiveData<TeaBuddyUser?> = MutableLiveData(null)

    var currentActionPage: MutableLiveData<Int> = MutableLiveData()
    val realm = Realm.getDefaultInstance()

    val isSignedIn: Boolean
        get() = TeaUserAuthDAO.isUserSignedIn()

    val teaFirestoreDAO = TeaFirestoreDao()

    var fireStoreTeaList = listOf<Tea>()

    val teaList: List<Tea>
        get() = TeaRealmDAO.getTeaList()

    val favList: List<Tea>
        get() = TeaRealmDAO.getFavList()



    var teaListUpdated : MutableLiveData<Boolean> = MutableLiveData(false)
    var launchLoginActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    val currentTea : MutableLiveData<Tea> = MutableLiveData()

    fun refreshTeaList(){
            teaListUpdated.value = false
            teaFirestoreDAO.retrieveTeaList(this)
    }

    fun firebaseUserToTeaBuddyUser(firebaseUser: FirebaseUser?): TeaBuddyUser?{

       if(firebaseUser == null) return null

        val user = TeaBuddyUser(firebaseUser.uid)
        user.email = firebaseUser.email
        user.name = firebaseUser.displayName
        user.photoUrl = firebaseUser.photoUrl.toString()
        user.emailVerified = firebaseUser.isEmailVerified

        return user
    }


    companion object {
        const val TAG = "MainActivityViewModel"

        const val DASHBOARD_PAGE = 0
        const val ENCYCLOPEDIA_PAGE = 1
        const val TEA_TIMER = 2
        const val TEA_INFO_PAGE = 3
        const val USER_PAGE = 4
        const val TEA_WORLD_PAGE = 5
    }


}