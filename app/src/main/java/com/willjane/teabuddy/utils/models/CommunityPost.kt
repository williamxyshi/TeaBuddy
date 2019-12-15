package com.willjane.teabuddy.utils.models

//this communitypost class handles all info needed to inflate a post view
class CommunityPost(title: String, desc: String,  _posterURL: String?, _posterName: String, hearts: Int) {

    //user information
    val posterURL: String? = _posterURL
    val posterName: String? = _posterName

    //title: the title of the post
    //desc: The actual body- sort of how reddit does it
    val postTitle: String = title
    val postDesc: String = title

    //TODO: not supported - but allow the ability to upload images
    val postImage: String? = null

    //number of "likes" a post gets
    var postHearts: Int = hearts

    var postId: String? = null

}