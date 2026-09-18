package com.doctordhruv.blackjackcounttrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doctordhruv.blackjackcounttrainer.engine.CountingEngine
import com.doctordhruv.blackjackcounttrainer.model.Card
import com.doctordhruv.blackjackcounttrainer.ui.TrackerScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BlackjackCountTrainerApp()
        }
    }
}

@androidx.compose.runtime.Composable
fun BlackjackCountTrainerApp() {

    var selectedDecks by remember {
        mutableStateOf<Int?>(null)
    }

    var shoeStarted by remember {
        mutableStateOf(false)
    }

    var countState by remember {
        mutableStateOf(CountingEngine().createNewShoe(1))
    }

    var playerCards by remember {
        mutableStateOf<List<Card>>(emptyList())
    }

    var dealerCards by remember {
        mutableStateOf<List<Card>>(emptyList())
    }

    val engine = remember {
        CountingEngine()
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            if (!shoeStarted) {

                ShoeSetupScreen(
                    selectedDecks = selectedDecks,
                    onDeckSelected = {
                        selectedDecks = it
                    },
                    onStart = {

                        selectedDecks?.let { decks ->

                            countState =
                                engine.createNewShoe(decks)

                            playerCards = emptyList()
                            dealerCards = emptyList()

                            shoeStarted = true
                        }
                    }
                )

            } else {

                TrackerScreen(
                    decks = selectedDecks ?: 1,
                    runningCount = countState.runningCount,
                    trueCount = countState.trueCount,
                    cardsSeen = countState.cardsSeen,
                    cardsRemaining = countState.cardsRemaining,
                    decksRemaining = countState.decksRemaining,

                    playerCards = playerCards,
                    dealerCards = dealerCards,

                    onCardSelected = { card ->

                        /*
                         * Cards are only added to the
                         * current hand here.
                         *
                         * Counting happens when
                         * END HAND is pressed.
                         */
                        if (playerCards.size <= dealerCards.size) {
                            playerCards =
                                playerCards + card
                        } else {
                            dealerCards =
                                dealerCards + card
                        }
                    },

                    onEndHand = {

                        val exposedCards =
                            playerCards + dealerCards

                        countState =
                            engine.applyHand(
                                countState,
                                exposedCards
                            )

                        playerCards = emptyList()
                        dealerCards = emptyList()
                    },

                    onClearHand = {
                        playerCards = emptyList()
                        dealerCards = emptyList()
                    },

                    onNewShoe = {

                        selectedDecks = null
                        shoeStarted = false

                        playerCards = emptyList()
                        dealerCards = emptyList()

                        countState =
                            engine.createNewShoe(1)
                    }
                )
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun ShoeSetupScreen(
    selectedDecks: Int?,
    onDeckSelected: (Int) -> Unit,
    onStart: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text = "BLACKJACK",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "COUNT TRAINER",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text("SELECT SHOE SIZE")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        listOf(1, 2, 4, 6, 8).forEach { decks ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                RadioButton(
                    selected =
                        selectedDecks == decks,

                    onClick = {
                        onDeckSelected(decks)
                    }
                )

                Text(
                    text =
                        "$decks Deck" +
                                if (decks > 1) "s"
                                else ""
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            enabled = selectedDecks != null,
            onClick = onStart
        ) {

            Text("START SHOE")
        }
    }
}
