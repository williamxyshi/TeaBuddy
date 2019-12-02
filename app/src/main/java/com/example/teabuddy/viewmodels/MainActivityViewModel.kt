package com.example.teabuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    var currentActionPage: MutableLiveData<Int> = MutableLiveData()

    companion object {
        const val DASHBOARD_PAGE = 0
        const val TEA_INFO = 1
    }


}