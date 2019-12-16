package com.willjane.teabuddy.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.willjane.teabuddy.utils.DAO.TeaFirestoreDao
import com.willjane.teabuddy.utils.DAO.TeaRealmDAO
import com.willjane.teabuddy.utils.DAO.TeaUserAuthDAO
import com.willjane.teabuddy.utils.DAO.TeaUserRealmDAO
import com.willjane.teabuddy.utils.models.CommunityPost
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.utils.models.TeaBuddyUser
import io.realm.Realm
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    //current user that is logged into google authentication - retrieved from realm
    var currentUser: MutableLiveData<TeaBuddyUser?> = MutableLiveData(null)

    //current fragment the activity is on
    var currentActionPage: MutableLiveData<Int> = MutableLiveData()

    //Realm instance
    val realm = Realm.getDefaultInstance()

    //is user signed into google auth
    val isSignedIn: Boolean
        get() = TeaUserAuthDAO.isUserSignedIn()

    //access to the firestore database object for Tea model
    val teaFirestoreDAO = TeaFirestoreDao()

    //list of teas retrieved from firestore
    var fireStoreTeaList = listOf<Tea>()

    //total list of teas - this is retrieved from realm
    val teaList: List<Tea>
        get() = TeaRealmDAO.getTeaList()

    //lise of teas favourited by user
    val favList: List<Tea>
        get() = TeaRealmDAO.getFavList()

    //list of community posts retrieved from firebase
    var communityPosts = listOf<CommunityPost>()

    //because google firestore call is async, sometimes its nice to know when the data is returned
    var teaListUpdated : MutableLiveData<Boolean> = MutableLiveData(false)
    var launchLoginActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    //launches TeaInfoFragment when set to a tea
    val currentTea : MutableLiveData<Tea> = MutableLiveData()

    fun refreshTeaList(){
            teaListUpdated.value = false
            teaFirestoreDAO.retrieveTeaList(this)
    }

    fun refreshPostsList(){
        viewModelScope.launch {
            val postList = teaFirestoreDAO.getCommunityPosts()
            Log.d(TAG, "post list updated: ${postList}")
            communityPosts = postList
        }


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

    fun signUserOut(context: Context){
        TeaUserAuthDAO.signUserOut(context)
        currentUser.value = null
        TeaUserRealmDAO.removeUser()
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