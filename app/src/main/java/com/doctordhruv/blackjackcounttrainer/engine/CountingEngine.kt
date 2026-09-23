package com.doctordhruv.blackjackcounttrainer.engine

data class CountState(
    val totalCards: Int = 0,
    val cardsSeen: Int = 0,
    val runningCount: Int = 0
) {
    val cardsRemaining: Int
        get() = (totalCards - cardsSeen).coerceAtLeast(0)

    val decksRemaining: Double
        get() = cardsRemaining / 52.0

    val trueCount: Double
        get() = if (decksRemaining > 0) {
            runningCount / decksRemaining
        } else {
            0.0
        }
}

class CountingEngine {

    fun createNewShoe(decks: Int): CountState {
        require(decks in listOf(1, 2, 4, 6, 8))

        return CountState(
            totalCards = decks * 52,
            cardsSeen = 0,
            runningCount = 0
        )
    }

    fun applyHand(
        state: CountState,
        countChange: Int,
        cardsCount: Int
    ): CountState {

        require(cardsCount >= 0) {
            "Card count cannot be negative."
        }

        require(
            state.cardsSeen + cardsCount <= state.totalCards
        ) {
            "More cards entered than available in the shoe."
        }

        return state.copy(
            cardsSeen = state.cardsSeen + cardsCount,
            runningCount = state.runningCount + countChange
        )
    }
}
