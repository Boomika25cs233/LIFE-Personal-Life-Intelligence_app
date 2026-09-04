package com.life_personallifeintelligence.ui.responsibility

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.life_personallifeintelligence.model.Memory

// ============================================================
// PREMIUM DARK UI COLORS
// ============================================================

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val CardDark2 = Color(0xFF20192A)

private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)

private val Green = Color(0xFF55D98A)
private val Blue = Color(0xFF62A8FF)
private val Yellow = Color(0xFFFFC857)
private val Orange = Color(0xFFFF945E)
private val Red = Color(0xFFFF647C)

private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val SoftPurple = Color(0xFF2A2140)
private val SoftGreen = Color(0xFF1D382B)
private val SoftBlue = Color(0xFF1B2D43)
private val SoftYellow = Color(0xFF3A301B)
private val SoftOrange = Color(0xFF3A271E)
private val SoftRed = Color(0xFF3A202A)


// ============================================================
// RESPONSIBILITY STATE
// UI ONLY
//
// Later Josilda can provide the actual state.
// For now the default visual state is UPCOMING.
// ============================================================

enum class ResponsibilityUiState {
    DETECTED,
    VERIFIED,
    UPCOMING,
    DUE,
    WAITING,
    OVERDUE,
    COMPLETED
}


// ============================================================
// MAIN SCREEN
// ============================================================

