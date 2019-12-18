package com.willjane.teabuddy.utils.DAO

import com.google.firebase.storage.FirebaseStorage

/**
 * Handles all interactions between TeaBuddy and firebase storage
 */
object TeaCloudStorageDAO {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference




}