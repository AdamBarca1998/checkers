package com.example.checkers.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.checkers.R
import com.example.checkers.Translator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var playerName = String()

    var language = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inits()

        setListeners()
    }

    private fun translate() {
        val translator = Translator(language)

        player1Button.text = translator.player1Button
        player2Button.text = translator.player2Button
        onlineButton.text = translator.onlineButton
        settingsButton.text = translator.settingsButton
    }

    private fun inits() {
        initLanguage()

        initName()

        initVariants()

        translate()
    }

    private fun initVariants() {
        val preferences = getSharedPreferences("Settings", 0)

        val board = preferences.getString("board", "")!!

        if (board == "") {
            val editor = preferences.edit()

            editor.putString("board", "classicBoard")
            editor.putString("whiteVariant", "whiteVariant1")
            editor.putString("blackVariant", "blackVariant1")

            editor.apply()
        }
    }

    private fun initLanguage() {
        val preferences = getSharedPreferences("Settings", 0)
        language = preferences.getString("language", "")!!

        if (language == "") {
            language = "English"

            val editor = preferences.edit()
            editor.putString("language", language)
            editor.apply()
        }
    }

    private fun initName() {
        val preferences = getSharedPreferences("Settings", 0)
        playerName = preferences.getString("playerName", "")!!

        if (playerName == "") {
            playerName = "Player1"

            val editor = preferences.edit()
            editor.putString("playerName", playerName)
            editor.apply()
        }
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v) {
                player1Button -> player1Activity()
                player2Button -> players2Activity()
                onlineButton -> roomsActivity()
                settingsButton -> settingsActivity()
            }
        }
    }

    private fun setListeners() {
        player1Button.setOnClickListener(this)
        player2Button.setOnClickListener(this)
        onlineButton.setOnClickListener(this)
        settingsButton.setOnClickListener(this)
    }

    private fun player1Activity() {
        val intent = Intent(this, Player1PreActivity::class.java)
        startActivity(intent)
    }

    private fun players2Activity() {
        val intent = Intent(this, Player2PreActivity::class.java)
        startActivity(intent)
    }

    private fun roomsActivity() {
        val intent = Intent(this, RoomsActivity::class.java)
        startActivity(intent)
    }

    private fun settingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
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
