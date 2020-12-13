package com.example.checkers.activitys

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.checkers.R
import com.example.checkers.Translator
import kotlinx.android.synthetic.main.activity_player1.*
import kotlinx.android.synthetic.main.board.view.*
import org.json.JSONArray
import org.json.JSONException
import kotlin.random.Random


class Player1Activity : AppCompatActivity() {

    var black = 0
    var white = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player1)

        inits()

        offerDraw1Player.setOnClickListener {
        }
    }

    private fun inits() {
        initName()

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
            "classicBoard" -> boardImageView1Player.setImageResource(R.drawable.classic_board)
            "woodBoard" -> boardImageView1Player.setImageResource(R.drawable.wood_board)
            "neonBoard" -> boardImageView1Player.setImageResource(R.drawable.neon_board)
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

    private fun translate() {
        val preferences = getSharedPreferences("Settings", 0)
        val language = preferences.getString("language", "")!!

        val translator = Translator(language)

        myScore1Player.text = translator.score
        AIScore1Player.text = translator.score
        offerDraw1Player.text = translator.offerDraw
    }

    private fun initName() {
        val preferences = getSharedPreferences("Settings", 0)
        val playerName = preferences.getString("playerName", "")!!

        myName1Player.text = playerName
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
        boardInclude1Player.imageButton01.setImageResource(black)
        boardInclude1Player.imageButton03.setImageResource(black)
        boardInclude1Player.imageButton05.setImageResource(black)
        boardInclude1Player.imageButton07.setImageResource(black)
        boardInclude1Player.imageButton10.setImageResource(black)
        boardInclude1Player.imageButton12.setImageResource(black)
        boardInclude1Player.imageButton14.setImageResource(black)
        boardInclude1Player.imageButton16.setImageResource(black)
        boardInclude1Player.imageButton21.setImageResource(black)
        boardInclude1Player.imageButton23.setImageResource(black)
        boardInclude1Player.imageButton25.setImageResource(black)
        boardInclude1Player.imageButton27.setImageResource(black)

        boardInclude1Player.imageButton50.setImageResource(white)
        boardInclude1Player.imageButton52.setImageResource(white)
        boardInclude1Player.imageButton54.setImageResource(white)
        boardInclude1Player.imageButton56.setImageResource(white)
        boardInclude1Player.imageButton61.setImageResource(white)
        boardInclude1Player.imageButton63.setImageResource(white)
        boardInclude1Player.imageButton65.setImageResource(white)
        boardInclude1Player.imageButton67.setImageResource(white)
        boardInclude1Player.imageButton70.setImageResource(white)
        boardInclude1Player.imageButton72.setImageResource(white)
        boardInclude1Player.imageButton74.setImageResource(white)
        boardInclude1Player.imageButton76.setImageResource(white)
    }
}
