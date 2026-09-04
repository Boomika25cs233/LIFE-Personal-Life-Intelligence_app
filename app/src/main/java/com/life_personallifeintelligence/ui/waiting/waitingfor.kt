package com.life_personallifeintelligence.ui.waiting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// PREMIUM DARK THEME
// ============================================================

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val CardDark2 = Color(0xFF211B2A)

private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)
private val Blue = Color(0xFF62A8FF)
private val Green = Color(0xFF55D98A)
private val Orange = Color(0xFFFF9A62)

private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val SoftPurple = Color(0xFF2A2140)
private val SoftPink = Color(0xFF38202D)
private val SoftBlue = Color(0xFF1B2D43)
private val SoftGreen = Color(0xFF19372B)
private val SoftOrange = Color(0xFF3A281E)


// ============================================================
// WAITING TYPE
// ============================================================

enum class WaitingType {
    REFUND,
    PROMISE,
    DELIVERY
}


// ============================================================
// WAITING ITEM
// UI MODEL ONLY
//
// Later the real responsibility module can provide these values.
// ============================================================

data class WaitingItemUi(
    val id: Int,
    val title: String,
    val description: String,
    val requestedDate: String,
    val expectedDate: String,
    val type: WaitingType
)


// ============================================================
// MAIN SCREEN
// ============================================================

