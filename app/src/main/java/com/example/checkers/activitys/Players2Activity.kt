package com.example.checkers.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.checkers.R
import com.example.checkers.Translator
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_player1.*
import kotlinx.android.synthetic.main.activity_players2.*
import kotlinx.android.synthetic.main.board.view.*
import kotlin.random.Random

class Players2Activity : AppCompatActivity() {

    var white = 0
    var black = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players2)

        inits()
    }

    private fun translate() {
        val preferences = getSharedPreferences("Settings", 0)
        val language = preferences.getString("language", "")!!

        val translator = Translator(language)

        name1Player2Players.text = translator.name1Player2Players
        name2Player2Players.text = translator.name2Player2Players

        score1Player2Players.text = translator.score
        score2Player2Players.text = translator.score
        offerDraw2Players.text = translator.offerDraw
    }

    private fun inits() {
        initVariants()
        initColor()
        initButtons()

        translate()
    }

    private fun initColor() {
        val color = intent.getStringExtra("color")

        if (color == "random") {
            val random = List(1) { Random.nextInt(0, 2)}

            if (random.first() == 1) {
                val tmp = white
                white = black
                black = tmp
            }
        } else if (color == "black") {
            val tmp = white
            white = black
            black = tmp
        }
    }

    private fun initVariants() {
        val preferences = getSharedPreferences("Settings", 0)

        val board = preferences.getString("board", "")!!
        val whiteStr = preferences.getString("whiteVariant", "")!!
        val blackStr = preferences.getString("blackVariant", "")!!

        when (board) {
            "classicBoard" -> boardImageView2Players.setImageResource(R.drawable.classic_board)
            "woodBoard" -> boardImageView2Players.setImageResource(R.drawable.wood_board)
            "neonBoard" -> boardImageView2Players.setImageResource(R.drawable.neon_board)
        }

        when (whiteStr) {
            "whiteVariant1" -> white = R.drawable.variant1_white
            "whiteVariant2" -> white = R.drawable.variant2_white
        }

        when (blackStr) {
            "blackVariant1" -> black = R.drawable.variant1_black
            "blackVariant2" -> black = R.drawable.variant2_black
        }
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

    private fun initButtons() {
        boardInclude2Players.imageButton01.setImageResource(black)
        boardInclude2Players.imageButton03.setImageResource(black)
        boardInclude2Players.imageButton05.setImageResource(black)
        boardInclude2Players.imageButton07.setImageResource(black)
        boardInclude2Players.imageButton10.setImageResource(black)
        boardInclude2Players.imageButton12.setImageResource(black)
        boardInclude2Players.imageButton14.setImageResource(black)
        boardInclude2Players.imageButton16.setImageResource(black)
        boardInclude2Players.imageButton21.setImageResource(black)
        boardInclude2Players.imageButton23.setImageResource(black)
        boardInclude2Players.imageButton25.setImageResource(black)
        boardInclude2Players.imageButton27.setImageResource(black)

        boardInclude2Players.imageButton50.setImageResource(white)
        boardInclude2Players.imageButton52.setImageResource(white)
        boardInclude2Players.imageButton54.setImageResource(white)
        boardInclude2Players.imageButton56.setImageResource(white)
        boardInclude2Players.imageButton61.setImageResource(white)
        boardInclude2Players.imageButton63.setImageResource(white)
        boardInclude2Players.imageButton65.setImageResource(white)
        boardInclude2Players.imageButton67.setImageResource(white)
        boardInclude2Players.imageButton70.setImageResource(white)
        boardInclude2Players.imageButton72.setImageResource(white)
        boardInclude2Players.imageButton74.setImageResource(white)
        boardInclude2Players.imageButton76.setImageResource(white)
    }
}
