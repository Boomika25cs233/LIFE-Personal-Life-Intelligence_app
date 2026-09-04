package com.life_personallifeintelligence.ui.home

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.life_personallifeintelligence.model.Memory
import java.util.Calendar

// ============================================================
// PREMIUM DARK PALETTE
// ============================================================

private val Background = Color(0xFF0B0910)
private val Surface = Color(0xFF14111A)
private val Surface2 = Color(0xFF1B1722)

private val White = Color.White
private val TextPrimary = Color(0xFFF8F5FA)
private val TextSecondary = Color(0xFFA9A1AF)
private val TextMuted = Color(0xFF716A78)

private val Purple = Color(0xFF8B5CF6)
private val Violet = Color(0xFFB56CFF)
private val Pink = Color(0xFFFF5C93)
private val Rose = Color(0xFFFF7BA8)

private val Orange = Color(0xFFFF9F43)
private val Yellow = Color(0xFFFFC857)
private val Green = Color(0xFF35D07F)
private val Cyan = Color(0xFF42D9FF)

private val SoftPurple = Color(0xFF211936)
private val SoftPink = Color(0xFF321725)
private val SoftOrange = Color(0xFF342316)
private val SoftGreen = Color(0xFF122A20)
private val SoftCyan = Color(0xFF102832)


// ============================================================
// HOME SCREEN
// ============================================================

