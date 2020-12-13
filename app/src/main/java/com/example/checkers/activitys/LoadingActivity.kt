package com.example.checkers.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.checkers.R
import kotlinx.android.synthetic.main.activity_loading.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.checkers.datas.RoomsData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LoadingActivity : AppCompatActivity() {
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 1000

    var roomsJson = JSONArray()
    var roomJson = JSONObject()
    val roomsData = ArrayList<RoomsData>()

    var id = "-1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
        loadingImage.animation = animation

        getRooms()
    }

    private fun getRooms() {
        val queue = Volley.newRequestQueue(this)

        val url = "https://adambarca123.pythonanywhere.com/game/"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            roomsJson = response.getJSONArray("rooms")
            getId()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun JsonToData() {
        var data = RoomsData()

        for (i in 0 until roomsJson.length()) {
            val employee = roomsJson.getJSONObject(i)

            data = RoomsData()

            data.roomName = employee.getString("id")
            data.player1 = employee.getString("player")
            data.colorChip1 = R.drawable.ic_white_chip
            data.locked = R.drawable.ic_unlock

            roomsData.add(data)
        }
    }

    private fun searchId() {
        val preferences = getSharedPreferences("Settings", 0)
        val playerName = preferences.getString("playerName", "")!!

        for (i in roomsData) {
            if (i.player1 == playerName) {
                id = i.roomName
                break
            }
        }
    }

    private fun getId() {
        JsonToData()

        searchId()
    }

    private fun isStart() {
        if (roomJson.getString("state") == "start") {
            nextActivity()
        }
    }

    private fun controlState() {
        val queue = Volley.newRequestQueue(this)

        val url = "https://adambarca123.pythonanywhere.com/game/$id/"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            roomJson = response
            isStart()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun nextActivity() {
        intent = Intent(this, GameActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("color", "w")

        startActivity(intent)
        finish()
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            controlState()
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
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
