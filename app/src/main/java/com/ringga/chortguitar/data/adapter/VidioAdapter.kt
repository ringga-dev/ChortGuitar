package com.ringga.chortguitar.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.model.VidioRespon
import com.ringga.chortguitar.ui.home.PlayVidioActivity
import com.ringga.chortguitar.ui.home.ThemeActivity
import kotlinx.android.synthetic.main.item_vidio.view.*

class VidioAdapter(
    private var vidio: MutableList<VidioRespon>,
    private var context: Context
) : RecyclerView.Adapter<VidioAdapter.ViewHolder>() {

    fun setVidio(r: List<VidioRespon>) {
        vidio.clear()
        vidio.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vidio, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(vidio[position], context)

    override fun getItemCount() = vidio.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(vidio: VidioRespon, context: Context) {

            itemView.tv_nama.text = vidio.nama
            itemView.cv_item.setOnClickListener {
//                Toast.makeText(context, wallpaper.nama, Toast.LENGTH_SHORT).show()
                var i = Intent(context, PlayVidioActivity::class.java)
                i.putExtra("URL", vidio.vidio)
                context.startActivity(i)
            }
        }
    }
}