@Composable
fun HomeScreen(
    memories: List<Memory> = emptyList(),

    onAddMemoryClick: () -> Unit = {},

    onMemoryDone: (Int, Boolean) -> Unit = { _, _ -> },

    onMemoryDelete: (Int) -> Unit = {},

    onMemoryEdit: (Memory) -> Unit = {},

    onInboxClick: () -> Unit = {},

    onDoneClick: () -> Unit = {},

    // --------------------------------------------------------
    // INTEGRATION-READY UI CALLBACKS
    // --------------------------------------------------------

    onInsightsClick: () -> Unit = {},

    onWaitingClick: () -> Unit = {},

    onResponsibilityClick: () -> Unit = {},

    onSettingsClick: () -> Unit = {}
) {

    val hour =
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    val greetingEmoji = when (hour) {
        in 5..11 -> "☀️"
        in 12..16 -> "🌤️"
        in 17..20 -> "🌆"
        else -> "🌙"
    }

    val urgentCount =
        memories.count {
            it.priority.equals("High", ignoreCase = true) &&
                    !it.isDone
        }

    val upcomingCount =
        memories.count {
            !it.isDone &&
                    !it.priority.equals(
                        "High",
                        ignoreCase = true
                    )
        }

    val doneCount =
        memories.count {
            it.isDone
        }

    val pendingMemories =
        memories.filter {
            !it.isDone
        }

    Scaffold(
        containerColor = Background,

        bottomBar = {
            PremiumBottomNavigation(
                onInboxClick = onInboxClick,
                onAddClick = onAddMemoryClick,
                onDoneClick = onDoneClick
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(paddingValues)
        ) {

            // =================================================
            // HERO
            // =================================================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF171022),
                                Color(0xFF211334),
                                Color(0xFF35184D),
                                Color(0xFF171022)
                            )
                        )
                    )
            ) {

                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .offset(
                            x = 270.dp,
                            y = (-60).dp
                        )
                        .clip(CircleShape)
                        .background(
                            Color(0x228B5CF6)
                        )
                )

                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .offset(
                            x = (-55).dp,
                            y = 170.dp
                        )
                        .clip(CircleShape)
                        .background(
                            Color(0x18FF5C93)
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 22.dp,
                            end = 22.dp,
                            top = 25.dp
                        )
                ) {

                    // -------------------------------------------------
                    // HEADER
                    // -------------------------------------------------

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment =
                            Alignment.CenterVertically,
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(
                                text =
                                    "$greeting $greetingEmoji",
                                fontSize = 29.sp,
                                fontFamily =
                                    FontFamily.Cursive,
                                color = White
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(
                                text =
                                    "Your life. Remembered intelligently.",
                                fontSize = 12.sp,
                                color =
                                    Color(0xFFBDB2C8)
                            )
                        }

                        // SETTINGS BUTTON

                        Surface(
                            modifier = Modifier
                                .size(47.dp)
                                .clickable {
                                    onSettingsClick()
                                },
                            shape =
                                RoundedCornerShape(16.dp),
                            color =
                                Color(0x18FFFFFF)
                        ) {

                            Box(
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text = "⚙",
                                    fontSize = 20.sp,
                                    color = White
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    // =================================================
                    // AI STATUS
                    // =================================================

                    Surface(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(24.dp),
                        color =
                            Color(0x1AFFFFFF)
                    ) {

                        Row(
                            modifier =
                                Modifier.padding(16.dp),
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(47.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Purple,
                                                Pink
                                            )
                                        )
                                    ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text = "✦",
                                    fontSize = 22.sp,
                                    color = White
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.width(13.dp)
                            )

                            Column(
                                modifier =
                                    Modifier.weight(1f)
                            ) {

                                Text(
                                    text =
                                        "LIFE Intelligence",
                                    fontSize = 14.sp,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color = White
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(3.dp)
                                )

                                Text(
                                    text =
                                        if (
                                            pendingMemories
                                                .isEmpty()
                                        ) {
                                            "Everything is under control ✨"
                                        } else {
                                            "$urgentCount urgent • " +
                                                    "${pendingMemories.size} active memories"
                                        },
                                    fontSize = 11.sp,
                                    color =
                                        Color(0xFFC8BECE)
                                )
                            }

                            Surface(
                                shape =
                                    RoundedCornerShape(50.dp),
                                color =
                                    Color(0x1835D07F)
                            ) {

                                Row(
                                    modifier =
                                        Modifier.padding(
                                            horizontal = 9.dp,
                                            vertical = 6.dp
                                        ),
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Green
                                            )
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.width(5.dp)
                                    )

                                    Text(
                                        text = "ACTIVE",
                                        fontSize = 8.sp,
                                        fontWeight =
                                            FontWeight.Bold,
                                        color = Green
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =================================================
            // TODAY CARD
            // =================================================

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .offset(y = (-25).dp),
                shape =
                    RoundedCornerShape(28.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor = Surface
                    ),
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(20.dp)
                ) {

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        verticalAlignment =
                            Alignment.CenterVertically,
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(
                                text = "TODAY",
                                fontSize = 10.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                letterSpacing = 2.sp,
                                color = Pink
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )

                            Text(
                                text = when {
                                    pendingMemories.isEmpty() ->
                                        "You're all caught up ✨"

                                    urgentCount > 0 ->
                                        "$urgentCount need your attention"

                                    else ->
                                        "You're doing great"
                                },
                                fontSize = 20.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Surface(
                            modifier =
                                Modifier.size(44.dp),
                            shape = CircleShape,
                            color = SoftPurple
                        ) {

                            Box(
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text = "✦",
                                    fontSize = 20.sp,
                                    color = Violet
                                )
                            }
                        }
                    }

                    if (
                        pendingMemories.isNotEmpty()
                    ) {

                        Spacer(
                            modifier =
                                Modifier.height(17.dp)
                        )

                        pendingMemories
                            .take(3)
                            .forEachIndexed {
                                    index,
                                    memory ->

                                TodayMemoryRow(
                                    memory = memory
                                )

                                if (
                                    index <
                                    minOf(
                                        pendingMemories.size,
                                        3
                                    ) - 1
                                ) {

                                    Spacer(
                                        modifier =
                                            Modifier.height(
                                                11.dp
                                            )
                                    )
                                }
                            }

                    } else {

                        Spacer(
                            modifier =
                                Modifier.height(14.dp)
                        )

                        Text(
                            text =
                                "Nothing urgent right now. Enjoy your day 💜",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            // =================================================
            // MAIN CONTENT
            // =================================================

            Column(
                modifier =
                    Modifier.padding(
                        horizontal = 20.dp
                    )
            ) {

                // =================================================
                // OVERVIEW
                // =================================================

                Text(
                    text = "Your overview",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    PremiumStatCard(
                        number =
                            urgentCount.toString(),
                        title = "Urgent",
                        icon = "🔥",
                        background = SoftOrange,
                        accent = Orange,
                        modifier =
                            Modifier.weight(1f)
                    )

                    PremiumStatCard(
                        number =
                            upcomingCount.toString(),
                        title = "Upcoming",
                        icon = "⏳",
                        background = SoftPurple,
                        accent = Violet,
                        modifier =
                            Modifier.weight(1f)
                    )

                    PremiumStatCard(
                        number =
                            doneCount.toString(),
                        title = "Completed",
                        icon = "✓",
                        background = SoftGreen,
                        accent = Green,
                        modifier =
                            Modifier.weight(1f)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(28.dp)
                )

                // =================================================
                // LIFE TOOLS
                // =================================================

                Text(
                    text = "LIFE tools",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(9.dp)
                ) {

                    ToolCard(
                        icon = "🧠",
                        title = "Insights",
                        subtitle = "Patterns",
                        background = SoftPurple,
                        accent = Violet,
                        modifier =
                            Modifier.weight(1f),
                        onClick =
                            onInsightsClick
                    )

                    ToolCard(
                        icon = "⏳",
                        title = "Waiting",
                        subtitle = "Track",
                        background = SoftCyan,
                        accent = Cyan,
                        modifier =
                            Modifier.weight(1f),
                        onClick =
                            onWaitingClick
                    )

                    ToolCard(
                        icon = "📋",
                        title = "Life",
                        subtitle = "Details",
                        background = SoftPink,
                        accent = Pink,
                        modifier =
                            Modifier.weight(1f),
                        onClick =
                            onResponsibilityClick
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(29.dp)
                )

                // =================================================
                // QUICK CAPTURE
                // =================================================

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Quick capture",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Text(
                        text = "Capture anything",
                        fontSize = 11.sp,
                        color = Violet
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(9.dp)
                ) {

                    CaptureCard(
                        icon = "✦",
                        title = "Memory",
                        subtitle = "Type it",
                        background = SoftPurple,
                        accent = Violet,
                        modifier =
                            Modifier.weight(1f),
                        onClick =
                            onAddMemoryClick
                    )

                    CaptureCard(
                        icon = "🎙",
                        title = "Voice",
                        subtitle = "Speak it",
                        background = SoftPink,
                        accent = Pink,
                        modifier =
                            Modifier.weight(1f)
                    )

                    CaptureCard(
                        icon = "📷",
                        title = "Photo",
                        subtitle = "Snap it",
                        background = SoftOrange,
                        accent = Orange,
                        modifier =
                            Modifier.weight(1f)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(29.dp)
                )

                // =================================================
                // MEMORIES
                // =================================================

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Column {

                        Text(
                            text = "Your memories",
                            fontSize = 21.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = TextPrimary
                        )

                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )

                        Text(
                            text =
                                "${memories.size} memories stored safely",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }

                    Surface(
                        shape =
                            RoundedCornerShape(12.dp),
                        color = SoftPurple
                    ) {

                        Text(
                            text = "LIFE ✦",
                            modifier =
                                Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 6.dp
                                ),
                            fontSize = 9.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Violet
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )

                if (memories.isEmpty()) {

                    EmptyMemoryCard(
                        onAddMemoryClick =
                            onAddMemoryClick
                    )

                } else {

                    memories.forEach { memory ->

                        PremiumMemoryCard(
                            memory = memory,

                            onEdit = {
                                onMemoryEdit(memory)
                            },

                            onDelete = {
                                onMemoryDelete(
                                    memory.id
                                )
                            },

                            onDone = {
                                onMemoryDone(
                                    memory.id,
                                    !memory.isDone
                                )
                            }
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
            }
        }
    }
}


// ============================================================
// TODAY MEMORY ROW
// ============================================================

@Composable
private fun TodayMemoryRow(
    memory: Memory
) {

    val icon =
        getMemoryIcon(memory)

    val background =
        when {
            memory.priority.equals(
                "High",
                true
            ) -> SoftOrange

            memory.priority.equals(
                "Low",
                true
            ) -> SoftGreen

            else -> SoftPurple
        }

    val accent =
        when {
            memory.priority.equals(
                "High",
                true
            ) -> Orange

            memory.priority.equals(
                "Low",
                true
            ) -> Green

            else -> Violet
        }

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Surface(
            modifier =
                Modifier.size(40.dp),
            shape =
                RoundedCornerShape(13.dp),
            color = background
        ) {

            Box(
                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = icon,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(
            modifier =
                Modifier.width(11.dp)
        )

        Column(
            modifier =
                Modifier.weight(1f)
        ) {

            Text(
                text = memory.text,
                fontSize = 14.sp,
                fontWeight =
                    FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text = memory.category,
                fontSize = 10.sp,
                color = TextMuted
            )
        }

        Surface(
            shape =
                RoundedCornerShape(9.dp),
            color = background
        ) {

            Text(
                text = memory.priority,
                modifier =
                    Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    ),
                fontSize = 8.sp,
                fontWeight =
                    FontWeight.Bold,
                color = accent
            )
        }
    }
}


// ============================================================
// MEMORY ICON
// ============================================================

private fun getMemoryIcon(
    memory: Memory
): String {

    val text =
        (
                memory.text +
                        " " +
                        memory.category
                ).lowercase()

    return when {

        "electric" in text ||
                "current" in text ||
                " eb" in text ||
                "power" in text ->
            "⚡"

        "milk" in text ->
            "🥛"

        "gas" in text ||
                "cylinder" in text ->
            "🔥"

        "emi" in text ||
                "loan" in text ||
                "payment" in text ||
                "bill" in text ->
            "💳"

        "medicine" in text ||
                "medical" in text ||
                "doctor" in text ||
                "hospital" in text ->
            "💊"

        "college" in text ||
                "assignment" in text ||
                "exam" in text ->
            "📚"

        "food" in text ||
                "lunch" in text ||
                "dinner" in text ->
            "🍽️"

        "birthday" in text ->
            "🎂"

        "wedding" in text ||
                "anniversary" in text ->
            "💍"

        "meeting" in text ->
            "📅"

        "call" in text ->
            "📞"

        "travel" in text ||
                "trip" in text ->
            "✈️"

        "shopping" in text ||
                "buy" in text ->
            "🛍️"

        "rent" in text ->
            "🏠"

        "water" in text ->
            "💧"

        else ->
            "✦"
    }
}


// ============================================================
// STAT CARD
// ============================================================

@Composable
private fun PremiumStatCard(
    number: String,
    title: String,
    icon: String,
    background: Color,
    accent: Color,
    modifier: Modifier
) {

    Card(
        modifier =
            modifier.height(118.dp),
        shape =
            RoundedCornerShape(22.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    background
            )
    ) {

        Column(
            modifier =
                Modifier.padding(14.dp)
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = icon,
                    fontSize = 18.sp
                )

                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(accent)
                )
            }

            Spacer(
                modifier =
                    Modifier.height(5.dp)
            )

            Text(
                text = number,
                fontSize = 27.sp,
                fontWeight =
                    FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = title,
                fontSize = 10.sp,
                color = TextSecondary
            )
        }
    }
}


// ============================================================
// LIFE TOOL CARD
// ============================================================

@Composable
private fun ToolCard(
    icon: String,
    title: String,
    subtitle: String,
    background: Color,
    accent: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .height(105.dp)
            .clickable {
                onClick()
            },
        shape =
            RoundedCornerShape(21.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    background
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(9.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {

            Surface(
                modifier =
                    Modifier.size(38.dp),
                shape =
                    CircleShape,
                color =
                    Color(0x22FFFFFF)
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 17.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight =
                    FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = subtitle,
                fontSize = 8.sp,
                color = TextSecondary
            )
        }
    }
}


// ============================================================
// CAPTURE CARD
// ============================================================

@Composable
private fun CaptureCard(
    icon: String,
    title: String,
    subtitle: String,
    background: Color,
    accent: Color,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .height(108.dp)
            .clickable {
                onClick()
            },
        shape =
            RoundedCornerShape(21.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    background
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 7.dp,
                        vertical = 10.dp
                    ),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {

            Surface(
                modifier =
                    Modifier.size(38.dp),
                shape =
                    CircleShape,
                color =
                    Color(0x22FFFFFF)
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 17.sp,
                        color = accent
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight =
                    FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = TextSecondary
            )
        }
    }
}


// ============================================================
// MEMORY CARD
// ============================================================

@Composable
private fun PremiumMemoryCard(
    memory: Memory,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDone: () -> Unit
) {

    val icon =
        getMemoryIcon(memory)

    val priorityColor =
        when {
            memory.priority.equals(
                "High",
                true
            ) -> Orange

            memory.priority.equals(
                "Low",
                true
            ) -> Green

            else -> Yellow
        }

    val priorityBackground =
        when {
            memory.priority.equals(
                "High",
                true
            ) -> SoftOrange

            memory.priority.equals(
                "Low",
                true
            ) -> SoftGreen

            else -> Color(0xFF302A19)
        }

    val cardBackground =
        if (memory.isDone) {
            Color(0xFF101B16)
        } else {
            Surface
        }

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(24.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    cardBackground
            )
    ) {

        Column(
            modifier =
                Modifier.padding(17.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.Top
            ) {

                Surface(
                    modifier =
                        Modifier.size(46.dp),
                    shape =
                        RoundedCornerShape(14.dp),
                    color =
                        if (memory.isDone)
                            SoftGreen
                        else
                            SoftPurple
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                if (memory.isDone)
                                    "✓"
                                else
                                    icon,
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text = memory.text,
                        fontSize = 16.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 2,
                        overflow =
                            TextOverflow.Ellipsis
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(7.dp)
                    ) {

                        Surface(
                            shape =
                                RoundedCornerShape(9.dp),
                            color =
                                SoftPurple
                        ) {

                            Text(
                                text =
                                    "🏷 ${memory.category}",
                                modifier =
                                    Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 5.dp
                                    ),
                                fontSize = 9.sp,
                                color =
                                    TextSecondary
                            )
                        }

                        Surface(
                            shape =
                                RoundedCornerShape(9.dp),
                            color =
                                priorityBackground
                        ) {

                            Text(
                                text =
                                    "● ${memory.priority}",
                                modifier =
                                    Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 5.dp
                                    ),
                                fontSize = 9.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    priorityColor
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(15.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(7.dp)
            ) {

                OutlinedButton(
                    onClick = onEdit,
                    modifier =
                        Modifier.weight(1f),
                    contentPadding =
                        PaddingValues(0.dp),
                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "✏️ Edit",
                        fontSize = 10.sp
                    )
                }

                OutlinedButton(
                    onClick = onDelete,
                    modifier =
                        Modifier.weight(1f),
                    contentPadding =
                        PaddingValues(0.dp),
                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "🗑 Delete",
                        fontSize = 10.sp,
                        color = Rose
                    )
                }

                Button(
                    onClick = onDone,
                    modifier =
                        Modifier.weight(1f),
                    contentPadding =
                        PaddingValues(0.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                if (memory.isDone)
                                    Green
                                else
                                    Purple
                        ),
                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text =
                            if (memory.isDone)
                                "✓ Done"
                            else
                                "Done",
                        fontSize = 10.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}


// ============================================================
// EMPTY MEMORY
// ============================================================

@Composable
private fun EmptyMemoryCard(
    onAddMemoryClick: () -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(26.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Surface
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(27.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier =
                    Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Purple,
                                    Pink
                                )
                            )
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = "✦",
                    fontSize = 28.sp,
                    color = White
                )
            }

            Spacer(
                modifier =
                    Modifier.height(13.dp)
            )

            Text(
                text =
                    "Your memory space is empty",
                fontSize = 16.sp,
                fontWeight =
                    FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(
                modifier =
                    Modifier.height(5.dp)
            )

            Text(
                text =
                    "Give LIFE something important to remember.",
                fontSize = 11.sp,
                color = TextSecondary
            )

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Button(
                onClick =
                    onAddMemoryClick,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Purple
                    ),
                shape =
                    RoundedCornerShape(14.dp)
            ) {

                Text(
                    text =
                        "＋ Add your first memory",
                    fontSize = 11.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}


// ============================================================
// BOTTOM NAVIGATION
// ============================================================

@Composable
private fun PremiumBottomNavigation(
    onInboxClick: () -> Unit,
    onAddClick: () -> Unit,
    onDoneClick: () -> Unit
) {

    NavigationBar(
        containerColor =
            Color(0xFF100D15),
        tonalElevation = 12.dp
    ) {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {

                Text(
                    text = "⌂",
                    fontSize = 23.sp,
                    color = Violet
                )
            },
            label = {

                Text(
                    text = "Home",
                    fontSize = 9.sp
                )
            },
            colors =
                NavigationBarItemDefaults.colors(
                    selectedIconColor = Violet,
                    selectedTextColor = Violet,
                    unselectedIconColor =
                        TextMuted,
                    unselectedTextColor =
                        TextMuted,
                    indicatorColor =
                        SoftPurple
                )
        )

        NavigationBarItem(
            selected = false,
            onClick = onInboxClick,
            icon = {

                Text(
                    text = "▣",
                    fontSize = 20.sp
                )
            },
            label = {

                Text(
                    text = "Inbox",
                    fontSize = 9.sp
                )
            },
            colors =
                NavigationBarItemDefaults.colors(
                    unselectedIconColor =
                        TextMuted,
                    unselectedTextColor =
                        TextMuted
                )
        )

        NavigationBarItem(
            selected = false,
            onClick = onAddClick,
            icon = {

                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Purple,
                                        Pink
                                    )
                                )
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = "+",
                        fontSize = 26.sp,
                        color = White
                    )
                }
            },
            label = {

                Text(
                    text = "Add",
                    fontSize = 9.sp
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onDoneClick,
            icon = {

                Text(
                    text = "✓",
                    fontSize = 21.sp
                )
            },
            label = {

                Text(
                    text = "Done",
                    fontSize = 9.sp
                )
            },
            colors =
                NavigationBarItemDefaults.colors(
                    unselectedIconColor =
                        TextMuted,
                    unselectedTextColor =
                        TextMuted
                )
        )
    }
}