@Composable
fun WaitingForScreen(
    items: List<WaitingItemUi> = defaultWaitingItems(),
    onBackClick: () -> Unit = {},
    onItemClick: (WaitingItemUi) -> Unit = {},
    onAddWaiting: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ====================================================
        // PREMIUM HEADER
        // ====================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(285.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF26153E),
                            Color(0xFF47245D),
                            Color(0xFF15101D),
                            Background
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 22.dp
                    )
            ) {

                // BACK BUTTON

                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            onBackClick()
                        },
                    shape = CircleShape,
                    color = Color(0x25FFFFFF)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "‹",
                            color = White,
                            fontSize = 31.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                // HEADER TITLE

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(65.dp),
                        shape = RoundedCornerShape(21.dp),
                        color = Color(0x30FFFFFF)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "⏳",
                                fontSize = 30.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.size(14.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "WAITING FOR",
                            color = Color(0xFFD8C7EA),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Things you're expecting",
                            color = White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "LIFE keeps track while you live.",
                            color = TextGray,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // SUMMARY CARD

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0x20FFFFFF)
                ) {

                    Row(
                        modifier = Modifier.padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = SoftPurple
                        ) {

                            Box(
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "✦",
                                    color = Purple,
                                    fontSize = 20.sp
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.size(12.dp)
                        )

                        Column {

                            Text(
                                text = if (items.isEmpty()) {
                                    "Nothing pending"
                                } else {
                                    "${items.size} things you're waiting for"
                                },
                                color = White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "LIFE will keep them in context.",
                                color = TextGray,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }


        // ====================================================
        // CONTENT
        // ====================================================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Text(
                text = "Your waiting list",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Things that haven't happened yet",
                color = TextGray,
                fontSize = 11.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // =================================================
            // EMPTY STATE
            // =================================================

            if (items.isEmpty()) {

                WaitingEmptyState(
                    onAddWaiting = onAddWaiting
                )

            } else {

                items.forEach { item ->

                    WaitingItemCard(
                        item = item,
                        onClick = {
                            onItemClick(item)
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // SMALL PRIVACY NOTE
            // =================================================

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(17.dp),
                color = Color(0xFF131019)
            ) {

                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {

                    Text(
                        text = "🔒",
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.size(9.dp)
                    )

                    Text(
                        text = "Waiting items stay part of your LIFE context. "
                                + "You remain in control of what gets added.",
                        color = TextGray,
                        fontSize = 10.sp,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}


// ============================================================
// WAITING ITEM CARD
// ============================================================

@Composable
private fun WaitingItemCard(
    item: WaitingItemUi,
    onClick: () -> Unit
) {

    val icon = when (item.type) {

        WaitingType.REFUND -> "💰"

        WaitingType.PROMISE -> "🤝"

        WaitingType.DELIVERY -> "📦"
    }

    val accent = when (item.type) {

        WaitingType.REFUND -> Green

        WaitingType.PROMISE -> Pink

        WaitingType.DELIVERY -> Blue
    }

    val softAccent = when (item.type) {

        WaitingType.REFUND -> SoftGreen

        WaitingType.PROMISE -> SoftPink

        WaitingType.DELIVERY -> SoftBlue
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            // =================================================
            // TOP
            // =================================================

            Row(
                verticalAlignment = Alignment.Top
            ) {

                Surface(
                    modifier = Modifier.size(55.dp),
                    shape = RoundedCornerShape(17.dp),
                    color = softAccent
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = icon,
                            fontSize = 25.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.size(13.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = waitingLabel(item.type),
                        color = accent,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = item.title,
                        color = White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = item.description,
                        color = TextGray,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }

                Text(
                    text = "›",
                    color = accent,
                    fontSize = 28.sp
                )
            }


            Spacer(
                modifier = Modifier.height(18.dp)
            )


            // =================================================
            // DATE INFORMATION
            // =================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {

                DateInfo(
                    label = "REQUESTED",
                    value = item.requestedDate,
                    modifier = Modifier.weight(1f)
                )

                DateInfo(
                    label = "EXPECTED",
                    value = item.expectedDate,
                    modifier = Modifier.weight(1f)
                )
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // =================================================
            // STATUS
            // =================================================

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = softAccent
            ) {

                Row(
                    modifier = Modifier.padding(
                        horizontal = 13.dp,
                        vertical = 11.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(7.dp),
                        shape = CircleShape,
                        color = accent
                    ) {}

                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )

                    Text(
                        text = "Still waiting",
                        color = White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "View details",
                        color = accent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


// ============================================================
// DATE INFO
// ============================================================

@Composable
private fun DateInfo(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        color = CardDark2
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = label,
                color = TextGray,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = value,
                color = White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


// ============================================================
// EMPTY STATE
// ============================================================

@Composable
private fun WaitingEmptyState(
    onAddWaiting: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier.size(75.dp),
                shape = CircleShape,
                color = SoftPurple
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "⏳",
                        fontSize = 31.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Nothing to wait for",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "When you're expecting something, "
                        + "LIFE can keep it in context.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Button(
                onClick = onAddWaiting,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple
                )
            ) {

                Text(
                    text = "＋ Add waiting item",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


// ============================================================
// LABEL
// ============================================================

private fun waitingLabel(
    type: WaitingType
): String {

    return when (type) {

        WaitingType.REFUND ->
            "REFUND EXPECTED"

        WaitingType.PROMISE ->
            "PROMISE"

        WaitingType.DELIVERY ->
            "DELIVERY EXPECTED"
    }
}


// ============================================================
// DEMO UI DATA
//
// This is ONLY for showing your UI.
// Later replace it with real data.
// ============================================================

private fun defaultWaitingItems(): List<WaitingItemUi> {

    return listOf(

        WaitingItemUi(
            id = 1,
            title = "Refund",
            description =
                "Refund from your recent purchase",
            requestedDate = "Aug 28",
            expectedDate = "Sep 8",
            type = WaitingType.REFUND
        ),

        WaitingItemUi(
            id = 2,
            title = "Someone promised",
            description =
                "Waiting for a promised response or action",
            requestedDate = "Sep 1",
            expectedDate = "Sep 6",
            type = WaitingType.PROMISE
        ),

        WaitingItemUi(
            id = 3,
            title = "Delivery expected",
            description =
                "Your order is expected to arrive",
            requestedDate = "Sep 2",
            expectedDate = "Sep 7",
            type = WaitingType.DELIVERY
        )
    )
}