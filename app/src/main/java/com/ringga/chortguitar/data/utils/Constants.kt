package com.ringga.chortguitar.data.utils

import android.content.Context

class Constants {

    companion object {
        const val URL_ENDPOINT = "http://192.168.43.8/sekolah-saya/public/"

        const val URL_ASSETS ="assets/profile/"

        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            val token = pref.getString("TOKEN", "undefined")
            return token!!
        }

        fun getId(context: Context): String {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            val token = pref.getString("ID", "undefined")
            return token!!
        }

        fun setToken(context: Context, token: String, id:String) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                putString("ID", id)
                apply()
            }
        }

        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun isVAlidEmail(email: String) =
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        fun isVAlidPassword(pass: String) = pass.length > 3
    }
}