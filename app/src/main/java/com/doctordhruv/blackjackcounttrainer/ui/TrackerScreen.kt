package com.doctordhruv.blackjackcounttrainer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doctordhruv.blackjackcounttrainer.model.Card

@Composable
fun TrackerScreen(
    runningCount: Int,
    trueCount: Double,
    cardsSeen: Int,
    cardsRemaining: Int,
    decksRemaining: Double,
    playerCards: List<Card>,
    dealerCards: List<Card>,
    selectedTarget: CardTarget,
    onTargetChanged: (CardTarget) -> Unit,
    onCardSelected: (Card) -> Unit,
    onEndHand: () -> Unit,
    onClearHand: () -> Unit,
    onNewShoe: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("BLACKJACK COUNT TRAINER")

        Spacer(modifier = Modifier.height(12.dp))

        Text("Running Count: $runningCount")
        Text("True Count: %.2f".format(trueCount))
        Text("Cards Seen: $cardsSeen")
        Text("Cards Remaining: $cardsRemaining")
        Text("Decks Remaining: %.2f".format(decksRemaining))

        Spacer(modifier = Modifier.height(20.dp))

        Text("PLAYER")

        CardRow(playerCards)

        Spacer(modifier = Modifier.height(8.dp))

        Text("DEALER")

        CardRow(dealerCards)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    onTargetChanged(CardTarget.PLAYER)
                }
            ) {
                Text(
                    if (selectedTarget == CardTarget.PLAYER)
                        "✓ PLAYER"
                    else
                        "PLAYER"
                )
            }

            Button(
                onClick = {
                    onTargetChanged(CardTarget.DEALER)
                }
            ) {
                Text(
                    if (selectedTarget == CardTarget.DEALER)
                        "✓ DEALER"
                    else
                        "DEALER"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("SELECT CARD")

        Spacer(modifier = Modifier.height(8.dp))

        CardGrid(
            onCardSelected = onCardSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedButton(
                onClick = onClearHand
            ) {
                Text("CLEAR")
            }

            Button(
                onClick = onEndHand,
                enabled = playerCards.isNotEmpty() &&
                        dealerCards.isNotEmpty()
            ) {
                Text("END HAND")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onNewShoe,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("NEW SHOE")
        }
    }
}

@Composable
private fun CardGrid(
    onCardSelected: (Card) -> Unit
) {

    Column {

        Card.entries.toList().chunked(5).forEach { rowCards ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                rowCards.forEach { card ->

                    Button(
                        onClick = {
                            onCardSelected(card)
                        },
                        modifier = Modifier.width(62.dp)
                    ) {
                        Text(card.label)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun CardRow(
    cards: List<Card>
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        items(cards) { card ->

            Card {

                Text(
                    text = card.label,
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    )
                )
            }
        }
    }
}
