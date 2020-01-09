package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.willjane.teabuddy.R
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.*
import com.willjane.teabuddy.utils.Helpers
import kotlinx.android.synthetic.main.fragment_teatimer.*


class TeaTimerFragment: Fragment() {
    private lateinit var  vm:  MainActivityViewModel
    private lateinit var timerMins: TextView
    private lateinit var timerSec: TextView
    private lateinit var startTime: Button
    private lateinit var stopTime: Button
    private lateinit var openSetTime: Button
    private lateinit var popupView: ViewGroup
    private lateinit var setMinutes: NumberPicker
    private lateinit var setSeconds: NumberPicker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "loading tea timer fragment")
        val rootView = inflater.inflate(R.layout.fragment_teatimer, container, false) as ViewGroup
        popupView = inflater.inflate(R.layout.set_teatimer, null) as ViewGroup

        timerMins = rootView.findViewById(R.id.timerMin)
        timerSec = rootView.findViewById(R.id.timerSec)
        startTime = rootView.findViewById(R.id.startTimer)
        stopTime = rootView.findViewById(R.id.stopTimer)
        openSetTime = rootView.findViewById(R.id.openSetTimer)
        setMinutes = popupView.findViewById(R.id.setMinutes)
        setSeconds = popupView.findViewById(R.id.setSeconds)

        initialize()

        vm.timerLength.observe(this, androidx.lifecycle.Observer {
            setSecText(vm.timerLength.value ?: 0)
            setMinText(vm.timerLength.value ?: 0)
        })

        return rootView
    }

    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.TEA_TIMER

        setMinText(vm.timerLength.value ?: 0)
        setSecText(vm.timerLength.value ?: 0)

        startTime.setOnClickListener {
            startTimer()
        }

        stopTime.setOnClickListener {
            stopTimer()
        }

        openSetTime.setOnClickListener {
            initPopup()
        }

    }

    private fun startTimer() {
        if(vm.timerLength.value == null) {
            Helpers.makeCustomToast("Please set your Tea Timer!")
        }
        else {
            vm.setTimerLength(vm.timerLength.value ?: 0)
            vm.countDownTimer.start()
        }
    }

    private fun stopTimer() {
        vm.countDownTimer.cancel()
    }

    private fun setMinText(millSec: Long) {
        var text = ((millSec / 60000) % 10).toString()
        if ((millSec / 60000 % 10) < 10) {
            text = ("0").plus(text)
        }
        timerMins.text = text
    }

    private fun setSecText(millSec: Long) {
        var text = ((millSec / 1000) % 60 ).toString()
        if ((millSec / 1000 % 60) < 10) {
            text = ("0").plus(text)
        }
        timerSec.text = text
    }

    private fun initPopup() {
        stopTimer()
        setMinutes.value = (vm.timerLength.value ?: 0 / 60000 % 10).toInt()
        setSeconds.value = (vm.timerLength.value ?: 0 / 1000 % 60).toInt()
        setMinutes.minValue = 0
        setMinutes.maxValue = 9
        setSeconds.minValue = 0
        setSeconds.maxValue = 59

        val window = PopupWindow(context)
        window.contentView = popupView
        val closeSetTime =  popupView.findViewById<Button>(R.id.closeSetTimer)
        closeSetTime.setOnClickListener{
            window.dismiss()
        }
        val setTimer = popupView.findViewById<Button>(R.id.setTimer)
        setTimer.setOnClickListener {
            vm.timerLength.value = (setMinutes.value * 60000 + setSeconds.value * 1000).toLong()
            setMinText(vm.timerLength.value ?: 0)
            setSecText(vm.timerLength.value ?: 0)
            window.dismiss()
        }
        window.showAtLocation(openSetTime, Gravity.CENTER,0,0)
    }

    companion object{
        const val TAG = "TeaTimerFragment"
    }
}