package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.willjane.teabuddy.R
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class TeaInformationFragment(private val tea: Tea) : Fragment() {

    private lateinit var vm: MainActivityViewModel

    private lateinit var teaImage: ImageView
    private lateinit var teaName: TextView
    private lateinit var brewTemp: TextView
    private lateinit var brewAmount: TextView
    private lateinit var brewTime: TextView
    private lateinit var teaFamilyText: TextView
    private lateinit var brewBtn: Button
    private lateinit var tempImage: ImageView
    private lateinit var scaleImage: ImageView
    private lateinit var timeImage: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "creating tea information fragment")
        val rootView = inflater.inflate(R.layout.fragment_tea_information, container, false) as ViewGroup

        initialize()

        teaImage = rootView.findViewById(R.id.teaImage)
        teaName = rootView.findViewById(R.id.postName)
        brewTemp = rootView.findViewById(R.id.brewTemp)
        brewAmount = rootView.findViewById(R.id.brewAmount)
        brewTime = rootView.findViewById(R.id.brewTime)
        teaFamilyText = rootView.findViewById(R.id.teaFamilyText)
        brewBtn = rootView.findViewById(R.id.brewBtn)
        tempImage = rootView.findViewById(R.id.thermometer)
        scaleImage = rootView.findViewById(R.id.scale)
        timeImage = rootView.findViewById(R.id.time)
        TooltipCompat.setTooltipText(tempImage, "Brew temperature(°C)")
        TooltipCompat.setTooltipText(scaleImage, "Brew amount(grams)")
        TooltipCompat.setTooltipText(timeImage, "Brew time(minutes)")

        Log.d(TAG, "binding data for tea info fragment")

        Glide.with(context?:return null).load(tea.imageUrl).into(teaImage)
        teaName.text = tea.teaName
        brewTemp.text = tea.brewTemp.toString()
        brewAmount.text = tea.brewAmount.toString()
        brewTime.text = tea.brewTime.toString()

        teaFamilyText.text = (tea.parentTea + " Tea").capitalize()

        brewBtn.setOnClickListener {
            vm.currentTeaTime.value = (tea.brewTime * 60000).toLong()
            vm.timerLength.value = (tea.brewTime * 60000).toLong()
        }


        return rootView
    }

    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.TEA_INFO_PAGE



    }

    companion object{
        const val TAG = "TeaInfoFragment"
    }
}

