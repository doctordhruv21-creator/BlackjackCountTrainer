package com.doctordhruv.blackjackcounttrainer.model

enum class Card(
    val label: String,
    val hiLoValue: Int
) {
    TWO("2", 1),
    THREE("3", 1),
    FOUR("4", 1),
    FIVE("5", 1),
    SIX("6", 1),

    SEVEN("7", 0),
    EIGHT("8", 0),
    NINE("9", 0),

    TEN("10", -1),
    JACK("J", -1),
    QUEEN("Q", -1),
    KING("K", -1),
    ACE("A", -1)
}
