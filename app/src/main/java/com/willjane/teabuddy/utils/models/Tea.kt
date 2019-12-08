package com.willjane.teabuddy.utils.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//Tea class
open class Tea(_name: String = "green", _id: Int = -1,
                 _temp: Int  = -1, _time: Int = -1,
                 _amount: Int = -1, _parentTea: String = "green",
                 _imageUrl: String = "") : RealmObject(){



    @PrimaryKey
    var teaId = _id


    var teaName = _name
    var parentTea = _parentTea
    var imageUrl = _imageUrl
    var brewTemp = _temp
    var brewTime = _time
    var brewAmount = _amount

    var fav = false
}