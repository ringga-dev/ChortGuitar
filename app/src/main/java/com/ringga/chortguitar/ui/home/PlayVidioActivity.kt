package com.ringga.chortguitar.ui.home

import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.ringga.chortguitar.R
import kotlinx.android.synthetic.main.activity_play_vidio.*

class PlayVidioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_vidio)
        val extras = intent.extras
//        val videoUrl = "http://192.168.43.8/sekolah-saya/public/assets/vidio/vidio.mp4"
        val videoUrl = "http://192.168.43.8/sekolah-saya/public/assets/vidio/${extras?.getString("URL")}"

        try {
            // Start the MediaController
            val mediacontroller = MediaController(this)
            mediacontroller.setAnchorView(video_view)
            // Get the URL from String videoUrl
            val video: Uri = Uri.parse(videoUrl)
            video_view.setMediaController(mediacontroller)
            video_view.setVideoURI(video)
        } catch (e: Exception) {
            Log.e("Error", e.message!!)
            e.printStackTrace()
        }

        video_view.setOnPreparedListener(OnPreparedListener { video_view.start() })
    }
}