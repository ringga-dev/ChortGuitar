package com.ringga.chortguitar.data.model

import com.google.gson.annotations.SerializedName

data class BaseRespon<T>(
    @SerializedName("stts") var stts: Boolean?=null,
    @SerializedName("pesan") var pesan: String?=null,
    @SerializedName("data") var data: T? =null
)

data class BaseListRespon<T>(
    @SerializedName("stts") var stts: Boolean?=null,
    @SerializedName("pesan") var pesan: String?=null,
    @SerializedName("data") var data: List<T>? =null
)