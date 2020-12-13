package com.example.checkers

class Translator(language: String) {

    //main activity
    var player1Button = String()
    var player2Button = String()
    var onlineButton = String()
    var settingsButton = String()

    //1 player activity, online activity
    var score = String()
    var offerDraw = String()

    //2 players activity
    var name1Player2Players = String()
    var name2Player2Players = String()

    //rooms activity
    var roomsSearchBar = String()
    var createRoomButton = String()
    var joinButton = String()

    //settings activity
    var languageTextView = String()
    var nameTextView = String()
    var variants = String()

    init {
        when (language) {
            "English" -> {
                player1Button = "1 Player"
                player2Button = "2 Players"
                onlineButton = "Online"
                settingsButton = "Settings"

                score = "Score:"
                offerDraw = "Offer draw"

                name1Player2Players = "Player 1"
                name2Player2Players = "Player 2"

                roomsSearchBar = "Search room name"
                createRoomButton = "Create room"
                joinButton = "join"

                languageTextView = "Language:"
                nameTextView = "Name:"
                variants = "Variants:"
            }
            "Česky" -> {
                player1Button = "1 Hráč"
                player2Button = "2 Hráčů"
                onlineButton = "Online"
                settingsButton = "Nastavení"

                score = "Skóre:"
                offerDraw = "Nabídnout remízu"

                name1Player2Players = "Hráč 1"
                name2Player2Players = "Hráč 2"

                roomsSearchBar = "Hledat název místnosti"
                createRoomButton = "Vytvořit místnost"
                joinButton = "Připojit se"

                languageTextView = "Jazyk:"
                nameTextView = "Jméno:"
                variants = "Varianty:"
            }
            "Slovensky" -> {
                player1Button = "1 Hráč"
                player2Button = "2 Hráči"
                onlineButton = "Online"
                settingsButton = "Nastavenia"

                score = "Skóre:"
                offerDraw = "Ponúknuť remízu"

                name1Player2Players = "Hráč 1"
                name2Player2Players = "Hráč 2"

                roomsSearchBar = "Vyhľadajte názov miestnosti"
                createRoomButton = "Vytvoriť miestosť"
                joinButton = "Pripojiť sa"

                languageTextView = "Jazyk:"
                nameTextView = "Meno:"
                variants = "Varianty:"
            }
        }
    }
}