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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.doctordhruv.blackjackcounttrainer.engine.BetSizing
import com.doctordhruv.blackjackcounttrainer.engine.CountingEngine
import com.doctordhruv.blackjackcounttrainer.engine.CountState
import com.doctordhruv.blackjackcounttrainer.ui.TrackerScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackjackCountTrainerApp()
        }
    }
}

@Composable
fun BlackjackCountTrainerApp() {
    val engine = remember { CountingEngine() }

    var selectedDecks by remember { mutableStateOf<Int?>(null) }
    var shoeStarted by remember { mutableStateOf(false) }
    var countState by remember { mutableStateOf<CountState?>(null) }
    var unitAmountText by remember { mutableStateOf("100") }

    // Cards entered for the current hand but not yet committed to the shoe.
    val pendingCards = remember { mutableStateListOf<Double>() }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (!shoeStarted) {
                ShoeSetupScreen(
                    selectedDecks = selectedDecks,
                    unitAmountText = unitAmountText,
                    onDeckSelected = { selectedDecks = it },
                    onUnitAmountChanged = { unitAmountText = it },
                    onStart = {
                        val decks = selectedDecks ?: return@ShoeSetupScreen
                        val unitAmount = unitAmountText.toIntOrNull()
                            ?.coerceAtLeast(1) ?: 100

                        unitAmountText = unitAmount.toString()
                        countState = engine.createNewShoe(decks)
                        pendingCards.clear()
                        shoeStarted = true
                    }
                )
            } else {
                val state = countState

                if (state != null) {
                    val unitAmount = unitAmountText.toIntOrNull()
                        ?.coerceAtLeast(1) ?: 100

                    val betRecommendation =
                        BetSizing.recommendation(
                            trueCount = state.trueCount,
                            unitAmount = unitAmount
                        )

                    TrackerScreen(
                        runningCount = state.runningCount,
                        trueCount = state.trueCount,
                        cardsSeen = state.cardsSeen,
                        cardsRemaining = state.cardsRemaining,
                        decksRemaining = state.decksRemaining,
                        pendingCount = pendingCards.sum(),
                        pendingCards = pendingCards.size,
                        betRecommendation = betRecommendation,
                        unitAmount = unitAmount,
                        onCountSelected = { value ->
                            if (state.cardsRemaining > pendingCards.size) {
                                pendingCards.add(value)
                            }
                        },
                        onUndo = {
                            if (pendingCards.isNotEmpty()) {
                                pendingCards.removeAt(pendingCards.lastIndex)
                            }
                        },
                        onEndHand = {
                            if (pendingCards.isNotEmpty()) {
                                countState = engine.applyCards(
                                    state = state,
                                    countChange = pendingCards.sum(),
                                    cardsCount = pendingCards.size
                                )
                                pendingCards.clear()
                            }
                        },
                        onClearHand = {
                            pendingCards.clear()
                        },
                        onNewShoe = {
                            selectedDecks = null
                            countState = null
                            pendingCards.clear()
                            shoeStarted = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShoeSetupScreen(
    selectedDecks: Int?,
    unitAmountText: String,
    onDeckSelected: (Int) -> Unit,
    onUnitAmountChanged: (String) -> Unit,
    onStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "WONG HALVES",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "BLACKJACK COUNT TRAINER",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(text = "SELECT SHOE SIZE")

        Spacer(modifier = Modifier.height(10.dp))

        listOf(1, 2, 4, 6, 8).forEach { decks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDecks == decks,
                    onClick = { onDeckSelected(decks) }
                )
                Text(
                    text = "$decks Deck" +
                        if (decks > 1) "s" else ""
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = unitAmountText,
            onValueChange = {
                if (it.length <= 7 && it.all(Char::isDigit)) {
                    onUnitAmountChanged(it)
                }
            },
            label = { Text("1 Unit Value") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            enabled = selectedDecks != null &&
                unitAmountText.toIntOrNull()?.let { it > 0 } == true,
            onClick = onStart
        ) {
            Text("START SHOE")
        }
    }
}
