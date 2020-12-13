package com.example.checkers.datas

import com.example.checkers.R

data class RoomsData(var roomName: String = "",
                     var player1: String = "",
                     var player2: String = "",
                     var colorChip1: Int = R.drawable.ic_random_chip,
                     var colorChip2: Int? = null,
                     var locked: Int = R.drawable.ic_unlock
)