@Composable
fun ResponsibilityDetailScreen(
    memory: Memory,
    onBackClick: () -> Unit = {},
    onComplete: () -> Unit = {},
    onTransfer: () -> Unit = {},
    onWhyClick: () -> Unit = {},
    onEvidenceClick: () -> Unit = {},

    // UI integration point.
    // Josilda can connect the real state later.
    responsibilityState: ResponsibilityUiState =
        if (memory.isDone) {
            ResponsibilityUiState.COMPLETED
        } else {
            ResponsibilityUiState.UPCOMING
        }
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // =====================================================
        // HERO HEADER
        // =====================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF24163B),
                            Color(0xFF48275F),
                            Color(0xFF100D18)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                // BACK

                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            onBackClick()
                        },
                    shape = CircleShape,
                    color = Color(0x25FFFFFF)
                ) {

                    Text(
                        text = "‹",
                        color = White,
                        fontSize = 31.sp,
                        modifier = Modifier.padding(
                            start = 12.dp,
                            bottom = 2.dp
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                // TITLE

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0x30FFFFFF)
                    ) {

                        Text(
                            text = categoryIcon(memory.category),
                            fontSize = 29.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = memory.category.uppercase(),
                            color = Color(0xFFD5C8E6),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = memory.text,
                            color = White,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // CURRENT STATE BADGE

                StateBadge(
                    state = responsibilityState
                )
            }
        }


        // =====================================================
        // BODY
        // =====================================================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            // =================================================
            // RESPONSIBILITY STATE
            // =================================================

            Text(
                text = "LIFE Status",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Where this responsibility currently stands",
                color = TextGray,
                fontSize = 11.sp
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            StateProgressCard(
                currentState = responsibilityState
            )


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // ABOUT
            // =================================================

            SectionTitle(
                "About this",
                "Current information"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DetailCard {

                DetailRow(
                    icon = categoryIcon(memory.category),
                    label = "Category",
                    value = memory.category
                )

                DividerLine()

                DetailRow(
                    icon = "⚡",
                    label = "Priority",
                    value = memory.priority
                )

                DividerLine()

                DetailRow(
                    icon = "👤",
                    label = "Owner",
                    value = "You"
                )
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // WHY LIFE
            // =================================================

            SectionTitle(
                "Why LIFE showed this",
                "Understand the reason"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PremiumActionCard(
                icon = "🧠",
                title = "LIFE Intelligence",
                description =
                    "See why LIFE thinks this may be relevant.",
                color = SoftPurple,
                accent = Purple,
                onClick = onWhyClick
            )


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // EVIDENCE
            // =================================================

            SectionTitle(
                "Evidence",
                "Information connected to this item"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PremiumActionCard(
                icon = "🔗",
                title = "Related activity",
                description =
                    "View information connected to this responsibility.",
                color = SoftBlue,
                accent = Blue,
                onClick = onEvidenceClick
            )


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // WHAT HAPPENS NEXT
            // =================================================

            SectionTitle(
                "What happens next",
                "LIFE keeps this in context"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark
                )
            ) {

                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.Top
                ) {

                    Text(
                        text = "✦",
                        color = Purple,
                        fontSize = 22.sp
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Text(
                        text = nextStateDescription(
                            responsibilityState
                        ),
                        color = TextGray,
                        fontSize = 12.sp,
                        lineHeight = 19.sp
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // =================================================
            // ACTIONS
            // =================================================

            if (
                responsibilityState !=
                ResponsibilityUiState.COMPLETED
            ) {

                Button(
                    onClick = onComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple
                    )
                ) {

                    Text(
                        text = "✓  Mark as completed",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                OutlinedButton(
                    onClick = onTransfer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextGray
                    )
                ) {

                    Text(
                        text = "⇄  Transfer responsibility",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            } else {

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(17.dp),
                    color = SoftGreen
                ) {

                    Row(
                        modifier = Modifier.padding(17.dp),
                        horizontalArrangement =
                            Arrangement.Center,
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = "✓",
                            color = Green,
                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "Responsibility completed",
                            color = Green,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


// ============================================================
// STATE PROGRESS CARD
// ============================================================

@Composable
private fun StateProgressCard(
    currentState: ResponsibilityUiState
) {

    val states = listOf(
        ResponsibilityUiState.DETECTED,
        ResponsibilityUiState.VERIFIED,
        ResponsibilityUiState.UPCOMING,
        ResponsibilityUiState.DUE,
        ResponsibilityUiState.WAITING,
        ResponsibilityUiState.OVERDUE,
        ResponsibilityUiState.COMPLETED
    )

    val currentIndex = states.indexOf(currentState)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = stateTitle(currentState),
                color = stateColor(currentState),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = stateDescription(currentState),
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            states.forEachIndexed { index, state ->

                StateTimelineItem(
                    state = state,
                    isCurrent = index == currentIndex,
                    isCompleted = index < currentIndex,
                    isLast = index == states.lastIndex
                )
            }
        }
    }
}


// ============================================================
// STATE TIMELINE ITEM
// ============================================================

@Composable
private fun StateTimelineItem(
    state: ResponsibilityUiState,
    isCurrent: Boolean,
    isCompleted: Boolean,
    isLast: Boolean
) {

    Row(
        verticalAlignment = Alignment.Top
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier.size(
                    if (isCurrent) 38.dp else 30.dp
                ),
                shape = CircleShape,
                color = when {
                    isCurrent -> stateSoftColor(state)
                    isCompleted -> SoftGreen
                    else -> CardDark2
                }
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = when {
                            isCompleted -> "✓"
                            else -> stateIcon(state)
                        },
                        color = when {
                            isCurrent ->
                                stateColor(state)

                            isCompleted ->
                                Green

                            else ->
                                TextGray
                        },
                        fontSize =
                            if (isCurrent) 15.sp
                            else 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (!isLast) {

                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(25.dp)
                        .background(
                            if (isCompleted) {
                                Green.copy(alpha = 0.5f)
                            } else {
                                Color(0xFF332C3B)
                            }
                        )
                )
            }
        }

        Spacer(
            modifier = Modifier.width(13.dp)
        )

        Column(
            modifier = Modifier.padding(
                top = if (isCurrent) 8.dp else 5.dp
            )
        ) {

            Text(
                text = stateDisplayName(state),
                color = when {
                    isCurrent ->
                        stateColor(state)

                    isCompleted ->
                        Green

                    else ->
                        TextGray
                },
                fontSize = 12.sp,
                fontWeight =
                    if (isCurrent || isCompleted) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
            )

            if (isCurrent) {

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "Current stage",
                    color = TextGray,
                    fontSize = 10.sp
                )
            }
        }
    }
}


// ============================================================
// STATE BADGE
// ============================================================

@Composable
private fun StateBadge(
    state: ResponsibilityUiState
) {

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = stateSoftColor(state)
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 13.dp,
                vertical = 7.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stateIcon(state),
                color = stateColor(state),
                fontSize = 11.sp
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = stateDisplayName(state).uppercase(),
                color = stateColor(state),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
        }
    }
}


// ============================================================
// PREMIUM ACTION CARD
// ============================================================

@Composable
private fun PremiumActionCard(
    icon: String,
    title: String,
    description: String,
    color: Color,
    accent: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(47.dp),
                shape = RoundedCornerShape(15.dp),
                color = Color(0x35FFFFFF)
            ) {

                Text(
                    text = icon,
                    fontSize = 21.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(13.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = description,
                    color = TextGray,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }

            Text(
                text = "›",
                color = accent,
                fontSize = 27.sp
            )
        }
    }
}


// ============================================================
// SECTION TITLE
// ============================================================

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String
) {

    Column {

        Text(
            text = title,
            color = White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = subtitle,
            color = TextGray,
            fontSize = 11.sp
        )
    }
}


// ============================================================
// DETAIL CARD
// ============================================================

@Composable
private fun DetailCard(
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {

        Column(
            modifier = Modifier.padding(17.dp),
            content = content
        )
    }
}


// ============================================================
// DETAIL ROW
// ============================================================

@Composable
private fun DetailRow(
    icon: String,
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier.size(38.dp),
            shape = RoundedCornerShape(12.dp),
            color = SoftPurple
        ) {

            Text(
                text = icon,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = label,
            color = TextGray,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// ============================================================
// DIVIDER
// ============================================================

@Composable
private fun DividerLine() {

    HorizontalDivider(
        modifier = Modifier.padding(
            vertical = 13.dp
        ),
        color = Color(0xFF2B2633)
    )
}


// ============================================================
// STATE HELPERS
// ============================================================

private fun stateDisplayName(
    state: ResponsibilityUiState
): String {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            "Detected"

        ResponsibilityUiState.VERIFIED ->
            "Verified"

        ResponsibilityUiState.UPCOMING ->
            "Upcoming"

        ResponsibilityUiState.DUE ->
            "Due"

        ResponsibilityUiState.WAITING ->
            "Waiting"

        ResponsibilityUiState.OVERDUE ->
            "Overdue"

        ResponsibilityUiState.COMPLETED ->
            "Completed"
    }
}


private fun stateIcon(
    state: ResponsibilityUiState
): String {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            "✦"

        ResponsibilityUiState.VERIFIED ->
            "✓"

        ResponsibilityUiState.UPCOMING ->
            "◷"

        ResponsibilityUiState.DUE ->
            "!"

        ResponsibilityUiState.WAITING ->
            "⌛"

        ResponsibilityUiState.OVERDUE ->
            "!"

        ResponsibilityUiState.COMPLETED ->
            "✓"
    }
}


private fun stateColor(
    state: ResponsibilityUiState
): Color {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            Purple

        ResponsibilityUiState.VERIFIED ->
            Blue

        ResponsibilityUiState.UPCOMING ->
            Yellow

        ResponsibilityUiState.DUE ->
            Orange

        ResponsibilityUiState.WAITING ->
            Purple

        ResponsibilityUiState.OVERDUE ->
            Red

        ResponsibilityUiState.COMPLETED ->
            Green
    }
}


private fun stateSoftColor(
    state: ResponsibilityUiState
): Color {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            SoftPurple

        ResponsibilityUiState.VERIFIED ->
            SoftBlue

        ResponsibilityUiState.UPCOMING ->
            SoftYellow

        ResponsibilityUiState.DUE ->
            SoftOrange

        ResponsibilityUiState.WAITING ->
            SoftPurple

        ResponsibilityUiState.OVERDUE ->
            SoftRed

        ResponsibilityUiState.COMPLETED ->
            SoftGreen
    }
}


private fun stateTitle(
    state: ResponsibilityUiState
): String {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            "LIFE detected something"

        ResponsibilityUiState.VERIFIED ->
            "Responsibility verified"

        ResponsibilityUiState.UPCOMING ->
            "Coming up"

        ResponsibilityUiState.DUE ->
            "Needs attention"

        ResponsibilityUiState.WAITING ->
            "Waiting for an update"

        ResponsibilityUiState.OVERDUE ->
            "This needs attention"

        ResponsibilityUiState.COMPLETED ->
            "Completed successfully"
    }
}


