package com.willjane.teabuddy.utils.models

//Tea class
class Tea(_name: String, _id: Int, _temp: Int, _time: Int, _amount: Int, _parentTea: String) {

    val teaName = _name
    val parentTea = _parentTea
    val teaId = _id

    val brewTemp = _temp
    val brewTime = _time
    val brewAmount = _amount
}