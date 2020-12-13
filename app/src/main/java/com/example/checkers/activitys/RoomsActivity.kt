package com.example.checkers.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.checkers.R
import com.example.checkers.Translator
import com.example.checkers.adapters.MyListAdapter
import com.example.checkers.datas.RoomsData
import kotlinx.android.synthetic.main.activity_rooms.*
import kotlinx.android.synthetic.main.user_search.view.*
import org.json.JSONArray
import org.json.JSONException
import android.os.Handler
import android.widget.Toast
import org.json.JSONObject

class RoomsActivity : AppCompatActivity() {
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 1000

    val roomsData = ArrayList<RoomsData>()
    val namesRooms = ArrayList<String>()

    var roomsJson = JSONArray()

    var idRoom = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        inits()

        rooms.setOnItemClickListener() { adapterView, view, position, id ->
            idRoom = adapterView.getItemAtPosition(position).toString()

            connectRoom(idRoom)
        }

        createRoomButton.setOnClickListener {
            createRoom()
        }

        joinButton.setOnClickListener {
            if (roomsData.isNotEmpty()) {
                idRoom = roomsData[0].roomName
                connectRoom(idRoom)
            } else {
                createRoom()
            }
        }
    }

    private fun createRoom() {
        val preferences = getSharedPreferences("Settings", 0)
        val playerName = preferences.getString("playerName", "")!!

        val jsonObject = JSONObject()
        jsonObject.put("name", playerName)

        val queue = Volley.newRequestQueue(this)
        val url = "https://adambarca123.pythonanywhere.com/game/"

        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject, Response.Listener {
                response ->try {
            nextLoading()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun getRooms() {
        val queue = Volley.newRequestQueue(this)

        val url = "https://adambarca123.pythonanywhere.com/game/"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            roomsJson = response.getJSONArray("rooms")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(request)
    }

    private fun connectRoom(id: String) {
        val preferences = getSharedPreferences("Settings", 0)
        val playerName = preferences.getString("playerName", "")!!

        val jsonObject = JSONObject()
        jsonObject.put("name", playerName)

        val queue = Volley.newRequestQueue(this)
        val url = "https://adambarca123.pythonanywhere.com/game/$id/"

        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject, Response.Listener {
                response ->try {
            nextGame()
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

        roomsSeachBar.editText.setText(translator.roomsSearchBar)
        createRoomButton.text = translator.createRoomButton
        joinButton.text = translator.joinButton
    }

    private fun inits() {
        showRooms()

        translate()
    }

    private fun nextLoading() {
        val intent = Intent(this, LoadingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun nextGame() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("id", idRoom)
        intent.putExtra("color", "b")

        startActivity(intent)
        finish()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
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
            namesRooms.add(employee.getString("id"))
         }
    }

    private fun showRooms() {
        //room 1
        roomsData.clear()
        namesRooms.clear()

        getRooms()
        JsonToData()
        deleteRooms()

        val myListAdapter = MyListAdapter(this, roomsData, namesRooms)
        rooms.adapter = myListAdapter
    }

    private fun deleteData(name: String):ArrayList<String> {
        val answer = ArrayList<String>()

        var i = 0
        while (i < roomsData.size) {
            if (roomsData[i].player1 == name) {
                answer.add(roomsData[i].roomName)
                roomsData.removeAt(i)
                namesRooms.removeAt(i)
                i -= 1
            }
            i += 1
        }

        return answer
    }

    private fun deleteRooms() {
        val queue = Volley.newRequestQueue(this)

        val preferences = getSharedPreferences("Settings", 0)
        val playerName = preferences.getString("playerName", "")!!

        val array = deleteData(playerName)

        for (id in array) {
            val url = "https://adambarca123.pythonanywhere.com/game/$id/"

            val request =
                JsonObjectRequest(Request.Method.DELETE, url, null, Response.Listener { response ->
                    try {
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error -> error.printStackTrace() })

            queue.add(request)
        }
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            showRooms()
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
