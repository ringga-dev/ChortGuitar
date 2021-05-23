package com.ringga.chortguitar.data.model.user

data class UserDataRespon(
    val alamat: String,
    val email: String,
    val image: String,
    val keahlian: List<Keahlian>,
    val nama: String,
    val no_pengenal: String,
    val no_phone: String
)