package com.doctordhruv.blackjackcounttrainer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doctordhruv.blackjackcounttrainer.engine.BetRecommendation

@Composable
fun TrackerScreen(
    runningCount: Double,
    trueCount: Double,
    cardsSeen: Int,
    cardsRemaining: Int,
    decksRemaining: Double,
    pendingCount: Double,
    pendingCards: Int,
    betRecommendation: BetRecommendation,
    unitAmount: Int,
    onCountSelected: (Double) -> Unit,
    onUndo: () -> Unit,
    onEndHand: () -> Unit,
    onClearHand: () -> Unit,
    onNewShoe: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "WONG HALVES",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "BLACKJACK COUNT TRAINER",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.5.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "RUNNING COUNT",
                value = formatCount(runningCount)
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "TRUE COUNT",
                value = formatCount(trueCount)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BET",
                    color = Color(0xFFA7F3D0),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "${betRecommendation.units} UNIT" +
                        if (betRecommendation.units == 1) "" else "S",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Amount: ${betRecommendation.amount}  •  1 unit = $unitAmount",
                    color = Color(0xFFA7F3D0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (pendingCards > 0) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CURRENT HAND",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "$pendingCards cards  •  contribution ${formatCount(pendingCount)}",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = "PENDING",
                        color = Color(0xFF38BDF8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = "WONG HALVES",
            color = Color(0xFFCBD5E1),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CountButton(
                modifier = Modifier.weight(1f),
                cards = "5",
                value = "+1.5",
                onClick = { onCountSelected(1.5) }
            )
            CountButton(
                modifier = Modifier.weight(1f),
                cards = "3 • 4 • 6",
                value = "+1",
                onClick = { onCountSelected(1.0) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CountButton(
                modifier = Modifier.weight(1f),
                cards = "2 • 7",
                value = "+0.5",
                onClick = { onCountSelected(0.5) }
            )
            CountButton(
                modifier = Modifier.weight(1f),
                cards = "8",
                value = "0",
                onClick = { onCountSelected(0.0) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CountButton(
                modifier = Modifier.weight(1f),
                cards = "9",
                value = "−0.5",
                onClick = { onCountSelected(-0.5) }
            )
            CountButton(
                modifier = Modifier.weight(1f),
                cards = "10 • J • Q • K • A",
                value = "−1",
                onClick = { onCountSelected(-1.0) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                enabled = pendingCards > 0,
                onClick = onUndo,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("UNDO")
            }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                enabled = pendingCards > 0,
                onClick = onClearHand,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("CLEAR")
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = pendingCards > 0,
            onClick = onEndHand,
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("END HAND")
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNewShoe,
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("NEW SHOE")
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "$cardsSeen cards seen  •  $cardsRemaining remaining  •  " +
                "%.2f decks remaining".format(decksRemaining),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF64748B),
            fontSize = 11.sp
        )
    }
}

private fun formatCount(value: Double): String {
    val rounded = kotlin.math.round(value * 2.0) / 2.0
    return if (rounded > 0.0) {
        "+%.1f".format(rounded)
    } else {
        "%.1f".format(rounded)
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    title: String,
    value: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color(0xFF94A3B8),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = value,
                color = Color(0xFF38BDF8),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun CountButton(
    modifier: Modifier,
    cards: String,
    value: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(74.dp),
        onClick = onClick,
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1E293B),
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cards,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = value,
                color = Color(0xFF38BDF8),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
