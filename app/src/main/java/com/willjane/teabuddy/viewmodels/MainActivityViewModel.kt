package com.willjane.teabuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.willjane.teabuddy.utils.DAO.TeaFirestoreDao
import com.willjane.teabuddy.utils.models.Tea
import io.realm.Realm

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    var currentActionPage: MutableLiveData<Int> = MutableLiveData()
    val realm = Realm.getDefaultInstance()

    val teaFirestoreDAO = TeaFirestoreDao()
    var teaList = listOf<Tea>()
    var teaListUpdated : MutableLiveData<Boolean> = MutableLiveData(false)

    fun refreshTeaList(){
            teaListUpdated.value = false
            teaFirestoreDAO.retrieveTeaList(this)
    }


    companion object {
        const val TAG = "MainActivityViewModel"

        const val DASHBOARD_PAGE = 0
        const val TEA_INFO = 1
        const val TEA_TIMER = 2
    }


}