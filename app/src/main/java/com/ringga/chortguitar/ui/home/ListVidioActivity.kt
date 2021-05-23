package com.ringga.chortguitar.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.adapter.ChortAdapter
import com.ringga.chortguitar.data.adapter.VidioAdapter
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.ui.auth.LoginActivity
import com.ringga.chortguitar.ui.viewmodels.AppState
import com.ringga.chortguitar.ui.viewmodels.GuitarViewModel
import kotlinx.android.synthetic.main.activity_list_vidio.*

class ListVidioActivity : AppCompatActivity() {

    private lateinit var viewModel: GuitarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_vidio)
        islogin()
        setupRecler()
        viewModel = ViewModelProviders.of(this).get(GuitarViewModel::class.java)

        viewModel.getAllVidio().observe(this, Observer {
            rv_vidio.adapter?.let { adapter ->
                if (adapter is VidioAdapter) {
                    adapter.setVidio(it)
                }
            }
        })

        viewModel.getState().observer(this, Observer {
            handleUiState(it)
        })
    }

    private fun handleUiState(it: AppState) {
        when (it) {
            is AppState.IsLoding -> isLoding(it.state)
            is AppState.Error -> {
                getToals(it.err)
                isLoding(false)
            }
        }
    }

    private fun getToals(message: String?) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    private fun isLoding(state: Boolean) {
        if (state)loding.visibility = View.VISIBLE else loding.visibility = View.GONE
    }


    private fun setupRecler() {
        rv_vidio.apply {
            layoutManager = LinearLayoutManager(this@ListVidioActivity)
            adapter = VidioAdapter(mutableListOf(), this@ListVidioActivity)

        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getVidio(Constants.getToken(this))
    }

    private fun islogin(){
        if (Constants.getToken(this).equals("undefined")){
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }).also { finish() }
        }
    }
}