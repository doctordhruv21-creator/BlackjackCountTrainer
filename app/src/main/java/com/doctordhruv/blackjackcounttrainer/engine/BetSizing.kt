package com.doctordhruv.blackjackcounttrainer.engine

data class BetRecommendation(
    val amount: Int,
    val units: Int
)

object BetSizing {

    private const val UNIT_AMOUNT = 100

    fun recommendation(trueCount: Double): BetRecommendation {

        val units = when {
            trueCount >= 4.0 -> 8
            trueCount >= 3.0 -> 4
            trueCount >= 2.0 -> 2
            else -> 1
        }

        return BetRecommendation(
            amount = UNIT_AMOUNT * units,
            units = units
        )
    }
}
