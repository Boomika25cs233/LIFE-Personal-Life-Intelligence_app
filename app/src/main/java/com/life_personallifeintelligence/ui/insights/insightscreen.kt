package com.life_personallifeintelligence.ui.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
// INSIGHT TYPE
// ============================================================

enum class InsightType {
    ANOMALY,
    PATTERN,
    PREDICTION,
    WAITING,
    POSITIVE
}


// ============================================================
// UI MODEL
// ============================================================

data class InsightUi(
    val id: Int,
    val title: String,
    val description: String,
    val type: InsightType,
    val supportingText: String = ""
)


// ============================================================
// MAIN INSIGHTS SCREEN
// ============================================================

@Composable
fun InsightsScreen(
    insights: List<InsightUi> = defaultInsights(),
    onBackClick: () -> Unit = {},
    onInsightClick: (InsightUi) -> Unit = {}
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
                .height(275.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF26153E),
                            Color(0xFF42235C),
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

                // HEADER

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(21.dp),
                        color = Color(0x30FFFFFF)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "🧠",
                                fontSize = 30.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "LIFE INSIGHTS",
                            color = Color(0xFFD9C9EA),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Things LIFE noticed",
                            color = White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Small patterns that may matter to you.",
                            color = TextGray,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(23.dp)
                )

                // AI SUMMARY

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
                            modifier = Modifier.width(12.dp)
                        )

                        Column {

                            Text(
                                text = if (insights.isEmpty()) {
                                    "Nothing new to notice"
                                } else {
                                    "${insights.size} things LIFE noticed"
                                },
                                color = White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "Your life, understood quietly.",
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
                text = "Your insights",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Patterns, changes and predictions",
                color = TextGray,
                fontSize = 11.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // =================================================
            // EMPTY STATE
            // =================================================

            if (insights.isEmpty()) {

                InsightsEmptyState()

            } else {

                insights.forEach { insight ->

                    InsightCard(
                        insight = insight,
                        onClick = {
                            onInsightClick(insight)
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(22.dp)
            )


            // =================================================
            // PRIVACY NOTE
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
                        modifier = Modifier.width(9.dp)
                    )

                    Text(
                        text = "Insights are meant to help you notice "
                                + "patterns, not make decisions for you.",
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
// INSIGHT CARD
// ============================================================

@Composable
private fun InsightCard(
    insight: InsightUi,
    onClick: () -> Unit
) {

    val icon = when (insight.type) {

        InsightType.ANOMALY -> "⚠️"

        InsightType.PATTERN -> "📈"

        InsightType.PREDICTION -> "🔮"

        InsightType.WAITING -> "⏳"

        InsightType.POSITIVE -> "✨"
    }


    val accent = when (insight.type) {

        InsightType.ANOMALY -> Orange

        InsightType.PATTERN -> Blue

        InsightType.PREDICTION -> Purple

        InsightType.WAITING -> Pink

        InsightType.POSITIVE -> Green
    }


    val softAccent = when (insight.type) {

        InsightType.ANOMALY -> SoftOrange

        InsightType.PATTERN -> SoftBlue

        InsightType.PREDICTION -> SoftPurple

        InsightType.WAITING -> SoftPink

        InsightType.POSITIVE -> SoftGreen
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
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
                    modifier = Modifier.size(54.dp),
                    shape = RoundedCornerShape(17.dp),
                    color = softAccent
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = icon,
                            fontSize = 24.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.width(13.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = insightLabel(insight.type),
                        color = accent,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.1.sp
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = insight.title,
                        color = White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = insight.description,
                        color = TextGray,
                        fontSize = 11.sp,
                        lineHeight = 17.sp
                    )
                }

                Text(
                    text = "›",
                    color = accent,
                    fontSize = 27.sp
                )
            }


            // =================================================
            // SUPPORTING INFORMATION
            // =================================================

            if (insight.supportingText.isNotBlank()) {

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                HorizontalDivider(
                    color = Color(0x18FFFFFF),
                    thickness = 1.dp
                )

                Spacer(
                    modifier = Modifier.height(13.dp)
                )

                Text(
                    text = insight.supportingText,
                    color = White,
                    fontSize = 10.sp,
                    lineHeight = 16.sp
                )
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // =================================================
            // VIEW DETAILS
            // =================================================

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = softAccent
            ) {

                Row(
                    modifier = Modifier.padding(
                        horizontal = 13.dp,
                        vertical = 10.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(7.dp),
                        shape = CircleShape,
                        color = accent
                    ) {}

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "LIFE noticed this",
                        color = White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "View ›",
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
// EMPTY STATE
// ============================================================

@Composable
private fun InsightsEmptyState() {

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
                        text = "🧠",
                        fontSize = 30.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Nothing to notice yet",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = "As LIFE learns your routines and patterns, "
                        + "useful insights will appear here.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }
    }
}


// ============================================================
// LABEL
// ============================================================

private fun insightLabel(
    type: InsightType
): String {

    return when (type) {

        InsightType.ANOMALY ->
            "SOMETHING CHANGED"

        InsightType.PATTERN ->
            "YOUR PATTERN"

        InsightType.PREDICTION ->
            "COMING UP"

        InsightType.WAITING ->
            "WAITING"

        InsightType.POSITIVE ->
            "NICE PATTERN"
    }
}


// ============================================================
// DEMO UI DATA
//
// UI ONLY.
// Later Dharshini's intelligence module can provide
// real InsightUi objects.
// ============================================================

private fun defaultInsights(): List<InsightUi> {

    return listOf(

        InsightUi(
            id = 1,
            title = "Electricity spending changed",
            description =
                "Your latest electricity bill is higher "
                        + "than your recent bills.",
            type = InsightType.ANOMALY,
            supportingText =
                "Recent bills were around ₹1,250–₹1,450. "
                        + "This one is noticeably higher."
        ),

        InsightUi(
            id = 2,
            title = "Your grocery cycle is around 6 days",
            description =
                "Your recent grocery activity appears "
                        + "to follow a repeating pattern.",
            type = InsightType.PATTERN,
            supportingText =
                "LIFE can use this pattern to give "
                        + "gentle reminders when appropriate."
        ),

        InsightUi(
            id = 3,
            title = "Gas may be needed soon",
            description =
                "Your usual refill pattern suggests "
                        + "you may need gas soon.",
            type = InsightType.PREDICTION,
            supportingText =
                "This is a prediction based on your "
                        + "previous activity — not a certainty."
        ),

        InsightUi(
            id = 4,
            title = "2 things are still pending",
            description =
                "You have items you're currently waiting "
                        + "for.",
            type = InsightType.WAITING,
            supportingText =
                "LIFE can keep them visible until they "
                        + "are resolved."
        ),

        InsightUi(
            id = 5,
            title = "Most recurring expenses are handled on time",
            description =
                "Your recent recurring payments appear "
                        + "to be completed consistently.",
            type = InsightType.POSITIVE,
            supportingText =
                "That's a useful pattern worth keeping."
        )
    )
}