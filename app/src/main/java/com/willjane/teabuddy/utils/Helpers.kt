package com.willjane.teabuddy.utils

import android.content.Context
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics
import kotlin.math.roundToInt

object Helpers {

    var displayMetrics: DisplayMetrics? = null
    fun init(ctx: Context){
        displayMetrics = ctx.resources.displayMetrics
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
}