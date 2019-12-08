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
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class EncyclopediaListAdapter(private val vm: MainActivityViewModel, private val context: Context): RecyclerView.Adapter<EncyclopediaListAdapter.EncyclopediaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncyclopediaViewHolder {

        return EncyclopediaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_encyclopedia_item, parent, false)).apply {
            teaName = itemView.findViewById(R.id.encyclopedia_list_text)
            teaImage = itemView.findViewById(R.id.encyclopedia_list_image)
        }
    }

    override fun onBindViewHolder(holder: EncyclopediaViewHolder, position: Int) {
        val tea = vm.teaList[position]
        holder.teaName.text = tea.teaName

//            holder.teaImage.setImageBitmap(urlToBitmap(tea.imageUrl))
//        DownloadImageTask(holder.teaImage).execute(tea.imageUrl)
        Glide.with(context).load(tea.imageUrl).into(holder.teaImage)
    }

    override fun getItemCount(): Int {
        return vm.teaList.size
    }

    inner class EncyclopediaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var teaName: TextView
        lateinit var teaImage: ImageView

    }

    companion object{
        const val TAG = "EncyclopediaListAdapter"
    }
}