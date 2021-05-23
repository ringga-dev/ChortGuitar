package com.ringga.chortguitar.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ringga.chortguitar.data.api.ApiClient
import com.ringga.chortguitar.data.model.*
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.data.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuitarViewModel : ViewModel() {
    private var chorts = MutableLiveData<List<ChortRespon>>()
    private var wallpaper = MutableLiveData<List<WallpaperRespon>>()
    private var vidio = MutableLiveData<List<VidioRespon>>()
    private var chort = MutableLiveData<String>()
    private var state: SingleLiveEvent<AppState> = SingleLiveEvent()
    private var api = ApiClient.instance()

    fun chortLagu (lagu : String){
        chort.postValue(lagu)
    }

    fun fetchAllPost(token: String) {
        state.value = AppState.IsLoding(true)
        api.getLagu().enqueue(object : Callback<BaseListRespon<ChortRespon>> {
            override fun onResponse(
                call: Call<BaseListRespon<ChortRespon>>,
                response: Response<BaseListRespon<ChortRespon>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body() as BaseListRespon<ChortRespon>
                    if (body.stts == true) {
                        val r = body.data
                        chorts.postValue(r)
                    } else {
                        state.value = AppState.Error(body.pesan)
                    }
                    state.value = AppState.IsLoding(false)
                }
            }

            override fun onFailure(call: Call<BaseListRespon<ChortRespon>>, t: Throwable) {
                state.value = AppState.Error(t.message)
                state.value = AppState.IsLoding(false)
            }

        })
    }

    fun getWallpaper(token: String) {
        state.value = AppState.IsLoding(true)
        api.getWallpaper().enqueue(object : Callback<BaseListRespon<WallpaperRespon>> {
            override fun onResponse(
                call: Call<BaseListRespon<WallpaperRespon>>,
                response: Response<BaseListRespon<WallpaperRespon>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body() as BaseListRespon<WallpaperRespon>
                    if (body.stts == true) {
                        val r = body.data
                        wallpaper.postValue(r)
                    } else {
                        state.value = AppState.Error(body.pesan)
                    }
                    state.value = AppState.IsLoding(false)
                }
            }

            override fun onFailure(call: Call<BaseListRespon<WallpaperRespon>>, t: Throwable) {
                state.value = AppState.Error(t.message)
                state.value = AppState.IsLoding(false)
            }

        })
    }

    fun getVidio(token: String) {
        state.value = AppState.IsLoding(true)
        api.getVidio().enqueue(object : Callback<BaseListRespon<VidioRespon>> {
            override fun onResponse(
                call: Call<BaseListRespon<VidioRespon>>,
                response: Response<BaseListRespon<VidioRespon>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body() as BaseListRespon<VidioRespon>
                    if (body.stts == true) {
                        val r = body.data
                        vidio.postValue(r)
                    } else {
                        state.value = AppState.Error(body.pesan)
                    }
                    state.value = AppState.IsLoding(false)
                }
            }

            override fun onFailure(call: Call<BaseListRespon<VidioRespon>>, t: Throwable) {
                state.value = AppState.Error(t.message)
                state.value = AppState.IsLoding(false)
            }

        })
    }


//    fun fetchOnePost(token: String, id: String) {
//        state.value = AppState.IsLoding(true)
//        api.getOneLagu(id).enqueue(object : Callback<BaseRespon<ChortRespon>> {
//            override fun onResponse(
//                call: Call<BaseRespon<ChortRespon>>,
//                response: Response<BaseRespon<ChortRespon>>
//            ) {
//                if (response.isSuccessful) {
//                    val body = response.body() as BaseRespon<ChortRespon>
//                    if (body.stts == true) {
//                        val r = body.data
//                        chort.postValue(r)
//                    } else {
//                        state.value = AppState.Error(body.pesan)
//                    }
//                    state.value = AppState.IsLoding(false)
//                }
//            }
//
//            override fun onFailure(call: Call<BaseRespon<ChortRespon>>, t: Throwable) {
//                state.value = AppState.Error(t.message)
//                state.value = AppState.IsLoding(false)
//            }
//
//        })
//
//    }

    fun validate(title: String, conten: String): Boolean {
        state.value = AppState.Reset
        if (title != null) {
            if (title.isEmpty()) {
                state.value = AppState.ShowToast("title tidak boleh kosong")
                return false
            }
            if (title.length < 5) {
                state.value = AppState.GuitarValidation(title = "title kurang dari 5 karakter")
                return false
            }
        }

        if (conten != null) {
            if (conten.isEmpty()) {
                state.value = AppState.ShowToast("conten tidak boleh kosong")
                return false
            }
            if (conten.length < 5) {
                state.value = AppState.GuitarValidation(title = "conten kurang dari 5 karakter")
                return false
            }
        }

        if (title.isEmpty() || conten.isEmpty()) {
            state.value = AppState.ShowToast("Isi semua form")
            return false
        }
        return true
    }

    fun getAllLagu() = chorts
    fun getAllWallpaper() = wallpaper
    fun getAllVidio() = vidio
    fun getOneLagu() = chort
    fun getState() = state

}

sealed class AppState {
    data class ShowToast(var message: String) : AppState()
    data class IsLoding(var state: Boolean = false) : AppState()
    data class GuitarValidation(
        var title: String? = null,
        var content: String? = null
    ) : AppState()

    data class Error(var err: String?) : AppState()
    data class IsSuccess(var what: Int? = null) : AppState()
    object Reset : AppState()
}
