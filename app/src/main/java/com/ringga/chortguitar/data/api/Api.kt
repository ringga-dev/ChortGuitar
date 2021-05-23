package com.ringga.chortguitar.data.api

import com.ringga.chortguitar.data.model.*
import com.ringga.chortguitar.data.model.user.UserDataRespon
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("userapi/listlagu")
    fun getLagu(): Call<BaseListRespon<ChortRespon>>

    @GET("userapi/listlagu/{id}")
    fun getOneLagu(
        @Path("id") id: String
    ):Call<BaseRespon<ChortRespon>>

    //contoh yang pakai Authorization
    @GET("userapi/listlagu/{id}")
    fun _gettoken(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ):Call<BaseRespon<ChortRespon>>

    @FormUrlEncoded
    @POST("userapi/band")
    fun postBand(
        @Field("name") name:String
    ): Call<BaseRespon<Any>>

    @DELETE("userapi/band/{id}")
    fun deleteBand(
        @Path("id") id: String
    ):Call<BaseRespon<Any>>

    @FormUrlEncoded
    @POST("userapi/index")
    fun loginUser(
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<BaseRespon<UserRespon>>

    @FormUrlEncoded
    @POST("userapi/register")
    fun registerUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") passwprd:String
    ): Call<BaseRespon<UserRespon>>

    @FormUrlEncoded
    @POST("userapi/user_data")
    fun getUser(
        @Field("token") token:String,
        @Field("no_pengenal") id:String
    ): Call<BaseRespon<UserDataRespon>>

    @GET("userapi/wallpaper")
    fun getWallpaper(): Call<BaseListRespon<WallpaperRespon>>

    @GET("userapi/vidio")
    fun getVidio(): Call<BaseListRespon<VidioRespon>>


}