package com.willjane.teabuddy.utils.DAO

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.utils.models.TeaBuddyUser
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlin.coroutines.CoroutineContext

class TeaFirestoreDao{

    private val firestore = FirebaseFirestore.getInstance()

    fun getUserHashmap(user: TeaBuddyUser):HashMap<String, Any?>{
        val userToAdd = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "uid" to user.uid,
            "photoUrl" to user.photoUrl.toString(),
            "emailVerified" to user.emailVerified
        )

        return userToAdd

    }


    fun updateUser(user: TeaBuddyUser){

        val userToUpdate = getUserHashmap(user)

        firestore.collection("users").document(user.uid).set(userToUpdate).addOnSuccessListener {
            Log.d(TAG, "user successfully updated")
        }.addOnFailureListener {
            Log.w(TAG, "user failed to update", it)
        }
    }

    fun addUser(user: TeaBuddyUser){

        val docRef = firestore.collection("users").document(user.uid)

        docRef.get().addOnSuccessListener {document ->
            if(document != null){
                Log.d(TAG, "user already exists")
            } else {
                val userToAdd = getUserHashmap(user)

                firestore.collection("users").document(user.uid).set(userToAdd).addOnSuccessListener {
                    Log.d(TAG, "User successfully added to firestore")
                } . addOnFailureListener {
                    Log.w(TAG, "error adding user", it)
                }
            }
        }
    }


    fun retrieveTeaList(vm: MainActivityViewModel){

        val teaList: MutableList<Tea> = mutableListOf()

        firestore.collection("teas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val tea = formatToTea(document.data)
                    tea.fav = TeaRealmDAO.isFav(tea.teaId)

                    teaList.add(tea)

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                vm.fireStoreTeaList = teaList
                Log.d(MainActivityViewModel.TAG, "teaList: ${vm.fireStoreTeaList}")
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