private fun stateDescription(
    state: ResponsibilityUiState
): String {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            "LIFE has identified information that may become a responsibility."

        ResponsibilityUiState.VERIFIED ->
            "This responsibility has been confirmed."

        ResponsibilityUiState.UPCOMING ->
            "This responsibility is approaching but does not need immediate action."

        ResponsibilityUiState.DUE ->
            "This responsibility may need your attention now."

        ResponsibilityUiState.WAITING ->
            "LIFE is waiting for the expected next event."

        ResponsibilityUiState.OVERDUE ->
            "The expected action or update has not happened yet."

        ResponsibilityUiState.COMPLETED ->
            "This responsibility has been completed."
    }
}


private fun nextStateDescription(
    state: ResponsibilityUiState
): String {

    return when (state) {

        ResponsibilityUiState.DETECTED ->
            "LIFE can continue evaluating this item until it becomes relevant enough to act on."

        ResponsibilityUiState.VERIFIED ->
            "LIFE will keep this responsibility available for future state updates."

        ResponsibilityUiState.UPCOMING ->
            "As the due date approaches, LIFE can move this responsibility toward Due."

        ResponsibilityUiState.DUE ->
            "LIFE can keep this visible until the responsibility is completed or its state changes."

        ResponsibilityUiState.WAITING ->
            "LIFE can continue watching for the expected event or response."

        ResponsibilityUiState.OVERDUE ->
            "LIFE can keep this visible and help you decide what to do next."

        ResponsibilityUiState.COMPLETED ->
            "This responsibility is finished and can remain in your history."
    }
}


