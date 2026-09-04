package com.life_personallifeintelligence.ui.done

import androidx.compose.foundation.background
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
import com.life_personallifeintelligence.model.Memory
import androidx.compose.ui.text.font.FontFamily


private val Background = Color(0xFF0F0D16)
private val CardDark = Color(0xFF1B1725)
private val Purple = Color(0xFF8B5CF6)
private val Green = Color(0xFF55D68A)
private val SoftGreen = Color(0xFF193528)
private val SoftPurple = Color(0xFF2A2140)
private val White = Color.White
private val TextGray = Color(0xFFAAA3B5)

@Composable
fun DoneScreen(
    memories: List<Memory> = emptyList(),
    onBackClick: () -> Unit = {}
) {

    val completedMemories = memories.filter { it.isDone }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // =========================
        // PREMIUM HEADER
        // =========================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(235.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF10251B),
                            Color(0xFF19422E),
                            Color(0xFF15121C)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 22.dp,
                        end = 22.dp,
                        top = 25.dp
                    )
            ) {

                // HEADER

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        color = Color(0x22FFFFFF),
                        onClick = onBackClick
                    ) {

                        Text(
                            text = "‹",
                            color = White,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(
                                start = 12.dp,
                                bottom = 3.dp
                            )
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Column {

                        Text(
                            text = "Completed",
                            fontSize = 32.sp,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Everything you've taken care of",
                            color = TextGray,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(27.dp)
                )

                // COMPLETION SUMMARY

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = Color(0x20FFFFFF)
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = SoftGreen
                        ) {

                            Text(
                                text = "✓",
                                color = Green,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(11.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(13.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = if (
                                    completedMemories.isEmpty()
                                ) {
                                    "Your journey starts here"
                                } else {
                                    "${completedMemories.size} memories completed"
                                },
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = if (
                                    completedMemories.isEmpty()
                                ) {
                                    "Complete a memory and it will appear here."
                                } else {
                                    "Small progress. Big difference. ✨"
                                },
                                color = TextGray,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // =========================
        // CONTENT
        // =========================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = "Your achievements",
                color = White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Things you've successfully taken care of.",
                color = TextGray,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            if (completedMemories.isEmpty()) {

                EmptyDoneCard()

            } else {

                completedMemories.forEach { memory ->

                    CompletedMemoryCard(
                        memory = memory
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}


// =================================
// COMPLETED MEMORY CARD
// =================================

@Composable
private fun CompletedMemoryCard(
    memory: Memory
) {

    val icon = when {
        memory.category.contains(
            "bill",
            ignoreCase = true
        ) -> "💡"

        memory.category.contains(
            "milk",
            ignoreCase = true
        ) -> "🥛"

        memory.category.contains(
            "gas",
            ignoreCase = true
        ) -> "🔥"

        memory.category.contains(
            "medical",
            ignoreCase = true
        ) -> "💊"

        memory.category.contains(
            "college",
            ignoreCase = true
        ) -> "🎓"

        memory.category.contains(
            "loan",
            ignoreCase = true
        ) -> "💰"

        memory.category.contains(
            "shopping",
            ignoreCase = true
        ) -> "🛍️"

        else -> "✦"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(17.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ICON

                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = SoftGreen
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
                        text = memory.text,
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = memory.category,
                        color = TextGray,
                        fontSize = 11.sp
                    )
                }

                // DONE BADGE

                Surface(
                    shape = RoundedCornerShape(11.dp),
                    color = SoftGreen
                ) {

                    Text(
                        text = "✓ Done",
                        color = Green,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 6.dp
                        )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // CATEGORY + PRIORITY

            Row(
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                Surface(
                    shape = RoundedCornerShape(9.dp),
                    color = SoftPurple
                ) {

                    Text(
                        text = "🏷 ${memory.category}",
                        color = White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 6.dp
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(9.dp),
                    color = SoftGreen
                ) {

                    Text(
                        text = "✓ Completed",
                        color = Green,
                        fontSize = 10.sp,
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


// =================================
// EMPTY DONE
// =================================

@Composable
private fun EmptyDoneCard() {

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
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                color = SoftGreen
            ) {

                Text(
                    text = "✓",
                    color = Green,
                    fontSize = 31.sp,
                    modifier = Modifier.padding(18.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Nothing completed yet",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Complete your first memory and celebrate the progress.",
                color = TextGray,
                fontSize = 12.sp
            )
        }
    }
}