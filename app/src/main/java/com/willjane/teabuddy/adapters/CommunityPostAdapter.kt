package com.willjane.teabuddy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willjane.teabuddy.R
import com.willjane.teabuddy.utils.DAO.TeaRealmDAO
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class CommunityPostAdapter(private val vm: MainActivityViewModel, private val context: Context): RecyclerView.Adapter<CommunityPostAdapter.CommunityPostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostViewHolder {
        return CommunityPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_community_post, parent, false)).apply {
            postTitle = itemView.findViewById(R.id.postTitle)
            userName = itemView.findViewById(R.id.userName)
            userImage = itemView.findViewById(R.id.postUserImage)
            favStar = itemView.findViewById(R.id.favStar)

        }
    }

    override fun onBindViewHolder(holder: CommunityPostViewHolder, position: Int) {
        val post = vm.communityPosts[position]
        holder.postTitle.text = post.postTitle

        holder.userName.text = post.posterName

        //TODO: add hearts feature, and icons to comment and stuff


//            holder.teaImage.setImageBitmap(urlToBitmap(tea.imageUrl))
//        DownloadImageTask(holder.teaImage).execute(tea.imageUrl)
        Glide.with(context).load(post.posterURL).into(holder.userImage)


    }

    override fun getItemCount(): Int {
        return vm.communityPosts.size
    }

    inner class CommunityPostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var postTitle: TextView
        lateinit var userName: TextView
        lateinit var userImage: ImageView

        lateinit var favStar : ImageView

    }

    companion object{
        const val TAG = "EncyclopediaListAdapter"
    }
}