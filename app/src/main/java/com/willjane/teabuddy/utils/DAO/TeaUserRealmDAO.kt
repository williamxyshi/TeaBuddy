package com.willjane.teabuddy.utils.DAO

import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.utils.models.TeaBuddyUser
import io.realm.Realm

object TeaUserRealmDAO {
    private val realm = Realm.getDefaultInstance()

    fun updateTeaBuddyUser(user: TeaBuddyUser){
        realm.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(user)
        }
    }

    fun getUserByUid(uid: String): TeaBuddyUser? {
        var user: TeaBuddyUser? = null
        realm.executeTransaction { realm ->
            user = realm.where(TeaBuddyUser::class.java).equalTo("uid", uid).findFirst()
        }
        return user
    }

    fun getUser(): TeaBuddyUser? {
        var user: TeaBuddyUser? = null
        realm.executeTransaction { realm ->
            user = realm.where(TeaBuddyUser::class.java).findFirst()
        }
        return user
    }

}