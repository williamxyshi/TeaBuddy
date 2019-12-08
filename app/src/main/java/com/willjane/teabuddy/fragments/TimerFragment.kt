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
import android.text.InputFilter
import android.widget.Button
import android.widget.TextView
import io.opencensus.stats.Aggregation
import kotlinx.android.synthetic.main.fragment_teatimer.view.*
import java.util.*


class TeaTimerFragment: Fragment() {
    private lateinit var  vm:  MainActivityViewModel
    private lateinit var timerMins: TextView
    private lateinit var timerSec: TextView
    private lateinit var startTime: Button
    private lateinit var stopTime: Button
    private var timerLength: Long = 350000
    private lateinit var countDownTimer: CountDownTimer
    private var isInitStart = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "loading tea timer fragment")
        val rootView = inflater.inflate(R.layout.fragment_teatimer, container, false) as ViewGroup
        timerMins = rootView.findViewById(R.id.timerMin)
        timerSec = rootView.findViewById(R.id.timerSec)
        startTime = rootView.findViewById(R.id.startTimer)
        stopTime = rootView.findViewById(R.id.stopTimer)
        setMinutes(timerLength)
        setSeconds(timerLength)
        initializeTimer(timerLength)
        initialize()

        return rootView
    }

    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.TEA_TIMER

        startTime.setOnClickListener {
            startTimer()
        }

        stopTime.setOnClickListener {
            stopTimer()
        }
    }

    private fun initializeTimer(timeLeft : Long) {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                setMinutes(millisUntilFinished)
                setSeconds(millisUntilFinished)
                timerLength = millisUntilFinished
            }
            override fun onFinish() {
                timerSec.text = "done"
            }

        }
    }
    private fun startTimer() {
        if (!isInitStart) {
            initializeTimer(timerLength)
            countDownTimer.start()
        }
    }

    private fun stopTimer() {
        countDownTimer.cancel()

    }

    private fun setMinutes(millSec: Long) {
        timerMins.text = millToMin(millSec)
    }
    private fun setSeconds(millSec: Long) {
        timerSec.text = millToSec(millSec)
    }

    private fun millToMin(millSec : Long) : String {
        return (((millSec / 60000) % 10).toString())
    }

    private fun millToSec(millSec : Long) : String {
        return (((millSec / 1000) % 60 ).toString())
    }
    companion object{
        const val TAG = "TeaTimerFragment"
    }
}