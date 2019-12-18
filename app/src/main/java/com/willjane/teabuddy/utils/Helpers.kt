package com.willjane.teabuddy.utils

import android.content.Context
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics
import android.widget.Toast
import com.willjane.teabuddy.TeaBuddyApplication
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

object Helpers {

    var displayMetrics: DisplayMetrics? = null
    fun init(){
        displayMetrics = TeaBuddyApplication.getContext().get()?.resources?.displayMetrics
    }
    fun dpToPx(dp: Float): Int {
        return if (displayMetrics == null) {
            dp.roundToInt()
        } else (dp * displayMetrics!!.density).roundToInt()
    }

    fun dpToPx(dp: Double): Int {
        return if (displayMetrics == null) {
            dp.roundToInt()
        } else (dp * displayMetrics!!.density).roundToInt()
    }

    fun dpToPx(dp: Int): Int {
        return if (displayMetrics == null) {
            dp
        } else (dp * displayMetrics!!.density).roundToInt()
    }

    /**
     * creates a custom toast with intended string
     */
    fun makeCustomToast(displayText: String){
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(TeaBuddyApplication.getContext().get(), displayText, duration)
        toast.show()
    }
}