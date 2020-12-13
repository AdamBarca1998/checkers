package com.tutorialsbuzz.customspinnerwithimagetext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.checkers.R
import com.example.checkers.datas.languageData

class languageAdapter(val context: Context, var dataSource: ArrayList<languageData>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.language, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        vh.vlajka.setImageResource(dataSource[position].vlajka)
        vh.jazyk.text = dataSource[position].jazyk

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val vlajka: ImageView
        val jazyk: TextView

        init {
            vlajka = row?.findViewById(R.id.vlajka) as ImageView
            jazyk = row?.findViewById(R.id.jazyk) as TextView
        }
    }

}