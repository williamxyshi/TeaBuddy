package com.willjane.teabuddy.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.rotationMatrix
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.willjane.teabuddy.R
import com.willjane.teabuddy.TeaListAdapter
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_encyclopedia.*
import java.lang.Exception

class EncyclopediaFragment: Fragment() {

    private lateinit var  vm:  MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_encyclopedia, container, false) as ViewGroup

        initialize()

        val picasso = Picasso.Builder(context).listener(object : Picasso.Listener {
            override fun onImageLoadFailed(picasso: Picasso?, uri: Uri?, exception: Exception?) {
                Log.d(TeaListAdapter.TAG, "Image load failts")
                exception?.printStackTrace()
            }
        }).build()

//        picasso.load("https://cdn.shopify.com/s/files/1/1329/3455/products/Ancient_Forest_Tea_burned.jpg?v=1489465824").placeholder(R.drawable.ic_teacup).into(test)

        return rootView
    }
    private fun initialize(){
        vm = ViewModelProviders.of(activity?:return).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.TEA_INFO



    }

    companion object{
        const val TAG = "TeaInfoFragment"
    }
}