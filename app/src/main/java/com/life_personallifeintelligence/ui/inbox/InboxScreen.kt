package com.life_personallifeintelligence.ui.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.life_personallifeintelligence.model.Memory

// ============================================================
// PREMIUM DARK THEME
// ============================================================

private val Background = Color(0xFF09070E)
private val SurfaceDark = Color(0xFF12101A)
private val CardDark = Color(0xFF17141F)
private val CardDark2 = Color(0xFF1D1928)

private val White = Color(0xFFFDFBFF)
private val TextSecondary = Color(0xFFAAA4B5)
private val TextMuted = Color(0xFF777180)

private val Purple = Color(0xFF9B6CFF)
private val PurpleBright = Color(0xFFB48AFF)
private val Pink = Color(0xFFFF5F9E)
private val Coral = Color(0xFFFF806E)
private val Blue = Color(0xFF5FA8FF)
private val Green = Color(0xFF55D98A)
private val Amber = Color(0xFFFFC857)

private val PurpleGlow = Color(0x332F1C5C)
private val PinkGlow = Color(0x332D1324)
private val GreenGlow = Color(0x33205032)

// ============================================================
// INBOX SCREEN
// ============================================================

@Composable
fun InboxScreen(
    memories: List<Memory> = emptyList(),

    // Navigation
    onBackClick: () -> Unit = {},
    onAddMemory: () -> Unit = {},

    // Integration-ready actions
    onSaveMemory: (Memory) -> Unit = {},
    onIgnoreMemory: (Memory) -> Unit = {},
    onLaterMemory: (Memory) -> Unit = {},
    onWhyClick: (Memory) -> Unit = {},
    onMemoryClick: (Memory) -> Unit = {}
) {

    val pendingMemories = memories.filter { !it.isDone }

    Scaffold(
        containerColor = Background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(paddingValues)
                .navigationBarsPadding(),

            contentPadding = PaddingValues(
                bottom = 35.dp
            ),

            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // ====================================================
            // PREMIUM HEADER
            // ====================================================

            item {

                PremiumInboxHeader(
                    pendingCount = pendingMemories.size,
                    onBackClick = onBackClick
                )
            }

            // ====================================================
            // INTRO
            // ====================================================

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 24.dp
                        )
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Recently noticed",
                                color = White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Text(
                                text = "LIFE found these from your everyday activity.",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                maxLines = 2
                            )
                        }

                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = PurpleGlow
                        ) {

                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = PurpleBright,
                                modifier = Modifier.padding(11.dp)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )
                }
            }

            // ====================================================
            // EMPTY STATE
            // ====================================================

            if (pendingMemories.isEmpty()) {

                item {

                    EmptyInboxPremium(
                        onAddMemory = onAddMemory
                    )
                }

            } else {

                // =================================================
                // MEMORY CARDS
                // =================================================

                items(
                    items = pendingMemories,
                    key = { it.id }
                ) { memory ->

                    PremiumInboxMemoryCard(
                        memory = memory,

                        onSave = {
                            onSaveMemory(memory)
                        },

                        onIgnore = {
                            onIgnoreMemory(memory)
                        },

                        onLater = {
                            onLaterMemory(memory)
                        },

                        onWhy = {
                            onWhyClick(memory)
                        },

                        onClick = {
                            onMemoryClick(memory)
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )
                }
            }
        }
    }
}

// ============================================================
// PREMIUM HEADER
// ============================================================

