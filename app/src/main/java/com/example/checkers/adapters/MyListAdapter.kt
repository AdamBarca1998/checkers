package com.example.checkers.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.checkers.R
import com.example.checkers.datas.RoomsData

class MyListAdapter(private val context: Activity, private val data: ArrayList<RoomsData>, private val roomsNames: ArrayList<String>)
    : ArrayAdapter<String>(context,
    R.layout.activity_rooms, roomsNames) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val roomName = rowView.findViewById(R.id.roomName) as TextView
        val player1 = rowView.findViewById(R.id.player1Button) as TextView
        val player2 = rowView.findViewById(R.id.player2Button) as TextView
        val chip1 = rowView.findViewById(R.id.chip1View) as ImageView
        val chip2 = rowView.findViewById(R.id.chip2View) as ImageView
        val lock = rowView.findViewById(R.id.lockView) as ImageView

        roomName.text = roomsNames[position]
        player1.text = data[position].player1
        player2.text = data[position].player2
        chip1.setImageResource(data[position].colorChip1)

        if (data[position].colorChip2 != null) {
            chip2.setImageResource(data[position].colorChip2!!)
        } else {
            chip2.setImageResource(0)
        }

        lock.setImageResource(data[position].locked)

        return rowView
    }
}