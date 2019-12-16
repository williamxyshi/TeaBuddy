package com.willjane.teabuddy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willjane.teabuddy.R
import com.willjane.teabuddy.utils.models.CommunityPost
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CommunityPostAdapter(private val vm: MainActivityViewModel, private val context: Context): RecyclerView.Adapter<CommunityPostAdapter.CommunityPostViewHolder>() {

    interface CommunityPostInterface{
        fun showPostPopup(anchorView: View, post: CommunityPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostViewHolder {
        return CommunityPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_community_post, parent, false)).apply {
            postTitle = itemView.findViewById(R.id.postTitle)
            userName = itemView.findViewById(R.id.userName)
            userImage = itemView.findViewById(R.id.postUserImage)
            favHeart = itemView.findViewById(R.id.heart)
            heartCount = itemView.findViewById(R.id.heartCount)

        }
    }

    override fun onBindViewHolder(holder: CommunityPostViewHolder, position: Int) {
        val post = vm.communityPosts[position]

        //current user uid is initialized because realm forbids accessing it from background threads
        val currentUserUid = vm.currentUser.value?.uid

        //checks if the current post has already been liked by the user
        val likedByCurrentUser = post.likedUsers?.contains(currentUserUid)

        //sets the colour of the heart
        if(likedByCurrentUser == true){
            holder.favHeart.setColorFilter(context.resources.getColor(R.color.red))
        } else {
            holder.favHeart.setColorFilter(context.resources.getColor(R.color.white))
        }

        holder.postTitle.text = post.postTitle
        holder.userName.text = post.posterName

        //TODO: add hearts feature, and icons to comment and stuff


//            holder.teaImage.setImageBitmap(urlToBitmap(tea.imageUrl))
//        DownloadImageTask(holder.teaImage).execute(tea.imageUrl)
        Glide.with(context).load(post.posterURL).into(holder.userImage)

        holder.favHeart.setOnClickListener {
            //handles liking and unliking
            //we increase/decrease the hearts immediately so the UI is less sluggish
            if(likedByCurrentUser == true) {

                GlobalScope.launch {
                    vm.communityPosts[position].postHearts -= 1
                    vm.communityPosts[position].likedUsers?.remove(currentUserUid)
                    vm.teaFirestoreDAO.updateCommunityPost(vm.communityPosts[position])
                    vm.refreshPostsList()
                }

            } else {
                GlobalScope.launch {

                    vm.communityPosts[position].postHearts += 1
                    vm.communityPosts[position].likedUsers?.put(currentUserUid?:"", true)
                    vm.teaFirestoreDAO.updateCommunityPost(vm.communityPosts[position])
                    vm.refreshPostsList()
                }

            }

            notifyDataSetChanged()
        }
        holder.heartCount.text = post.postHearts.toString()

        holder.postTitle.setOnClickListener {
           
        }


    }

    override fun getItemCount(): Int {
        return vm.communityPosts.size
    }

    inner class CommunityPostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var postTitle: TextView
        lateinit var userName: TextView
        lateinit var userImage: ImageView

        lateinit var favHeart : ImageView
        lateinit var heartCount : TextView

    }

    companion object{
        const val TAG = "EncyclopediaListAdapter"
    }
}