package com.willjane.teabuddy.utils.DAO

import android.content.Context
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

object TeaUserAuthDAO {

    private val firebaseAuth = FirebaseAuth.getInstance()


    fun isUserSignedIn(): Boolean{
        Log.d(TAG, "is user signed in: ${firebaseAuth.currentUser != null}")

        return firebaseAuth.currentUser != null
    }

    fun signUserOut(context: Context){
        AuthUI.getInstance().signOut(context)
    }

    fun getCurrentUser(): FirebaseUser?{
        return firebaseAuth.currentUser
    }

    private const val TAG = "TeaUserAuthDAO"

}