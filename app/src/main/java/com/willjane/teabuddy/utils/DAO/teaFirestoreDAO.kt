package com.willjane.teabuddy.utils.DAO

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class TeaFirestoreDao{

    private val firestore = FirebaseFirestore.getInstance()


    fun retrieveTeaList(vm: MainActivityViewModel){

        val teaList: MutableList<Tea> = mutableListOf()

        firestore.collection("teas")
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

    private fun formatToTea(hashMap: Map<String, Any>): Tea{

        return Tea( hashMap["name"] as String,
            ( hashMap["teaId"] as Long).toInt(),
            (hashMap["brewTemp"] as Long).toInt(),
            (hashMap["brewTime"] as Long).toInt(),
            (hashMap["brewAmount"] as Double).toInt(),
            hashMap["parentTea"] as String,
            hashMap["image"] as String)
    }

    companion object{
        private const val TAG = "teaFirestoreDAO"
    }

}