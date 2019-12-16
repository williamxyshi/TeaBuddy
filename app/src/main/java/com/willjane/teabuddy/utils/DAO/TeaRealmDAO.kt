package com.willjane.teabuddy.utils.DAO

import android.util.Log
import com.willjane.teabuddy.utils.models.Tea
import io.realm.Realm

/**
 * this object manages all interactions the application
 * makes with the Realm Database related to TEA objects
 */
object TeaRealmDAO {

    private val realm = Realm.getDefaultInstance()

    /**
     * updates the realm tealist with the @param tea list
     */
    fun updateTeaList(teaList: List<Tea>){
        Log.d("realm", "teaList: ${teaList.size}")
        realm.executeTransaction {realm ->
            var i = 0
            teaList.forEach {tea ->
                realm.copyToRealmOrUpdate(tea)
                i++
            }
        }
    }

    /**
     * returns a tea from realm from the @param teaId
     */
    fun getTeaById(teaId: Int): Tea?{
        var tea: Tea?  = null
        realm.executeTransaction {

             tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
        }
        return tea
    }

    /**
     * returns a list of teas retrieved from the realm database
     */
    fun getTeaList(): List<Tea>{
        var teaList: List<Tea> = mutableListOf()
        realm.executeTransaction {
            teaList = it.where(Tea::class.java).findAll()
        }
        return teaList
    }

    /**
     * returns a list of teas that have been favourited by the user from
     * the realm database
     */
    fun getFavList(): List<Tea>{
        var teaList: List<Tea> = mutableListOf()
        realm.executeTransaction {
            teaList = it.where(Tea::class.java).equalTo("fav", true).findAll()
        }
        return teaList
    }

    /**
     * sets a tea as @param fav and saves it to the realm database
     */
    fun setFav(teaId: Int, fav: Boolean){
        var tea: Tea?  = null
        realm.executeTransaction {

            tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
            tea?.fav = fav
            it.copyToRealmOrUpdate(tea?:return@executeTransaction)
        }
    }

    /**
     * toggles  a tea as favourite
     */
    fun toggleFav(teaId: Int){
        realm.executeTransaction {

            val tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
            if(tea!= null) {
                tea.fav = !tea.fav
                it.copyToRealmOrUpdate(tea)
            }
        }
    }

    /**
     * returns whether or not the @param tea is a favourite
     */
    fun isFav(teaId: Int): Boolean{
        var isFav = false
        realm.executeTransaction {
            val tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
            if(tea?.fav == true) isFav = true
        }
        return isFav
    }

}