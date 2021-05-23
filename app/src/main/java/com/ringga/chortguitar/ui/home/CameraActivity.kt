package com.ringga.chortguitar.ui.home

import android.Manifest
import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ringga.chortguitar.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.IOException

class CameraActivity : AppCompatActivity() {

    var camBitmap: Bitmap? = null
    private val PICK_IMAGE = 1
    private val CAPTURE_IMAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        setSupportActionBar(mtbCam)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val cam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cam, 0)

        save_btn.setOnClickListener(View.OnClickListener {
            val wlp = WallpaperManager.getInstance(applicationContext)
            Toast.makeText(applicationContext, "Success Set Wallpaper", Toast.LENGTH_SHORT).show()
            try {
                wlp.setBitmap(camBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode != Activity.RESULT_CANCELED) {
            camBitmap = data?.extras!!["data"] as Bitmap?
            place_pic?.setImageBitmap(camBitmap)

        } else {
            startActivity(Intent(this, WallpaperActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

//
//        if (data?.extras!!["data"]!= null) {
//            camBitmap = data.extras!!["data"] as Bitmap?
//            place_pic?.setImageBitmap(camBitmap)
//        }else{
//            startActivity(Intent(this, WallpaperActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            })
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}