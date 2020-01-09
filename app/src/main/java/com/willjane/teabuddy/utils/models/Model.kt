package com.willjane.teabuddy.utils.models

object Model {
    data class Result(val query: Query)
    data class Query(val search: List<Search>)
    data class Search(val snippet: String)
}