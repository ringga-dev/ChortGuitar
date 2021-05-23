package com.ringga.chortguitar.ui.home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.github.akshay_naik.texthighlighterapi.TextHighlighter
import com.ringga.chortguitar.R
import kotlinx.android.synthetic.main.activity_lagu.*
import kotlin.math.log

class LaguActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lagu)
        val highlighter = TextHighlighter()
        val extras = intent.extras
        val lagu =  extras?.getString("kunci")
        val artis=  extras?.get("artis").toString()
        val judul=  extras?.get("judul").toString()

        //set color kunci gitar
        highlighter.setColorForTheToken("C", "#00c8ff")
        highlighter.setColorForTheToken("C#m", "#00c8ff")
        highlighter.setColorForTheToken("D", "#00c8ff")
        highlighter.setColorForTheToken("Dm", "#00c8ff")
        highlighter.setColorForTheToken("D#", "#00c8ff")
        highlighter.setColorForTheToken("D#m", "#00c8ff")
        highlighter.setColorForTheToken("E", "#00c8ff")
        highlighter.setColorForTheToken("Em", "#00c8ff")
        highlighter.setColorForTheToken("F", "#00c8ff")
        highlighter.setColorForTheToken("Fm", "#00c8ff")
        highlighter.setColorForTheToken("F#", "#00c8ff")
        highlighter.setColorForTheToken("F#m", "#00c8ff")
        highlighter.setColorForTheToken("G", "#00c8ff")
        highlighter.setColorForTheToken("Gm", "#00c8ff")
        highlighter.setColorForTheToken("G#", "#00c8ff")
        highlighter.setColorForTheToken("G#m", "#00c8ff")
        highlighter.setColorForTheToken("A", "#00c8ff")
        highlighter.setColorForTheToken("Am", "#00c8ff")
        highlighter.setColorForTheToken("A#", "#00c8ff")
        highlighter.setColorForTheToken("A#m", "#00c8ff")
        highlighter.setColorForTheToken("B", "#00c8ff")
        highlighter.setColorForTheToken("Bm", "#00c8ff")

        val format  = highlighter.getHighlightedText(lagu?.replace("\r\n", " <br> "))
        val tampil = highlighter.getHighlightedText(format)
        Log.i("TAG", format)
//        tv_kunci.text= tampil

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           tv_kunci.text =
                Html.fromHtml(format, Html.FROM_HTML_MODE_COMPACT);
        } else {
            tv_kunci.text =
                Html.fromHtml(format);
        }

    }
}