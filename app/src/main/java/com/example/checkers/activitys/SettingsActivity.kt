package com.example.checkers.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.checkers.R
import com.example.checkers.Translator
import com.example.checkers.adapters.VariantsAdapter
import com.example.checkers.datas.VariantsData
import com.example.checkers.datas.languageData
import com.tutorialsbuzz.customspinnerwithimagetext.languageAdapter
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    val languageData = ArrayList<languageData>()

    var language = String()

    var boardData = ArrayList<VariantsData>()
    var whiteData = ArrayList<VariantsData>()
    var blackData = ArrayList<VariantsData>()

    var board = String()
    var white = String()
    var black = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        inits()

        val languageAdapter = languageAdapter(this, languageData)
        val boardAdapter = VariantsAdapter(this, boardData)
        val whiteAdapter = VariantsAdapter(this, whiteData)
        val blackAdapter = VariantsAdapter(this, blackData)

        spinnerLanguage.adapter = languageAdapter
        spinnerBoard.adapter = boardAdapter
        spinnerWhite.adapter = whiteAdapter
        spinnerBlack.adapter = blackAdapter

        spinnerLanguage.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val clickedItem: languageData = parent.getItemAtPosition(position) as languageData
                language = clickedItem.jazyk
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerBoard.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val clickedItem: VariantsData = parent.getItemAtPosition(position) as VariantsData
                board = clickedItem.nazov
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerWhite.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val clickedItem: VariantsData = parent.getItemAtPosition(position) as VariantsData
                white = clickedItem.nazov
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerBlack.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val clickedItem: VariantsData = parent.getItemAtPosition(position) as VariantsData
                black = clickedItem.nazov
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        okButton.setOnClickListener {
            setData()

            nextActivity()
        }

        closeKeyboard()
    }

    private fun translate() {
        val preferences = getSharedPreferences("Settings", 0)
        val language = preferences.getString("language", "")!!

        val translator = Translator(language)

        languageTextView.text = translator.languageTextView
        nameTextView.text = translator.nameTextView
        variantsTextView.text = translator.variants
    }

    private fun inits() {
        initLanguagesData()

        initName()

        initBoardData()
        initWhiteData()
        initBlackData()

        translate()
    }

    private fun initBlackData() {
        //variant 1
        var data = VariantsData()

        data.image = R.drawable.variant1_black
        data.nazov = "blackVariant1"

        setBlack(data)

        //variant 2
        data = VariantsData()

        data.image = R.drawable.variant2_black
        data.nazov = "blackVariant2"

        setBlack(data)
    }

    private fun initWhiteData() {
        //variant 1
        var data = VariantsData()

        data.image = R.drawable.variant1_white
        data.nazov = "whiteVariant1"

        setWhite(data)

        //variant 2
        data = VariantsData()

        data.image = R.drawable.variant2_white
        data.nazov = "whiteVariant2"

        setWhite(data)
    }

    private fun initBoardData() {
        //classic board
        var data = VariantsData()

        data.image = R.drawable.classic_board
        data.nazov = "classicBoard"

        setBoard(data)

        //wood board
        data = VariantsData()

        data.image = R.drawable.wood_board
        data.nazov = "woodBoard"

        setBoard(data)

        //neon board
        data = VariantsData()

        data.image = R.drawable.neon_board
        data.nazov = "neonBoard"

        setBoard(data)
    }

    private fun setData() {
        val playerName = editName.text.toString()

        val preferences = getSharedPreferences("Settings", 0)
        val editor = preferences.edit()

        editor.putString("playerName", playerName)
        editor.putString("language", language)
        editor.putString("board", board)
        editor.putString("whiteVariant", white)
        editor.putString("blackVariant", black)

        editor.apply()
    }

    private fun initName() {
        val preferences = getSharedPreferences("Settings", 0)
        val playerName = preferences.getString("playerName", "")!!

        editName.setText(playerName)
    }

    private fun setLanguage(data: languageData) {
        val preferences = getSharedPreferences("Settings", 0)
        language = preferences.getString("language", "")!!

        if (language == data.jazyk) {
            languageData.add(0, data)
        } else {
            languageData.add(data)
        }
    }

    private fun setBoard(data: VariantsData) {
        val preferences = getSharedPreferences("Settings", 0)
        board = preferences.getString("board", "")!!

        if (board == data.nazov) {
            boardData.add(0, data)
        } else {
            boardData.add(data)
        }
    }

    private fun setWhite(data: VariantsData) {
        val preferences = getSharedPreferences("Settings", 0)
        white = preferences.getString("whiteVariant", "")!!

        if (white == data.nazov) {
            whiteData.add(0, data)
        } else {
            whiteData.add(data)
        }
    }

    private fun setBlack(data: VariantsData) {
        val preferences = getSharedPreferences("Settings", 0)
        black = preferences.getString("blackVariant", "")!!

        if (black == data.nazov) {
            blackData.add(0, data)
        } else {
            blackData.add(data)
        }
    }

    private fun initLanguagesData() {
        //Cesky
        var data = languageData()

        data.vlajka = R.drawable.ic_flag_czech
        data.jazyk = "Česky"

        setLanguage(data)

        //English
        data = languageData()

        data.vlajka = R.drawable.ic_flag_english
        data.jazyk = "English"

        setLanguage(data)

        //Slovensky
        data = languageData()

        data.vlajka = R.drawable.ic_flag_slovakia
        data.jazyk = "Slovensky"

        setLanguage(data)
    }

    private fun nextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun closeKeyboard() {
        val view = this.currentFocus

        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
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
