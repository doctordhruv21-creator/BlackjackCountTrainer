package com.doctordhruv.blackjackcounttrainer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
    runningCount: Int,
    trueCount: Double,
    cardsSeen: Int,
    cardsRemaining: Int,
    decksRemaining: Double,
    playerCount: Int,
    playerCardsCount: Int,
    dealerCount: Int,
    dealerCardsCount: Int,
    selectedTarget: CardTarget,
    betRecommendation: BetRecommendation,
    onTargetChanged: (CardTarget) -> Unit,
    onCountSelected: (Int) -> Unit,
    onEndHand: () -> Unit,
    onClearHand: () -> Unit,
    onNewShoe: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // HEADER
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "BLACKJACK",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "COUNT TRAINER",
                color = Color(0xFF94A3B8),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )
        }

        // COUNT DASHBOARD
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            StatCard(
                modifier = Modifier.weight(1f),
                title = "RUNNING COUNT",
                value = if (runningCount >= 0) {
                    "+$runningCount"
                } else {
                    "$runningCount"
                }
            )

            StatCard(
                modifier = Modifier.weight(1f),
                title = "TRUE COUNT",
                value = if (trueCount >= 0) {
                    "+%.2f".format(trueCount)
                } else {
                    "%.2f".format(trueCount)
                }
            )
        }

        // BET CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF064E3B)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "RECOMMENDED BET",
                    color = Color(0xFFA7F3D0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "₹${betRecommendation.amount}",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${betRecommendation.units} UNIT" +
                            if (betRecommendation.units > 1) "S" else "",
                    color = Color(0xFFA7F3D0),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // CURRENT HAND
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E293B)
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "CURRENT HAND",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                HandCountRow(
                    title = "PLAYER",
                    count = playerCount,
                    cards = playerCardsCount
                )

                HorizontalDivider(
                    color = Color(0xFF334155)
                )

                HandCountRow(
                    title = "DEALER",
                    count = dealerCount,
                    cards = dealerCardsCount
                )
            }
        }

        // TARGET
        Text(
            text = "ADD CARD TO",
            color = Color(0xFFCBD5E1),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            TargetButton(
                modifier = Modifier.weight(1f),
                text = "PLAYER",
                selected = selectedTarget == CardTarget.PLAYER,
                onClick = {
                    onTargetChanged(CardTarget.PLAYER)
                }
            )

            TargetButton(
                modifier = Modifier.weight(1f),
                text = "DEALER",
                selected = selectedTarget == CardTarget.DEALER,
                onClick = {
                    onTargetChanged(CardTarget.DEALER)
                }
            )
        }

        // CARD GROUPS
        Text(
            text = "SELECT CARD GROUP",
            color = Color(0xFFCBD5E1),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        CountButton(
            modifier = Modifier.fillMaxWidth(),
            cards = "2   3   4   5   6",
            count = "+1",
            subtitle = "LOW CARDS",
            backgroundColor = Color(0xFF166534),
            onClick = {
                onCountSelected(1)
            }
        )

        CountButton(
            modifier = Modifier.fillMaxWidth(),
            cards = "7   8   9",
            count = "0",
            subtitle = "NEUTRAL CARDS",
            backgroundColor = Color(0xFF155E75),
            onClick = {
                onCountSelected(0)
            }
        )

        CountButton(
            modifier = Modifier.fillMaxWidth(),
            cards = "10   J   Q   K   A",
            count = "−1",
            subtitle = "HIGH CARDS",
            backgroundColor = Color(0xFF991B1B),
            onClick = {
                onCountSelected(-1)
            }
        )

        // ACTIONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onClearHand,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("CLEAR")
            }

            Button(
                modifier = Modifier.weight(1f),
                enabled = playerCardsCount > 0 &&
                        dealerCardsCount > 0,
                onClick = onEndHand,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("END HAND")
            }
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNewShoe,
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("NEW SHOE")
        }

        // SHOE INFO
        Text(
            text = "$cardsSeen cards seen  •  " +
                    "$cardsRemaining remaining  •  " +
                    "%.2f decks remaining".format(decksRemaining),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF64748B),
            fontSize = 11.sp
        )
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
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                color = Color(0xFF94A3B8),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

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
private fun HandCountRow(
    title: String,
    count: Int,
    cards: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = Color(0xFFCBD5E1),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )

        Text(
            text = if (count >= 0) "+$count" else "$count",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = "  •  $cards card" +
                    if (cards == 1) "" else "s",
            color = Color(0xFF64748B),
            fontSize = 11.sp
        )
    }
}

@Composable
private fun TargetButton(
    modifier: Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                Color(0xFF0EA5E9)
            } else {
                Color(0xFF334155)
            },
            contentColor = Color.White
        )
    ) {

        Text(
            text = if (selected) "✓ $text" else text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CountButton(
    modifier: Modifier,
    cards: String,
    count: String,
    subtitle: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier.height(70.dp),
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = cards,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = count,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
