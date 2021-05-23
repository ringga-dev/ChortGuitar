package com.ringga.chortguitar.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ringga.chortguitar.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        m_list_lagu.setOnClickListener {
            startActivity(Intent(this, ListLaguActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

        m_profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

        m_wallpaper.setOnClickListener {
            startActivity(Intent(this, WallpaperActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

        m_vidio.setOnClickListener {
            startActivity(Intent(this, ListVidioActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
    }
}