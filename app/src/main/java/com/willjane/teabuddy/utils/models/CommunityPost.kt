package com.willjane.teabuddy.utils.models

//this communitypost class handles all info needed to inflate a post view
class CommunityPost(title: String, desc: String,  teaUser: TeaBuddyUser, hearts: Int) {

    //user information
    val posterURL: String? = teaUser.photoUrl
    val posterName: String? = teaUser.name

    //title: the title of the post
    //desc: The actual body- sort of how reddit does it
    val postTitle: String = title
    val postDesc: String = title

    //TODO: not supported - but allow the ability to upload images
    val postImage: String? = null

    //number of "likes" a post gets
    val postHearts: Int = hearts



}