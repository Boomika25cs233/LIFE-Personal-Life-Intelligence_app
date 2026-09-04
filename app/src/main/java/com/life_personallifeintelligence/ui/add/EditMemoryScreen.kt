package com.life_personallifeintelligence.ui.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.life_personallifeintelligence.model.Memory

// ============================================================
// PREMIUM DARK COLORS
// ============================================================

private val Background = Color(0xFF09070F)
private val SurfaceDark = Color(0xFF12101C)
private val CardDark = Color(0xFF181522)
private val CardDark2 = Color(0xFF211B32)

private val Purple = Color(0xFF8B5CFF)
private val PurpleBright = Color(0xFFA875FF)
private val Pink = Color(0xFFFF5FA2)
private val Cyan = Color(0xFF5DE7FF)

private val White = Color(0xFFF9F7FF)
private val TextSecondary = Color(0xFFA8A2B3)
private val TextMuted = Color(0xFF777184)

private val HighRed = Color(0xFFFF667A)
private val NormalPurple = Color(0xFF9B70FF)
private val LowGreen = Color(0xFF57D69A)


// ============================================================
// EDIT MEMORY SCREEN
// ============================================================

@Composable
fun EditMemoryScreen(
    memory: Memory,
    onSave: (String, String, String) -> Unit,
    onCancel: () -> Unit = {}
) {

    var memoryText by remember(memory.text) {
        mutableStateOf(memory.text)
    }

    var selectedCategory by remember(memory.category) {
        mutableStateOf(memory.category)
    }

    var selectedPriority by remember(memory.priority) {
        mutableStateOf(memory.priority)
    }


    val canSave = memoryText.trim().isNotEmpty()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        // ====================================================
        // BACKGROUND GLOW
        // ====================================================

        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(
                    x = 180.dp,
                    y = (-90).dp
                )
                .clip(CircleShape)
                .background(
                    Color(0x332D145E)
                )
        )

        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(
                    x = (-100).dp,
                    y = 500.dp
                )
                .clip(CircleShape)
                .background(
                    Color(0x221B4D63)
                )
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 20.dp
                )
        ) {

            Spacer(
                modifier = Modifier.height(22.dp)
            )


            // =================================================
            // HEADER
            // =================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Color(0xFF211A35)
                        )
                        .clickable {
                            onCancel()
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "‹",
                        fontSize = 32.sp,
                        color = White
                    )
                }


                Spacer(
                    modifier = Modifier.width(14.dp)
                )


                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Edit memory",
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )

                    Text(
                        text = "Update your memory for LIFE.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }


                // AI sparkle

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Purple,
                                    Pink
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "✦",
                        fontSize = 22.sp,
                        color = White
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // =================================================
            // HERO CARD
            // =================================================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .clip(
                        RoundedCornerShape(28.dp)
                    )
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF24183E),
                                Color(0xFF171329),
                                Color(0xFF24162E)
                            )
                        )
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "✦  EDIT MEMORY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = PurpleBright
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = if (memoryText.isEmpty()) {
                            "What's important today?"
                        } else {
                            memoryText
                        },
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = if (memoryText.isEmpty()) {
                            TextMuted
                        } else {
                            White
                        },
                        maxLines = 2
                    )

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = "LIFE will keep it safe for you.",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // MEMORY INPUT
            // =================================================

            Text(
                text = "What should LIFE remember?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )


            OutlinedTextField(
                value = memoryText,
                onValueChange = {
                    memoryText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp),
                placeholder = {
                    Text(
                        text = "e.g. Pay electricity bill on Wednesday",
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    color = White,
                    fontSize = 15.sp
                ),
                shape = RoundedCornerShape(22.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardDark,
                    unfocusedContainerColor = CardDark,

                    focusedBorderColor = Purple,
                    unfocusedBorderColor = Color(0xFF302A3D),

                    cursorColor = PurpleBright,

                    focusedTextColor = White,
                    unfocusedTextColor = White
                )
            )


            Spacer(
                modifier = Modifier.height(27.dp)
            )


            // =================================================
            // CATEGORY
            // =================================================

            Text(
                text = "Category",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )


            val categories = listOf(
                "General" to "✦",
                "Bill" to "💡",
                "Medical" to "💊",
                "Finance" to "💳",
                "Shopping" to "🛍️",
                "Family" to "❤️"
            )


            Column(
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {

                categories
                    .chunked(3)
                    .forEach { rowItems ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(9.dp)
                        ) {

                            rowItems.forEach { item ->

                                PremiumChoiceChip(
                                    icon = item.second,
                                    text = item.first,
                                    selected = selectedCategory == item.first,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        selectedCategory = item.first
                                    }
                                )
                            }
                        }
                    }
            }


            Spacer(
                modifier = Modifier.height(27.dp)
            )


            // =================================================
            // PRIORITY
            // =================================================

            Text(
                text = "Priority",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                PremiumPriorityChip(
                    title = "High",
                    emoji = "🔥",
                    color = HighRed,
                    selected = selectedPriority == "High",
                    modifier = Modifier.weight(1f)
                ) {
                    selectedPriority = "High"
                }


                PremiumPriorityChip(
                    title = "Normal",
                    emoji = "✦",
                    color = NormalPurple,
                    selected = selectedPriority == "Normal",
                    modifier = Modifier.weight(1f)
                ) {
                    selectedPriority = "Normal"
                }


                PremiumPriorityChip(
                    title = "Low",
                    emoji = "🌿",
                    color = LowGreen,
                    selected = selectedPriority == "Low",
                    modifier = Modifier.weight(1f)
                ) {
                    selectedPriority = "Low"
                }
            }


            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // =================================================
            // SMART AI INFO
            // =================================================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(
                        Color(0xFF151120)
                    )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0x332B1C58)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "✦",
                            fontSize = 19.sp,
                            color = PurpleBright
                        )
                    }


                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )


                    Column {

                        Text(
                            text = "LIFE Intelligence",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = "We'll help you remember this at the right time.",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // SAVE BUTTON
            // =================================================

            Button(
                onClick = {

                    if (canSave) {

                        onSave(
                            memoryText.trim(),
                            selectedCategory,
                            selectedPriority
                        )
                    }
                },
                enabled = canSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(21.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple,
                    disabledContainerColor = Color(0xFF292431),
                    contentColor = White,
                    disabledContentColor = TextMuted
                )
            ) {

                Text(
                    text = if (canSave) {
                        "Update Memory   →"
                    } else {
                        "Write a memory first"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}


// ============================================================
// CATEGORY CHIP
// ============================================================

@Composable
private fun PremiumChoiceChip(
    icon: String,
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(58.dp)
            .clip(
                RoundedCornerShape(17.dp)
            )
            .background(
                if (selected) {
                    Color(0xFF2D2050)
                } else {
                    CardDark
                }
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (selected) {
                    PurpleBright
                } else {
                    TextSecondary
                }
            )
        }
    }
}


// ============================================================
// PRIORITY CHIP
// ============================================================

@Composable
private fun PremiumPriorityChip(
    title: String,
    emoji: String,
    color: Color,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .height(78.dp)
            .clip(
                RoundedCornerShape(19.dp)
            )
            .background(
                if (selected) {
                    color.copy(alpha = 0.16f)
                } else {
                    CardDark
                }
            )
            .clickable {
                onClick()
            }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = emoji,
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) {
                color
            } else {
                TextSecondary
            }
        )
    }
}