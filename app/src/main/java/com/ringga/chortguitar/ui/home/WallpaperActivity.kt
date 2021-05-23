package com.ringga.chortguitar.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.adapter.ChortAdapter
import com.ringga.chortguitar.data.adapter.WallpaperAdapter
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.ui.viewmodels.AppState
import com.ringga.chortguitar.ui.viewmodels.GuitarViewModel
import kotlinx.android.synthetic.main.activity_wallpaper.*

class WallpaperActivity : AppCompatActivity() {

    private lateinit var viewModel: GuitarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)
        setupRecler()
        viewModel = ViewModelProviders.of(this).get(GuitarViewModel::class.java)

        btn_camera.setOnClickListener(View.OnClickListener {
            val i = Intent(this, CameraActivity::class.java)
            val pathPic = "bg_item2"
            i.putExtra("PATH_PICTURE", pathPic)
            startActivity(i)
        })

        viewModel.getAllWallpaper().observe(this, Observer {
            rv_wallpaper.adapter?.let { adapter ->
                if (adapter is WallpaperAdapter) {
                    adapter.setWallpapers(it)
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
        rv_wallpaper.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = WallpaperAdapter(mutableListOf(), this@WallpaperActivity)

        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getWallpaper(Constants.getToken(this))
    }
}