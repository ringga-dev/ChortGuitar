package com.ringga.chortguitar.data.model

import com.google.gson.annotations.SerializedName

data class ChortRespon(
    @SerializedName("id") var id: Int?=null,
    @SerializedName("artis") var artis: String?=null,
    @SerializedName("judul") var judul: String?=null,
    @SerializedName("kunci") var kunci: String?=null
)