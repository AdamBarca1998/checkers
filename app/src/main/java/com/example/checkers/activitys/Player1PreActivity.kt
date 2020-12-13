package com.example.checkers.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.checkers.R
import kotlinx.android.synthetic.main.activity_player1_pre.*

class Player1PreActivity : AppCompatActivity(), View.OnClickListener {

    var color = "random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player1_pre)

        inits()
    }

    private fun inits() {
        setListeners()
    }

    private fun setListeners() {
        white1Player.setOnClickListener(this)
        random1Player.setOnClickListener(this)
        black1Player.setOnClickListener(this)
        start1Player.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v) {
                white1Player -> changeColor("white")
                random1Player -> changeColor("random")
                black1Player -> changeColor("black")
                start1Player -> startActivity()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeColor(colorTmp: String) {
        color = colorTmp

        white1Player.setBackgroundColor(Color.parseColor("#00000000"))
        random1Player.setBackgroundColor(Color.parseColor("#00000000"))
        black1Player.setBackgroundColor(Color.parseColor("#00000000"))

        when (colorTmp) {
            "white" -> {
                white1Player.setBackgroundColor(Color.parseColor("#80000000"))
            }
            "random" -> {
                random1Player.setBackgroundColor(Color.parseColor("#80000000"))
            }
            else -> {
                black1Player.setBackgroundColor(Color.parseColor("#80000000"))
            }
        }
    }

    private fun startActivity() {
        val intent = Intent(this, Player1Activity::class.java)
        intent.putExtra("color", color)
        startActivity(intent)
    }

    //Full screen functions
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}
