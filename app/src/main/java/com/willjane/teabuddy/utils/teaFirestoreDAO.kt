package com.willjane.teabuddy.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class TeaFirestoreDao{

    val firestore = FirebaseFirestore.getInstance()


    fun retrieveTeaList(vm: MainActivityViewModel){

        val teaList: MutableList<Tea> = mutableListOf()

        val task = firestore.collection("teas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    teaList.add(formatToTea(document.data))

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                vm.teaList = teaList
                Log.d(MainActivityViewModel.TAG, "teaList: ${vm.teaList}")
                vm.teaListUpdated.value = true
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun formatToTea(hashMap: Map<String, Any>): Tea{

        return Tea( hashMap["name"] as String,
            ( hashMap["teaId"] as Long).toInt(),
            (hashMap["brewTemp"] as Long).toInt(),
            (hashMap["brewTime"] as Long).toInt(),
            (hashMap["brewAmount"] as Double).toInt(),
            hashMap["parentTea"] as String)
    }

    companion object{
        private const val TAG = "teaFirestoreDAO"
    }

}