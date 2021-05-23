package com.ringga.chortguitar.ui.home

import android.Manifest
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_theme.*
import java.io.IOException


class ThemeActivity : AppCompatActivity() {
    var newString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        ActivityCompat.requestPermissions(
            this@ThemeActivity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        setSupportActionBar(mtbSet)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                newString = null
            } else {

                newString =
                    "${Constants.URL_ENDPOINT}assets/wallpaper/${extras.getString("PATH_PICTURE")}"
                Picasso.get().load(newString).into(theme_preview)

                save_btn.setOnClickListener(View.OnClickListener {
                    val wlp = WallpaperManager.getInstance(this)
                    Toast.makeText(
                        applicationContext,
                        "Success Set Wallpaper",
                        Toast.LENGTH_SHORT
                    ).show()
                    try {
                        Picasso.get()
                            .load(newString)
                            .into(object : com.squareup.picasso.Target {
                                override fun onBitmapFailed(
                                    e: Exception?,
                                    errorDrawable: Drawable?
                                ) {
                                    TODO("not implemented")
                                }

                                override fun onBitmapLoaded(
                                    bitmap: Bitmap?,
                                    from: Picasso.LoadedFrom?
                                ) {
                                    wlp.setBitmap(bitmap)
                                }

                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

                            })
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                })

            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}