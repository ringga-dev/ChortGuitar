package com.ringga.chortguitar.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.model.ChortRespon
import com.ringga.chortguitar.ui.home.LaguActivity
import kotlinx.android.synthetic.main.list_lagu.view.*

class ChortAdapter(
    private var chords: MutableList<ChortRespon>,
    private var context: Context
) : RecyclerView.Adapter<ChortAdapter.ViewHolder>() {

    fun setChords(r: List<ChortRespon>) {
        chords.clear()
        chords.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_lagu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(chords[position], context)

    override fun getItemCount() = chords.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chord: ChortRespon, context: Context) {
            itemView.tv_artis.text = chord.artis
            itemView.tv_judul.text = chord.judul
            itemView.cv_item.setOnClickListener {
                var i = Intent(context, LaguActivity::class.java)
                i.putExtra("kunci", chord.kunci)
                i.putExtra("artis", chord.artis)
                i.putExtra("judul", chord.judul)
                context.startActivity(i)
            }


        }
    }
}