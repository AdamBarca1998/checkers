package com.example.checkers

class Game {

    private val abeceda = ArrayList<Char>()

    init {
        for (c in 'a' .. 'h') {
            abeceda.add(c)
        }
    }

    private fun suradnice(index:Int): String {
        var answer = "null"

        val pismenoIndex = index.rem(8)
        val riadok = (index - pismenoIndex)/8

        answer = "${abeceda[pismenoIndex]}${riadok+1}"

        return answer
    }

    fun clickButton(index: Int): String {
        var answer = "null"

        answer = suradnice(index)

        return answer
    }
}