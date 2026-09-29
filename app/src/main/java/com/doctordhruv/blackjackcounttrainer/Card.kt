package com.doctordhruv.blackjackcounttrainer.model

enum class Card(
    val label: String,
    val wongHalvesValue: Double
) {
    TWO("2", 0.5),
    THREE("3", 1.0),
    FOUR("4", 1.0),
    FIVE("5", 1.5),
    SIX("6", 1.0),
    SEVEN("7", 0.5),
    EIGHT("8", 0.0),
    NINE("9", -0.5),
    TEN("10", -1.0),
    JACK("J", -1.0),
    QUEEN("Q", -1.0),
    KING("K", -1.0),
    ACE("A", -1.0)
}
