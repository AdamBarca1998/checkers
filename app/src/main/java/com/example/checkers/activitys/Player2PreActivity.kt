package com.example.checkers.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.checkers.R
import com.example.checkers.Translator
import kotlinx.android.synthetic.main.activity_player1_pre.*
import kotlinx.android.synthetic.main.activity_player2_pre.*

class Player2PreActivity : AppCompatActivity(), View.OnClickListener {

    var color = "random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player2_pre)

        inits()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v) {
                whitePlayer1PrePlayers2 -> changeColor("white")
                blackPlayer2PrePlayers2 -> changeColor("white")
                randomPlayer1PrePlayers1 -> changeColor("random")
                randomPlayer2PrePlayers2 -> changeColor("random")
                blackPlayer1PrePlayers2 -> changeColor("black")
                whitePlayer2PrePlayers2 -> changeColor("black")
                startPrePlayers2 -> startActivity()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeColor(colorTmp: String) {
        color = colorTmp

        whitePlayer2PrePlayers2.setBackgroundColor(Color.parseColor("#00000000"))
        randomPlayer2PrePlayers2.setBackgroundColor(Color.parseColor("#00000000"))
        blackPlayer2PrePlayers2.setBackgroundColor(Color.parseColor("#00000000"))

        whitePlayer1PrePlayers2.setBackgroundColor(Color.parseColor("#00000000"))
        randomPlayer1PrePlayers1.setBackgroundColor(Color.parseColor("#00000000"))
        blackPlayer1PrePlayers2.setBackgroundColor(Color.parseColor("#00000000"))

        when (colorTmp) {
            "white" -> {
                whitePlayer1PrePlayers2.setBackgroundColor(Color.parseColor("#80000000"))
                blackPlayer2PrePlayers2.setBackgroundColor(Color.parseColor("#80000000"))
            }
            "random" -> {
                randomPlayer1PrePlayers1.setBackgroundColor(Color.parseColor("#80000000"))
                randomPlayer2PrePlayers2.setBackgroundColor(Color.parseColor("#80000000"))
            }
            else -> {
                blackPlayer1PrePlayers2.setBackgroundColor(Color.parseColor("#80000000"))
                whitePlayer2PrePlayers2.setBackgroundColor(Color.parseColor("#80000000"))
            }
        }
    }

    private fun inits() {
        setListeners()

        translate()
    }

    private fun setListeners() {
        whitePlayer2PrePlayers2.setOnClickListener(this)
        randomPlayer2PrePlayers2.setOnClickListener(this)
        blackPlayer2PrePlayers2.setOnClickListener(this)
        whitePlayer1PrePlayers2.setOnClickListener(this)
        randomPlayer1PrePlayers1.setOnClickListener(this)
        blackPlayer1PrePlayers2.setOnClickListener(this)
        startPrePlayers2.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun translate() {
        val preferences = getSharedPreferences("Settings", 0)
        val language = preferences.getString("language", "")!!

        val translator = Translator(language)

        player1PrePlayers2.text = "${translator.name1Player2Players}:"
        player2PrePlayers2.text = "${translator.name2Player2Players}:"
    }

    private fun startActivity() {
        val intent = Intent(this, Players2Activity::class.java)
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