@Composable
private fun PremiumInboxHeader(
    pendingCount: Int,
    onBackClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(245.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF211238),
                        Color(0xFF342052),
                        Color(0xFF130E1D)
                    )
                )
            )
    ) {

        // Glow 1

        Box(
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.TopEnd)
                .offsetGlow()
                .clip(CircleShape)
                .background(
                    Color(0x249B6CFF)
                )
        )

        // Glow 2

        Box(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.BottomStart)
                .clip(CircleShape)
                .background(
                    Color(0x18FF5F9E)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp,
                    vertical = 22.dp
                )
        ) {

            // TOP ROW

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(44.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = Color(0x20FFFFFF)
                ) {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = White
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
                        text = "Inbox",
                        color = White,
                        fontSize = 31.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "LIFE noticed something",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Surface(
                    modifier = Modifier.size(44.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = Color(0x20FFFFFF)
                ) {

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // AI STATUS CARD

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(23.dp),
                color = Color(0x22FFFFFF)
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(47.dp),
                        shape = CircleShape,
                        color = PurpleGlow
                    ) {

                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PurpleBright,
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
                            text = "LIFE Intelligence",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = if (pendingCount == 0) {
                                "You're completely caught up ✨"
                            } else {
                                "$pendingCount item${if (pendingCount == 1) "" else "s"} waiting for review"
                            },
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = Color(0x1F55D98A)
                    ) {

                        Text(
                            text = "ACTIVE",
                            color = Green,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = 9.dp,
                                vertical = 6.dp
                            )
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// PREMIUM MEMORY CARD
// ============================================================

@Composable
private fun PremiumInboxMemoryCard(
    memory: Memory,
    onSave: () -> Unit,
    onIgnore: () -> Unit,
    onLater: () -> Unit,
    onWhy: () -> Unit,
    onClick: () -> Unit
) {

    val categoryIcon = getCategoryIcon(
        memory.category
    )

    val accent = getCategoryAccent(
        memory.category
    )

    val priorityColor = when (memory.priority) {

        "High" -> Coral
        "Low" -> Green
        else -> Amber
    }

    val priorityBackground = when (memory.priority) {

        "High" -> Color(0x221F1018)
        "Low" -> GreenGlow
        else -> Color(0x221F1A08)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(27.dp),

        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
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

                // CATEGORY ICON

                Surface(
                    modifier = Modifier.size(53.dp),
                    shape = RoundedCornerShape(17.dp),
                    color = Color(
                        red = accent.red,
                        green = accent.green,
                        blue = accent.blue,
                        alpha = 0.13f
                    )
                ) {

                    Text(
                        text = categoryIcon,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(13.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(13.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = memory.text,
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "LIFE noticed this from your activity",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // =================================================
            // TAGS
            // =================================================

            Row(
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                PremiumTag(
                    text = "🏷 ${memory.category}",
                    background = PurpleGlow,
                    textColor = PurpleBright
                )

                PremiumTag(
                    text = "● ${memory.priority}",
                    background = priorityBackground,
                    textColor = priorityColor
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // =================================================
            // WHY LIFE
            // =================================================

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onWhy()
                    },

                shape = RoundedCornerShape(16.dp),

                color = Color(0xFF100D16)
            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = CircleShape,
                        color = PurpleGlow
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = PurpleBright,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Why am I seeing this?",
                            color = White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "View LIFE's reasoning and evidence",
                            color = TextMuted,
                            fontSize = 9.sp
                        )
                    }

                    Text(
                        text = "›",
                        color = PurpleBright,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            // =================================================
            // ACTIONS
            // =================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                // IGNORE

                OutlinedButton(
                    onClick = onIgnore,

                    modifier = Modifier.weight(0.8f),

                    contentPadding = PaddingValues(
                        horizontal = 4.dp
                    ),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextSecondary
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(3.dp)
                    )

                    Text(
                        text = "Ignore",
                        fontSize = 10.sp
                    )
                }

                // LATER

                OutlinedButton(
                    onClick = onLater,

                    modifier = Modifier.weight(0.8f),

                    contentPadding = PaddingValues(
                        horizontal = 4.dp
                    ),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Blue
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(3.dp)
                    )

                    Text(
                        text = "Later",
                        fontSize = 10.sp
                    )
                }

                // SAVE

                Button(
                    onClick = onSave,

                    modifier = Modifier.weight(1.15f),

                    contentPadding = PaddingValues(
                        horizontal = 4.dp
                    ),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(3.dp)
                    )

                    Text(
                        text = "Save",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ============================================================
// PREMIUM TAG
// ============================================================

@Composable
private fun PremiumTag(
    text: String,
    background: Color,
    textColor: Color
) {

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = background
    ) {

        Text(
            text = text,
            color = textColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                horizontal = 9.dp,
                vertical = 6.dp
            )
        )
    }
}

// ============================================================
// EMPTY STATE
// ============================================================

@Composable
private fun EmptyInboxPremium(
    onAddMemory: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier.size(76.dp),
                shape = CircleShape,
                color = PurpleGlow
            ) {

                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = PurpleBright,
                    modifier = Modifier.padding(22.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Your inbox is clear",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "LIFE hasn't noticed anything new yet.",
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(19.dp)
            )

            Button(
                onClick = onAddMemory,

                shape = RoundedCornerShape(15.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple
                )
            ) {

                Text(
                    text = "＋ Add Memory",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ============================================================
// CATEGORY ICON
// ============================================================

private fun getCategoryIcon(
    category: String
): String {

    val value = category.lowercase()

    return when {

        value.contains("electric") ||
                value.contains("bill") ->
            "💡"

        value.contains("milk") ->
            "🥛"

        value.contains("gas") ->
            "🔥"

        value.contains("medical") ||
                value.contains("medicine") ||
                value.contains("hospital") ||
                value.contains("doctor") ->
            "💊"

        value.contains("college") ||
                value.contains("fee") ->
            "🎓"

        value.contains("loan") ||
                value.contains("emi") ->
            "💰"

        value.contains("insurance") ->
            "🛡️"

        value.contains("shopping") ||
                value.contains("grocery") ->
            "🛍️"

        value.contains("rent") ->
            "🏠"

        value.contains("travel") ->
            "✈️"

        value.contains("food") ||
                value.contains("restaurant") ->
            "🍽️"

        value.contains("vehicle") ||
                value.contains("car") ||
                value.contains("bike") ->
            "🚗"

        value.contains("event") ||
                value.contains("wedding") ||
                value.contains("birthday") ->
            "🎉"

        value.contains("subscription") ->
            "🔁"

        value.contains("payment") ->
            "💳"

        else ->
            "✦"
    }
}

// ============================================================
// CATEGORY ACCENT
// ============================================================

private fun getCategoryAccent(
    category: String
): Color {

    val value = category.lowercase()

    return when {

        value.contains("electric") ||
                value.contains("bill") ->
            Amber

        value.contains("milk") ->
            Blue

        value.contains("gas") ->
            Coral

        value.contains("medical") ||
                value.contains("medicine") ||
                value.contains("hospital") ||
                value.contains("doctor") ->
            Pink

        value.contains("college") ||
                value.contains("fee") ->
            PurpleBright

        value.contains("loan") ||
                value.contains("emi") ->
            Green

        value.contains("insurance") ->
            Blue

        value.contains("shopping") ||
                value.contains("grocery") ->
            Pink

        value.contains("rent") ->
            Coral

        value.contains("travel") ->
            Blue

        value.contains("food") ||
                value.contains("restaurant") ->
            Coral

        value.contains("vehicle") ||
                value.contains("car") ||
                value.contains("bike") ->
            PurpleBright

        value.contains("event") ||
                value.contains("wedding") ||
                value.contains("birthday") ->
            Pink

        else ->
            PurpleBright
    }
}

// ============================================================
// SMALL MODIFIER HELPER
// ============================================================

private fun Modifier.offsetGlow(): Modifier {
    return this.padding(
        top = 10.dp,
        end = 10.dp
    )
}