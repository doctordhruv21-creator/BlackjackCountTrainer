package com.doctordhruv.blackjackcounttrainer.engine

data class BetRecommendation(
    val units: Int,
    val amount: Int
)

object BetSizing {

    fun recommendation(
        trueCount: Double,
        unitAmount: Int
    ): BetRecommendation {

        val units = when {
            trueCount >= 6.0 -> 12
            trueCount >= 5.0 -> 10
            trueCount >= 4.0 -> 8
            trueCount >= 3.0 -> 6
            trueCount >= 2.0 -> 4
            trueCount >= 1.0 -> 2
            else -> 1
        }

        return BetRecommendation(
            units = units,
            amount = units * unitAmount
        )
    }
}
