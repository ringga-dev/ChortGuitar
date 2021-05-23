package com.ringga.chortguitar.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.model.WallpaperRespon
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.ui.home.ThemeActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_wallpaper.view.*

class WallpaperAdapter(
    private var wallpaper: MutableList<WallpaperRespon>,
    private var context: Context
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    fun setWallpapers(r: List<WallpaperRespon>) {
        wallpaper.clear()
        wallpaper.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_wallpaper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(wallpaper[position], context)

    override fun getItemCount() = wallpaper.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wallpaper: WallpaperRespon, context: Context) {
            Picasso.get().load(Constants.URL_ENDPOINT + "assets/wallpaper/${wallpaper.image}")
                .into(itemView.iv_image)
            itemView.tv_nama.text = wallpaper.nama
            itemView.cv_item.setOnClickListener {

//                Toast.makeText(context, wallpaper.nama, Toast.LENGTH_SHORT).show()
                var i = Intent(context, ThemeActivity::class.java)
                i.putExtra("PATH_PICTURE", wallpaper.image)
                context.startActivity(i)
            }
        }
    }
}