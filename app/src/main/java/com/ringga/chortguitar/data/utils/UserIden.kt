package com.ringga.chortguitar.data.utils

import android.content.Context

class UserIden {

    companion object {


        fun getId(context: Context): String {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            val token = pref.getString("IDENTITAS", "undefined")
            return token!!
        }

        fun setId(context: Context, id: String) {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("IDENTITAS", id)
                apply()
            }
        }

        fun clearId(context: Context) {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }
    }
}