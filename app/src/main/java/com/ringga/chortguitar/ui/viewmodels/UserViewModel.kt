package com.ringga.chortguitar.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ringga.chortguitar.data.utils.Constants
import androidx.lifecycle.ViewModel
import com.ringga.chortguitar.data.api.ApiClient
import com.ringga.chortguitar.data.model.BaseRespon
import com.ringga.chortguitar.data.model.UserRespon
import com.ringga.chortguitar.data.model.user.UserDataRespon
import com.ringga.chortguitar.data.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private var state: SingleLiveEvent<UserState> = SingleLiveEvent()
    private var api = ApiClient.instance()
    private var user = MutableLiveData<UserDataRespon>()

    fun login(email: String, password: String) {
        state.value = UserState.IsLoding(true)
        api.loginUser(email, password).enqueue(object : Callback<BaseRespon<UserRespon>> {
            override fun onResponse(
                call: Call<BaseRespon<UserRespon>>,
                response: Response<BaseRespon<UserRespon>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body() as BaseRespon<UserRespon>
                    if (body.stts == true) {
                        state.value = UserState.Success(body.data!!)
                    } else {
                        state.value = UserState.Failed(body.pesan!!)
                    }
                } else {
                    state.value = UserState.Failed("terjadi kesalahan saat login")
                }
                state.value = UserState.IsLoding(false)
            }

            override fun onFailure(call: Call<BaseRespon<UserRespon>>, t: Throwable) {
                state.value = UserState.Error(t.message!!)
            }

        })
    }


    fun user(token:String, id: String) {
        state.value = UserState.IsLoding(true)
        api.getUser(token,id, ).enqueue(object : Callback<BaseRespon<UserDataRespon>> {
            override fun onResponse(
                call: Call<BaseRespon<UserDataRespon>>,
                response: Response<BaseRespon<UserDataRespon>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body() as BaseRespon<UserDataRespon>
                    if (body.stts == true) {
                        state.value =UserState.user(body.data!!)
//                        state.value = UserState.Success(body.data!!)
//                        "Bearer${body.data?.token}"
                    } else {
                        state.value = UserState.Failed(body.pesan!!)
                    }
                } else {
                    state.value = UserState.Failed("terjadi kesalahan saat register")
                }
                state.value = UserState.IsLoding(false)
            }

            override fun onFailure(call: Call<BaseRespon<UserDataRespon>>, t: Throwable) {
                state.value = UserState.Error(t.message!!)
            }

        })
    }

    fun register(nama:String, email: String, password: String) {
        state.value = UserState.IsLoding(true)
        api.registerUser(nama,email, password).enqueue(object : Callback<BaseRespon<UserRespon>> {
            override fun onResponse(
                call: Call<BaseRespon<UserRespon>>,
                response: Response<BaseRespon<UserRespon>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body() as BaseRespon<UserRespon>
                    if (body.stts == true) {
                        state.value = UserState.Success(body.data!!)
                        "Bearer${body.data?.token}"
                    } else {
                        state.value = UserState.Failed(body.pesan!!)
                    }
                } else {
                    state.value = UserState.Failed("terjadi kesalahan saat register")
                }
                state.value = UserState.IsLoding(false)
            }

            override fun onFailure(call: Call<BaseRespon<UserRespon>>, t: Throwable) {
                state.value = UserState.Error(t.message!!)
            }

        })
    }

    fun validate(name:String? = null, email: String, password: String):Boolean{

        state.value = UserState.Reset
        if (name !=  null){
            state.value = UserState.ShoewToals("nama anda kosong")
            if (name.length < 5 ){
                state.value = UserState.ShoewToals("nama anda terlalu pendek")
                return false
            }
            return false
        }

        if (email.isEmpty() || password.isEmpty()){
            state.value = UserState.ShoewToals("mohon isi semua form")
            return false
        }
        if(!Constants.isVAlidEmail(email)){
            state.value = UserState.ShoewToals("email tidak valid")
            return false
        }

        if (!Constants.isVAlidPassword(password)){
            state.value = UserState.ShoewToals("password tidak valid")
            return false
        }
        return true
    }

    fun getState() = state

    fun getUserData() =user
}

sealed class UserState {
    data class Error(var err: String) : UserState()
    data class ShoewToals(var message: String) : UserState()
    data class Validate(
        var name: String? = null,
        var email: String? = null,
        var password: String? = null
    ) : UserState()

    data class IsLoding(var state: Boolean = false) : UserState()
    data class user(var userData: UserDataRespon) : UserState()
    data class Success(var data : UserRespon) : UserState()
    data class Failed(var message: String) : UserState()
    object Reset : UserState()
}