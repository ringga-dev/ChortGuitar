package com.ringga.chortguitar.data.model

import com.google.gson.annotations.SerializedName

data class UserRespon(
    @SerializedName("id") var id: String?=null,
    @SerializedName("no_pengenal") var no_pengenal: String?=null,
    @SerializedName("tgl_buat") var tgl_buat: String?=null,
    @SerializedName("tgl_update") var tgl_update: String?=null,
    @SerializedName("token") var token: String?=null
)