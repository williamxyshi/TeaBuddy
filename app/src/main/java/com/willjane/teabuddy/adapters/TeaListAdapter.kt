package com.willjane.teabuddy.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willjane.teabuddy.R
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import java.net.URL

class TeaListAdapter(private val vm: MainActivityViewModel, private val context: Context): RecyclerView.Adapter<TeaListAdapter.TeaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaViewHolder {

        return TeaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_tea_item, parent, false)).apply {
            teaName = itemView.findViewById(R.id.tea_list_name)
            teaImage = itemView.findViewById(R.id.tea_list_image)
        }
    }

    override fun onBindViewHolder(holder: TeaViewHolder, position: Int) {
        val tea = vm.teaList[position]
        holder.teaName.text = tea.teaName

//            holder.teaImage.setImageBitmap(urlToBitmap(tea.imageUrl))
//        DownloadImageTask(holder.teaImage).execute(tea.imageUrl)
        Glide.with(context).load(tea.imageUrl).into(holder.teaImage)

//        picassoo.load("https://cdn.shopify.com/s/files/1/1329/3455/products/Ancient_Forest_Tea_burned.jpg?v=1489465824").placeholder(R.drawable.ic_teacup).into(holder.teaImage)
    }

    override fun getItemCount(): Int {
       return vm.teaList.size
    }

    private fun urlToBitmap(url: String): Bitmap {
        val url = URL(url)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    inner class TeaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var teaName: TextView
        lateinit var teaImage: ImageView

    }

    companion object{
        const val TAG = "TeaListAdapter"
    }
}