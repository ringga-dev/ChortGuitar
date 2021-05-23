package com.ringga.chortguitar.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.adapter.KeahlianAdapter
import com.ringga.chortguitar.data.model.user.Keahlian
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.ui.auth.LoginActivity
import com.ringga.chortguitar.ui.viewmodels.UserState
import com.ringga.chortguitar.ui.viewmodels.UserViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_lagu.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        islogin()
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getState().observer(this, Observer {
            handleUiState(it)
        })
        getData()
        setupRecler()
    }

    private fun getData() {
        val token = Constants.getToken(this)
        val id = Constants.getId(this)
        userViewModel.user(token, id)
    }

    private fun handleUiState(it: UserState) {
        when (it) {

            is UserState.Error -> {
                isloding(false)
                toast(it.err)
            }
            is UserState.ShoewToals -> toast(it.message)
            is UserState.Failed -> {
                isloding(false)
                toast(it.message)
            }
            is UserState.user -> {
                //TODO data bakaldi tampilkan di sini
                val data = it.userData
                Picasso.get().load(Constants.URL_ENDPOINT + "assets/profile/${data.image}").into(image_user)
                tv_nama.text = data.nama
                tv_no_pengenal.text = data.no_pengenal
                tv_no_phone.text = data.no_phone
                tv_email.text = data.email
                tv_alamat.text = data.alamat
//                toast(it.userData.toString())
                keahlian(data.keahlian)
                loading.visibility = View.GONE
            }

            is UserState.IsLoding -> isloding(it.state)
        }
    }

    private fun keahlian(keahlian: List<Keahlian>) {
        rv_keahlian.adapter?.let { adapter ->
            if (adapter is KeahlianAdapter) {
                adapter.setKeaahlian(keahlian)
            }
        }
    }

    private fun isloding(state: Boolean) {
        loading.isIndeterminate = state
    }

    private fun toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    private fun islogin() {
        if (Constants.getToken(this).equals("undefined")) {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }).also { finish() }
        }
    }

    private fun setupRecler() {
        rv_keahlian.apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity)
            adapter = KeahlianAdapter(mutableListOf(), this@ProfileActivity)

        }
    }
}