package com.ringga.chortguitar.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.model.user.Keahlian
import kotlinx.android.synthetic.main.item_keahlian.view.*

class KeahlianAdapter(
    private var keahlian: MutableList<Keahlian>,
    private var context: Context
) : RecyclerView.Adapter<KeahlianAdapter.ViewHolder>() {

    fun setKeaahlian(r: List<Keahlian>) {
        keahlian.clear()
        keahlian.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_keahlian, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
    holder.bind(keahlian[position], context)


    override fun getItemCount()= keahlian.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(keahlian: Keahlian, context: Context) {
            itemView.tv_nameK.text = keahlian.nama
            itemView.pb_prog.progress = keahlian.persentase.toInt()
        }
    }
}