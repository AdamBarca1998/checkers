package com.example.checkers.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.checkers.R
import com.example.checkers.datas.TahyData

class TahyAdapter(private val context: Activity, private val data: ArrayList<TahyData>, private val cislaTahu: ArrayList<String>)
    : ArrayAdapter<String>(context,
    R.layout.activity_game, cislaTahu) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.write_list, null, true)

        val cislo = rowView.findViewById(R.id.cislo) as TextView
        val farba = rowView.findViewById(R.id.farba) as ImageView
        val tah = rowView.findViewById(R.id.tah) as TextView

        cislo.text = cislaTahu[position]
        farba.setImageResource(data[position].color)
        tah.text = data[position].tah

        return rowView
    }
}