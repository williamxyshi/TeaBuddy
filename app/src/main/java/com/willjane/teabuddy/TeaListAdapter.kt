package com.willjane.teabuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.willjane.teabuddy.viewmodels.MainActivityViewModel

class TeaListAdapter(val vm: MainActivityViewModel): RecyclerView.Adapter<TeaListAdapter.TeaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaViewHolder {

        return TeaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_tea_item, parent, false)).apply {
            teaName = itemView.findViewById(R.id.tea_list_name)
            teaImage = itemView.findViewById(R.id.tea_list_image)
        }
    }

    override fun onBindViewHolder(holder: TeaViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
       return vm.teaList.size
    }

    inner class TeaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var teaName: TextView
        lateinit var teaImage: ImageView

    }
}