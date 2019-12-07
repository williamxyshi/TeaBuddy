package com.willjane.teabuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import io.realm.Realm

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    var currentActionPage: MutableLiveData<Int> = MutableLiveData()
    val realm = Realm.getDefaultInstance()

    val firestore = FirebaseFirestore.getInstance()

    companion object {
        const val DASHBOARD_PAGE = 0
        const val TEA_INFO = 1
    }


}