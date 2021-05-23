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
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.ui.auth.LoginActivity
import com.ringga.chortguitar.ui.viewmodels.AppState
import com.ringga.chortguitar.ui.viewmodels.GuitarViewModel
import kotlinx.android.synthetic.main.activity_list_lagu.*

class ListLaguActivity : AppCompatActivity() {

    private lateinit var viewModel: GuitarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lagu)
        islogin()
        setupRecler()
        viewModel = ViewModelProviders.of(this).get(GuitarViewModel::class.java)



        viewModel.getAllLagu().observe(this, Observer {
            rv_lagu.adapter?.let { adapter ->
                if (adapter is ChortAdapter) {
                    adapter.setChords(it)
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
        if (state) pb_loding.visibility = View.VISIBLE else pb_loding.visibility = View.GONE
    }


    private fun setupRecler() {
        rv_lagu.apply {
            layoutManager = LinearLayoutManager(this@ListLaguActivity)
            adapter = ChortAdapter(mutableListOf(), this@ListLaguActivity)

        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchAllPost(Constants.getToken(this))
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