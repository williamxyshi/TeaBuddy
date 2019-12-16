package com.willjane.teabuddy.utils.DAO

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.willjane.teabuddy.utils.models.CommunityPost
import com.willjane.teabuddy.utils.models.Tea
import com.willjane.teabuddy.utils.models.TeaBuddyUser
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.tasks.await

class TeaFirestoreDao{

    private val firestore = FirebaseFirestore.getInstance()

    private fun getUserHashmap(user: TeaBuddyUser):HashMap<String, Any?>{
        val userToAdd = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "uid" to user.uid,
            "photoUrl" to user.photoUrl.toString(),
            "emailVerified" to user.emailVerified
        )

        return userToAdd

    }

    private fun getPostHashmap(post: CommunityPost):HashMap<String, Any?>{
        val userToAdd = hashMapOf(
            "postTitle" to post.postTitle,
            "postDescription" to post.postDesc,
            "posterURL" to post.posterURL,
            "posterName" to post.posterName,
            "postHearts" to post.postHearts,
            "likedUsers" to post.likedUsers
        )

        return userToAdd

    }

    suspend fun getCommunityPosts(vm: MainActivityViewModel): List<CommunityPost>{

        val postsList: MutableList<CommunityPost> = mutableListOf()

        val posts = firestore.collection("community_posts").get().await()
        posts.documents.forEach { doc ->
                val post = formatToPost(doc.data?: hashMapOf())
                post.postId = doc.id

                val map: Map<String, Any> = doc.data?: hashMapOf()
                val usersThatLIked:Map<String, Boolean> = map["likedUsers"]  as Map<String, Boolean>
                post.likedUsers = usersThatLIked as HashMap<String, Boolean>
                postsList.add(post)
                Log.d(TAG, "users that liked: ${post.likedUsers}")
        }

        return postsList
    }

    /**
     * updates a specific community post in the firestore database
     * should be called from launch
     */
    suspend fun updateCommunityPost(post: CommunityPost){

        val userToUpdate = getPostHashmap(post)

        firestore.collection("community_posts").document(post.postId?:"").set(userToUpdate).await()
        Log.d(TAG, "community post updated")
    }


    fun updateUser(user: TeaBuddyUser){

        val userToUpdate = getUserHashmap(user)

        firestore.collection("users").document(user.uid).set(userToUpdate).addOnSuccessListener {
            Log.d(TAG, "user successfully updated")
        }.addOnFailureListener {
            Log.w(TAG, "user failed to update", it)
        }
    }

    fun addUser(user: TeaBuddyUser){

        /**
         * Logging
         */
        Log.d(TAG, "user to add: ${user.email}, uid: ${user.uid}")
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "user size: ${result.size()}")
            }


        val docRef = firestore.collection("users").document(user.uid)

        docRef.get().addOnSuccessListener {document ->
            if(document.exists()){
                Log.d(TAG, "user already exists")
            } else {
                val userToAdd = getUserHashmap(user)

                firestore.collection("users").document(user.uid).set(userToAdd).addOnSuccessListener {
                    Log.d(TAG, "User successfully added to firestore")
                } . addOnFailureListener {
                    Log.w(TAG, "error adding user", it)
                }
            }
        }
    }


    fun retrieveTeaList(vm: MainActivityViewModel){

        val teaList: MutableList<Tea> = mutableListOf()

        firestore.collection("teas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val tea = formatToTea(document.data)
                    tea.fav = TeaRealmDAO.isFav(tea.teaId)

                    teaList.add(tea)

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                vm.fireStoreTeaList = teaList
                Log.d(MainActivityViewModel.TAG, "teaList: ${vm.fireStoreTeaList}")
                vm.teaListUpdated.value = true
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun formatToTea(hashMap: Map<String, Any>): Tea{

        return Tea( hashMap["name"] as String,
            ( hashMap["teaId"] as Long).toInt(),
            (hashMap["brewTemp"] as Long).toInt(),
            (hashMap["brewTime"] as Long).toInt(),
            (hashMap["brewAmount"] as Double).toInt(),
            hashMap["parentTea"] as String,
            hashMap["image"] as String)
    }

    private fun formatToPost(hashMap: Map<String, Any>): CommunityPost{
        return CommunityPost(hashMap["postTitle"] as String,
            hashMap["postDescription"] as String,
            hashMap["posterURL"] as String,
            hashMap["posterName"] as String,
            (hashMap["postHearts"] as Long).toInt()
            )
    }

    companion object{
        private const val TAG = "teaFirestoreDAO"
    }

}