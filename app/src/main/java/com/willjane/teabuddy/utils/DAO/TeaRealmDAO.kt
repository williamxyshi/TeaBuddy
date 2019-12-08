package com.willjane.teabuddy.utils.DAO

import com.willjane.teabuddy.utils.models.Tea
import io.realm.Realm

object TeaRealmDAO {

    val realm = Realm.getDefaultInstance()

    fun updateTeaList(teaList: List<Tea>){
        realm.executeTransaction {realm ->
            teaList.forEach {tea ->
                realm.copyToRealmOrUpdate(tea)
            }
        }
    }

    fun getTeaById(teaId: Int): Tea?{
        var tea: Tea?  = null
        realm.executeTransaction {

             tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
        }
        return tea
    }

    fun getTeaList(): List<Tea>{
        var teaList: List<Tea> = mutableListOf()
        realm.executeTransaction {
            teaList = it.where(Tea::class.java).findAll()
        }
        return teaList
    }

    fun getFavList(): List<Tea>{
        var teaList: List<Tea> = mutableListOf()
        realm.executeTransaction {
            teaList = it.where(Tea::class.java).equalTo("fav", true).findAll()
        }
        return teaList
    }

    fun setFav(teaId: Int, fav: Boolean){
        var tea: Tea?  = null
        realm.executeTransaction {

            tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
            tea?.fav = fav
            it.copyToRealmOrUpdate(tea?:return@executeTransaction)
        }
    }

    fun toggleFav(teaId: Int){
        realm.executeTransaction {

            val tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
            if(tea!= null) {
                tea.fav = !tea.fav
                it.copyToRealmOrUpdate(tea)
            }
        }
    }

    fun isFav(teaId: Int): Boolean{
        var isFav = false
        realm.executeTransaction {
            val tea = it.where(Tea::class.java).equalTo("teaId", teaId).findFirst()
            if(tea?.fav == true) isFav = true
        }
        return isFav
    }

}