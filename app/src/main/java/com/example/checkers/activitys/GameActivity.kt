package com.example.checkers.activitys

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.checkers.Game
import com.example.checkers.R
import com.example.checkers.Translator
import com.example.checkers.adapters.TahyAdapter
import com.example.checkers.datas.TahyData
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.board.view.*
import org.json.JSONException
import org.json.JSONObject


class GameActivity : AppCompatActivity(), View.OnClickListener {
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 500

    private var sachovnica = ArrayList<ImageButton>()

    private val tahyData = ArrayList<TahyData>()
    private var indexTahu = 1
    private val cislaTahu = ArrayList<String>()

    private var whiteVariant = 0
    private var blackVariant = 0

    private var white = 0
    private var black = 0

    private var myColor = ""
    private var id = ""

    private var game = Game()

    private var oldState = ""
    private var finishFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        inits()
    }

    private fun nextActivity() {
        val intent = Intent(this, RoomsActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.surrenderButton -> {
                    postSurr()
                }
                R.id.closeButton -> {
                    nextActivity()
                }
            }

            for ((index, i) in sachovnica.withIndex()) {
                if (v.id == i.id) {
                    val answer = game.clickButton(index)

                    click("$myColor$answer")
                }
            }
        }
    }

    private fun postSurr() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://adambarca123.pythonanywhere.com/game/$id/finish/"

        var who = 0

        who = if (myColor == "b") {
            1
        } else {
            2
        }

        val jsonObject = JSONObject()
        jsonObject.put("state", who)

        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject, Response.Listener {
                response ->try {
            showLoserDialog()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun click(tah: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://adambarca123.pythonanywhere.com/game/$id/click/"

        val jsonObject = JSONObject()
        jsonObject.put("tah", tah)

        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject, Response.Listener {
                response ->try {
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun translate() {
        val preferences = getSharedPreferences("Settings", 0)
        val language = preferences.getString("language", "")!!

        val translator = Translator(language)

        myScoreOnline.text = translator.score
        enemyScoreOnline.text = translator.score
        offerDrawOnline.text = translator.offerDraw
    }

    private fun inits() {
        initVariants()

        initColor()
        initNames()

        initSachovnica()

        setOnListeners()

        initButtons()

        translate()
    }

    private fun initColor() {
        myColor = intent.getStringExtra("color")

        if (myColor == "b") {
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
            "classicBoard" -> boardImageViewOnline.setImageResource(R.drawable.classic_board)
            "woodBoard" -> boardImageViewOnline.setImageResource(R.drawable.wood_board)
            "neonBoard" -> boardImageViewOnline.setImageResource(R.drawable.neon_board)
        }

        when (whiteStr) {
            "whiteVariant1" -> {
                white = R.drawable.variant1_white
                whiteVariant = R.drawable.variant1_white
            }
            "whiteVariant2" -> {
                white = R.drawable.variant2_white
                whiteVariant = R.drawable.variant2_white
            }
        }

        when (blackStr) {
            "blackVariant1" -> {
                black = R.drawable.variant1_black
                blackVariant = R.drawable.variant1_black
            }
            "blackVariant2" -> {
                black = R.drawable.variant2_black
                blackVariant = R.drawable.variant2_black
            }
        }
    }

    private fun whoWin(winner: String) {
        onPause()

        if ( (myColor == "b" && winner == "vyhral 2") || (myColor == "w" && winner == "vyhral 1") ) {
            showWinnerDialog()
        } else {
            showLoserDialog()
        }
    }

    private fun showLoserDialog() {
        if (!finishFlag) {
            val alertDialog = Dialog(this)
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            alertDialog.setContentView(R.layout.loser_dialog)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            //val imageView = alertDialog.findViewById<ImageView>(R.id.smile)
            //imageView.setImageResource(R.drawable.ic_unlock)

            val button = alertDialog.findViewById<Button>(R.id.closeButton)
            button.setOnClickListener(this)
            //alertDialog.setCancelable(false)
            alertDialog.show()
        }

        finishFlag = true
    }

    private fun showWinnerDialog() {
        if (!finishFlag) {
            val alertDialog = Dialog(this)
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            alertDialog.setContentView(R.layout.winner_dialog)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val button = alertDialog.findViewById<Button>(R.id.closeButton)
            button.setOnClickListener(this)

            //alertDialog.setCancelable(false)
            alertDialog.show()
        }

        finishFlag = true
    }

    private fun initName(player1: String, player2: String) {
        val preferences = getSharedPreferences("Settings", 0)
        val myName = preferences.getString("playerName", "")!!

        meNameOnline.text = myName

        if (myName == player1) {
            name2PlayerOnline.text = player2
        } else {
            name2PlayerOnline.text = player1
        }
    }

    private fun initNames() {
        id = intent.getStringExtra("id")

        val queue = Volley.newRequestQueue(this)

        val url = "https://adambarca123.pythonanywhere.com/game/$id/"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            initName(response.getString("player1"), response.getString("player2"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
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
        boardIncludeOnline.imageButton01.setImageResource(black)
        boardIncludeOnline.imageButton03.setImageResource(black)
        boardIncludeOnline.imageButton05.setImageResource(black)
        boardIncludeOnline.imageButton07.setImageResource(black)
        boardIncludeOnline.imageButton10.setImageResource(black)
        boardIncludeOnline.imageButton12.setImageResource(black)
        boardIncludeOnline.imageButton14.setImageResource(black)
        boardIncludeOnline.imageButton16.setImageResource(black)
        boardIncludeOnline.imageButton21.setImageResource(black)
        boardIncludeOnline.imageButton23.setImageResource(black)
        boardIncludeOnline.imageButton25.setImageResource(black)
        boardIncludeOnline.imageButton27.setImageResource(black)

        boardIncludeOnline.imageButton50.setImageResource(white)
        boardIncludeOnline.imageButton52.setImageResource(white)
        boardIncludeOnline.imageButton54.setImageResource(white)
        boardIncludeOnline.imageButton56.setImageResource(white)
        boardIncludeOnline.imageButton61.setImageResource(white)
        boardIncludeOnline.imageButton63.setImageResource(white)
        boardIncludeOnline.imageButton65.setImageResource(white)
        boardIncludeOnline.imageButton67.setImageResource(white)
        boardIncludeOnline.imageButton70.setImageResource(white)
        boardIncludeOnline.imageButton72.setImageResource(white)
        boardIncludeOnline.imageButton74.setImageResource(white)
        boardIncludeOnline.imageButton76.setImageResource(white)
    }

    private fun initSachovnica() {
        if (myColor == "w") {
            sachovnica.add(boardIncludeOnline.imageButton70)
            sachovnica.add(boardIncludeOnline.imageButton71)
            sachovnica.add(boardIncludeOnline.imageButton72)
            sachovnica.add(boardIncludeOnline.imageButton73)
            sachovnica.add(boardIncludeOnline.imageButton74)
            sachovnica.add(boardIncludeOnline.imageButton75)
            sachovnica.add(boardIncludeOnline.imageButton76)
            sachovnica.add(boardIncludeOnline.imageButton77)

            sachovnica.add(boardIncludeOnline.imageButton60)
            sachovnica.add(boardIncludeOnline.imageButton61)
            sachovnica.add(boardIncludeOnline.imageButton62)
            sachovnica.add(boardIncludeOnline.imageButton63)
            sachovnica.add(boardIncludeOnline.imageButton64)
            sachovnica.add(boardIncludeOnline.imageButton65)
            sachovnica.add(boardIncludeOnline.imageButton66)
            sachovnica.add(boardIncludeOnline.imageButton67)

            sachovnica.add(boardIncludeOnline.imageButton50)
            sachovnica.add(boardIncludeOnline.imageButton51)
            sachovnica.add(boardIncludeOnline.imageButton52)
            sachovnica.add(boardIncludeOnline.imageButton53)
            sachovnica.add(boardIncludeOnline.imageButton54)
            sachovnica.add(boardIncludeOnline.imageButton55)
            sachovnica.add(boardIncludeOnline.imageButton56)
            sachovnica.add(boardIncludeOnline.imageButton57)

            sachovnica.add(boardIncludeOnline.imageButton40)
            sachovnica.add(boardIncludeOnline.imageButton41)
            sachovnica.add(boardIncludeOnline.imageButton42)
            sachovnica.add(boardIncludeOnline.imageButton43)
            sachovnica.add(boardIncludeOnline.imageButton44)
            sachovnica.add(boardIncludeOnline.imageButton45)
            sachovnica.add(boardIncludeOnline.imageButton46)
            sachovnica.add(boardIncludeOnline.imageButton47)

            sachovnica.add(boardIncludeOnline.imageButton30)
            sachovnica.add(boardIncludeOnline.imageButton31)
            sachovnica.add(boardIncludeOnline.imageButton32)
            sachovnica.add(boardIncludeOnline.imageButton33)
            sachovnica.add(boardIncludeOnline.imageButton34)
            sachovnica.add(boardIncludeOnline.imageButton35)
            sachovnica.add(boardIncludeOnline.imageButton36)
            sachovnica.add(boardIncludeOnline.imageButton37)

            sachovnica.add(boardIncludeOnline.imageButton20)
            sachovnica.add(boardIncludeOnline.imageButton21)
            sachovnica.add(boardIncludeOnline.imageButton22)
            sachovnica.add(boardIncludeOnline.imageButton23)
            sachovnica.add(boardIncludeOnline.imageButton24)
            sachovnica.add(boardIncludeOnline.imageButton25)
            sachovnica.add(boardIncludeOnline.imageButton26)
            sachovnica.add(boardIncludeOnline.imageButton27)

            sachovnica.add(boardIncludeOnline.imageButton10)
            sachovnica.add(boardIncludeOnline.imageButton11)
            sachovnica.add(boardIncludeOnline.imageButton12)
            sachovnica.add(boardIncludeOnline.imageButton13)
            sachovnica.add(boardIncludeOnline.imageButton14)
            sachovnica.add(boardIncludeOnline.imageButton15)
            sachovnica.add(boardIncludeOnline.imageButton16)
            sachovnica.add(boardIncludeOnline.imageButton17)

            sachovnica.add(boardIncludeOnline.imageButton00)
            sachovnica.add(boardIncludeOnline.imageButton01)
            sachovnica.add(boardIncludeOnline.imageButton02)
            sachovnica.add(boardIncludeOnline.imageButton03)
            sachovnica.add(boardIncludeOnline.imageButton04)
            sachovnica.add(boardIncludeOnline.imageButton05)
            sachovnica.add(boardIncludeOnline.imageButton06)
            sachovnica.add(boardIncludeOnline.imageButton07)
        } else {
            sachovnica.add(boardIncludeOnline.imageButton07)
            sachovnica.add(boardIncludeOnline.imageButton06)
            sachovnica.add(boardIncludeOnline.imageButton05)
            sachovnica.add(boardIncludeOnline.imageButton04)
            sachovnica.add(boardIncludeOnline.imageButton03)
            sachovnica.add(boardIncludeOnline.imageButton02)
            sachovnica.add(boardIncludeOnline.imageButton01)
            sachovnica.add(boardIncludeOnline.imageButton00)

            sachovnica.add(boardIncludeOnline.imageButton17)
            sachovnica.add(boardIncludeOnline.imageButton16)
            sachovnica.add(boardIncludeOnline.imageButton15)
            sachovnica.add(boardIncludeOnline.imageButton14)
            sachovnica.add(boardIncludeOnline.imageButton13)
            sachovnica.add(boardIncludeOnline.imageButton12)
            sachovnica.add(boardIncludeOnline.imageButton11)
            sachovnica.add(boardIncludeOnline.imageButton10)

            sachovnica.add(boardIncludeOnline.imageButton27)
            sachovnica.add(boardIncludeOnline.imageButton26)
            sachovnica.add(boardIncludeOnline.imageButton25)
            sachovnica.add(boardIncludeOnline.imageButton24)
            sachovnica.add(boardIncludeOnline.imageButton23)
            sachovnica.add(boardIncludeOnline.imageButton22)
            sachovnica.add(boardIncludeOnline.imageButton21)
            sachovnica.add(boardIncludeOnline.imageButton20)

            sachovnica.add(boardIncludeOnline.imageButton37)
            sachovnica.add(boardIncludeOnline.imageButton36)
            sachovnica.add(boardIncludeOnline.imageButton35)
            sachovnica.add(boardIncludeOnline.imageButton34)
            sachovnica.add(boardIncludeOnline.imageButton33)
            sachovnica.add(boardIncludeOnline.imageButton32)
            sachovnica.add(boardIncludeOnline.imageButton31)
            sachovnica.add(boardIncludeOnline.imageButton30)

            sachovnica.add(boardIncludeOnline.imageButton47)
            sachovnica.add(boardIncludeOnline.imageButton46)
            sachovnica.add(boardIncludeOnline.imageButton45)
            sachovnica.add(boardIncludeOnline.imageButton44)
            sachovnica.add(boardIncludeOnline.imageButton43)
            sachovnica.add(boardIncludeOnline.imageButton42)
            sachovnica.add(boardIncludeOnline.imageButton41)
            sachovnica.add(boardIncludeOnline.imageButton40)

            sachovnica.add(boardIncludeOnline.imageButton57)
            sachovnica.add(boardIncludeOnline.imageButton56)
            sachovnica.add(boardIncludeOnline.imageButton55)
            sachovnica.add(boardIncludeOnline.imageButton54)
            sachovnica.add(boardIncludeOnline.imageButton53)
            sachovnica.add(boardIncludeOnline.imageButton52)
            sachovnica.add(boardIncludeOnline.imageButton51)
            sachovnica.add(boardIncludeOnline.imageButton50)

            sachovnica.add(boardIncludeOnline.imageButton67)
            sachovnica.add(boardIncludeOnline.imageButton66)
            sachovnica.add(boardIncludeOnline.imageButton65)
            sachovnica.add(boardIncludeOnline.imageButton64)
            sachovnica.add(boardIncludeOnline.imageButton63)
            sachovnica.add(boardIncludeOnline.imageButton62)
            sachovnica.add(boardIncludeOnline.imageButton61)
            sachovnica.add(boardIncludeOnline.imageButton60)

            sachovnica.add(boardIncludeOnline.imageButton77)
            sachovnica.add(boardIncludeOnline.imageButton76)
            sachovnica.add(boardIncludeOnline.imageButton75)
            sachovnica.add(boardIncludeOnline.imageButton74)
            sachovnica.add(boardIncludeOnline.imageButton73)
            sachovnica.add(boardIncludeOnline.imageButton72)
            sachovnica.add(boardIncludeOnline.imageButton71)
            sachovnica.add(boardIncludeOnline.imageButton70)
        }
    }

    private fun setOnListeners() {
        surrenderButton.setOnClickListener(this)

        boardIncludeOnline.imageButton00.setOnClickListener(this)
        boardIncludeOnline.imageButton01.setOnClickListener(this)
        boardIncludeOnline.imageButton02.setOnClickListener(this)
        boardIncludeOnline.imageButton03.setOnClickListener(this)
        boardIncludeOnline.imageButton04.setOnClickListener(this)
        boardIncludeOnline.imageButton05.setOnClickListener(this)
        boardIncludeOnline.imageButton06.setOnClickListener(this)
        boardIncludeOnline.imageButton07.setOnClickListener(this)

        boardIncludeOnline.imageButton10.setOnClickListener(this)
        boardIncludeOnline.imageButton11.setOnClickListener(this)
        boardIncludeOnline.imageButton12.setOnClickListener(this)
        boardIncludeOnline.imageButton13.setOnClickListener(this)
        boardIncludeOnline.imageButton14.setOnClickListener(this)
        boardIncludeOnline.imageButton15.setOnClickListener(this)
        boardIncludeOnline.imageButton16.setOnClickListener(this)
        boardIncludeOnline.imageButton17.setOnClickListener(this)

        boardIncludeOnline.imageButton20.setOnClickListener(this)
        boardIncludeOnline.imageButton21.setOnClickListener(this)
        boardIncludeOnline.imageButton22.setOnClickListener(this)
        boardIncludeOnline.imageButton23.setOnClickListener(this)
        boardIncludeOnline.imageButton24.setOnClickListener(this)
        boardIncludeOnline.imageButton25.setOnClickListener(this)
        boardIncludeOnline.imageButton26.setOnClickListener(this)
        boardIncludeOnline.imageButton27.setOnClickListener(this)

        boardIncludeOnline.imageButton30.setOnClickListener(this)
        boardIncludeOnline.imageButton31.setOnClickListener(this)
        boardIncludeOnline.imageButton32.setOnClickListener(this)
        boardIncludeOnline.imageButton33.setOnClickListener(this)
        boardIncludeOnline.imageButton34.setOnClickListener(this)
        boardIncludeOnline.imageButton35.setOnClickListener(this)
        boardIncludeOnline.imageButton36.setOnClickListener(this)
        boardIncludeOnline.imageButton37.setOnClickListener(this)

        boardIncludeOnline.imageButton40.setOnClickListener(this)
        boardIncludeOnline.imageButton41.setOnClickListener(this)
        boardIncludeOnline.imageButton42.setOnClickListener(this)
        boardIncludeOnline.imageButton43.setOnClickListener(this)
        boardIncludeOnline.imageButton44.setOnClickListener(this)
        boardIncludeOnline.imageButton45.setOnClickListener(this)
        boardIncludeOnline.imageButton46.setOnClickListener(this)
        boardIncludeOnline.imageButton47.setOnClickListener(this)

        boardIncludeOnline.imageButton50.setOnClickListener(this)
        boardIncludeOnline.imageButton51.setOnClickListener(this)
        boardIncludeOnline.imageButton52.setOnClickListener(this)
        boardIncludeOnline.imageButton53.setOnClickListener(this)
        boardIncludeOnline.imageButton54.setOnClickListener(this)
        boardIncludeOnline.imageButton55.setOnClickListener(this)
        boardIncludeOnline.imageButton56.setOnClickListener(this)
        boardIncludeOnline.imageButton57.setOnClickListener(this)

        boardIncludeOnline.imageButton60.setOnClickListener(this)
        boardIncludeOnline.imageButton61.setOnClickListener(this)
        boardIncludeOnline.imageButton62.setOnClickListener(this)
        boardIncludeOnline.imageButton63.setOnClickListener(this)
        boardIncludeOnline.imageButton64.setOnClickListener(this)
        boardIncludeOnline.imageButton65.setOnClickListener(this)
        boardIncludeOnline.imageButton66.setOnClickListener(this)
        boardIncludeOnline.imageButton67.setOnClickListener(this)

        boardIncludeOnline.imageButton70.setOnClickListener(this)
        boardIncludeOnline.imageButton71.setOnClickListener(this)
        boardIncludeOnline.imageButton72.setOnClickListener(this)
        boardIncludeOnline.imageButton73.setOnClickListener(this)
        boardIncludeOnline.imageButton74.setOnClickListener(this)
        boardIncludeOnline.imageButton75.setOnClickListener(this)
        boardIncludeOnline.imageButton76.setOnClickListener(this)
        boardIncludeOnline.imageButton77.setOnClickListener(this)
    }

    private fun showState() {
        val queue = Volley.newRequestQueue(this)

        val url = "https://adambarca123.pythonanywhere.com/game/$id/"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            if (response.getString("state") == "start") {
                setNewState(response.getString("chessboard"), response.getBoolean("onTurn"))
            } else {
                whoWin(response.getString("state"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun setSteps(state: String) {
        val game = Game()

        val tah = TahyData()

        var color = 0
        var old = ""
        var new = ""

        if (oldState.isNotEmpty()) {
            if (state != oldState) {
                for (i in 0 until (state.length / 2) - 1) {
                    if (state[i * 2 + 1] != oldState[i * 2 + 1]) {
                        if (state[i * 2 + 1] == '3') {
                            if (state[i * 2] == '0') {
                                color = whiteVariant
                            } else if (state[i * 2] == '1') {
                                color = blackVariant
                            }

                            new = game.clickButton(i)
                        }

                        if (state[i * 2 + 1] == '4') {
                            old = game.clickButton(i)
                        }
                    }
                }

                if (new == "") {
                    new = "d4"
                    color = blackVariant
                }

                tah.cisloTahu = "$indexTahu:"
                cislaTahu.add("$indexTahu:")
                indexTahu += 1

                tah.color = color
                tah.tah = "$old->$new"

                tahyData.add(tah)

                val myListAdapter = TahyAdapter(this, tahyData, cislaTahu)
                writeList.adapter = myListAdapter
            }
        }

        oldState = state.substring(0)
    }

    private fun setNewState(state: String, turn: Boolean) {
        var index = 0

        val stateTmp = cleanGreen(state, turn)

        setSteps(clearGreenRed(state))

        if (stateTmp.isNotEmpty()) {
            for (button in sachovnica) {
                when (stateTmp[index]) {
                    '0' -> button.setImageResource(whiteVariant)
                    '1' -> button.setImageResource(blackVariant)
                    '2' -> button.setImageResource(R.drawable.green)
                    '3' -> button.setImageResource(R.drawable.orange)
                    '4' -> button.setImageResource(android.R.color.transparent)
                }

                when (stateTmp[index + 1]) {
                    '0' -> button.background = null
                    '1' -> button.setBackgroundResource(R.drawable.background_green)
                    '2' -> button.setBackgroundResource(R.drawable.background_orange)
                    '3' -> button.setBackgroundColor(Color.parseColor("#AA000000"))
                    '4' -> button.setBackgroundColor(Color.parseColor("#55000000"))
                }

                index += 2
            }
        }
    }

    private fun clearGreenRed(state: String): String {
        var answer = state

        for (i in 0 until state.length) {
            if (i.rem(2) == 0 && state[i] in arrayOf('2', '3')) {
                answer = answer.substring(0, i) + '4' + answer.substring(i + 1)
            }

            if (i.rem(2) != 0 && state[i] in arrayOf('1', '2')) {
                answer = answer.substring(0, i) + '0' + answer.substring(i + 1)
            }
        }

        return answer
    }

    private fun clearGreen(state: String): String {
        var answer = state

        for (i in 0 until state.length) {
            if (i.rem(2) == 0 && state[i] == '2') {
                answer = answer.substring(0, i) + '4' + answer.substring(i + 1)
            }

            if (i.rem(2) != 0 && state[i] == '1') {
                answer = answer.substring(0, i) + '0' + answer.substring(i + 1)
            }
        }

        return answer
    }

    private fun cleanGreen(state: String, turn: Boolean): String {
        if ( (myColor == "w" && !turn) || (myColor == "b" && turn) ) {
            return state
        }

        return clearGreen(state)
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            showState()
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }
}