// ============================================================
// CATEGORY ICON
// ============================================================

private fun categoryIcon(
    category: String
): String {

    return when {

        category.contains(
            "electric",
            ignoreCase = true
        ) ||
                category.contains(
                    "bill",
                    ignoreCase = true
                ) ->
            "💡"

        category.contains(
            "milk",
            ignoreCase = true
        ) ->
            "🥛"

        category.contains(
            "gas",
            ignoreCase = true
        ) ->
            "🔥"

        category.contains(
            "medical",
            ignoreCase = true
        ) ||
                category.contains(
                    "medicine",
                    ignoreCase = true
                ) ->
            "💊"

        category.contains(
            "college",
            ignoreCase = true
        ) ||
                category.contains(
                    "education",
                    ignoreCase = true
                ) ->
            "🎓"

        category.contains(
            "loan",
            ignoreCase = true
        ) ||
                category.contains(
                    "emi",
                    ignoreCase = true
                ) ->
            "💰"

        category.contains(
            "shopping",
            ignoreCase = true
        ) ->
            "🛍️"

        category.contains(
            "food",
            ignoreCase = true
        ) ||
                category.contains(
                    "grocery",
                    ignoreCase = true
                ) ->
            "🛒"

        category.contains(
            "travel",
            ignoreCase = true
        ) ->
            "✈️"

        category.contains(
            "work",
            ignoreCase = true
        ) ->
            "💼"

        category.contains(
            "family",
            ignoreCase = true
        ) ->
            "👨‍👩‍👧"

        category.contains(
            "event",
            ignoreCase = true
        ) ->
            "📅"

        else ->
            "✦"
    }
}