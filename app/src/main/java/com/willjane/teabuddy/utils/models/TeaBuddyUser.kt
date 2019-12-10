package com.willjane.teabuddy.utils.models

import android.net.Uri
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TeaBuddyUser(_uid: String = ""): RealmObject(){

    @PrimaryKey
    var uid: String = _uid

    var name: String? = null
    var email: String? = null
    var photoUrl: String? = null
    var emailVerified: Boolean = false



    var isNew = true

}