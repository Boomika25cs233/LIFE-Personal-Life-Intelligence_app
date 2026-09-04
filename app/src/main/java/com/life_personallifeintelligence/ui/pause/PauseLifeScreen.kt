package com.life_personallifeintelligence.ui.pause

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)
private val Green = Color(0xFF35D07F)

private val SoftPurple = Color(0xFF2A2140)
private val SoftPink = Color(0xFF38202D)
private val SoftGreen = Color(0xFF122A20)
private val SoftOrange = Color(0xFF342316)

@Composable
fun PauseLifeScreen(
    onBackClick: () -> Unit = {}
) {

    var isPaused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ============================================================
        // HEADER
        // ============================================================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 22.dp
                )
        ) {

            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = Color(0x25FFFFFF),
                onClick = onBackClick
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

            Text(
                text = "LIFE CONTROL",
                color = Color(0xFFD9C9EA),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Pause LIFE",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Temporarily stop LIFE intelligence and reminders whenever you need a break.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }


        // ============================================================
        // STATUS CARD
        // ============================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardDark
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(54.dp),
                        shape = CircleShape,
                        color = if (isPaused)
                            SoftOrange
                        else
                            SoftGreen
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = if (isPaused)
                                    "⏸"
                                else
                                    "✦",
                                fontSize = 24.sp,
                                color = if (isPaused)
                                    Color(0xFFFF9F43)
                                else
                                    Green
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
                            text = if (isPaused)
                                "LIFE is paused"
                            else
                                "LIFE is active",
                            color = White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = if (isPaused)
                                "Intelligence and reminder suggestions are paused."
                            else
                                "LIFE is actively helping you remember.",
                            color = TextGray,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }

                    Switch(
                        checked = isPaused,
                        onCheckedChange = {
                            isPaused = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White,
                            checkedTrackColor = Color(0xFFFF9F43),
                            uncheckedThumbColor = TextGray,
                            uncheckedTrackColor = Color(0xFF35303B)
                        )
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ============================================================
        // WHAT HAPPENS
        // ============================================================

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        ) {

            Text(
                text = "What happens when paused?",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )


            PauseInfoCard(
                icon = "🔕",
                title = "No new suggestions",
                description = "LIFE will temporarily stop creating new intelligent reminder suggestions.",
                background = SoftPurple
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            PauseInfoCard(
                icon = "🧠",
                title = "Learning pauses",
                description = "LIFE will temporarily stop learning new activity patterns.",
                background = SoftPink
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            PauseInfoCard(
                icon = "💾",
                title = "Your memories stay safe",
                description = "Existing memories are not deleted and remain available when LIFE resumes.",
                background = SoftGreen
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // ========================================================
            // RESUME INFORMATION
            // ========================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SoftPurple
                )
            ) {

                Row(
                    modifier = Modifier.padding(17.dp),
                    verticalAlignment = Alignment.Top
                ) {

                    Text(
                        text = "💜",
                        fontSize = 20.sp
                    )

                    Spacer(
                        modifier = Modifier.width(11.dp)
                    )

                    Column {

                        Text(
                            text = "You are always in control",
                            color = White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "You can resume LIFE whenever you are ready.",
                            color = TextGray,
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(30.dp)
            )


            Text(
                text = if (isPaused)
                    "LIFE is currently taking a break."
                else
                    "LIFE is ready to help.",
                modifier = Modifier.fillMaxWidth(),
                color = if (isPaused)
                    Color(0xFFFF9F43)
                else
                    Green,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )


            Text(
                text = "LIFE • Personal Life Intelligence",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF665F70),
                fontSize = 9.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


// ============================================================================
// PAUSE INFORMATION CARD
// ============================================================================

@Composable
private fun PauseInfoCard(
    icon: String,
    title: String,
    description: String,
    background: Color
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = CardDark
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(47.dp),
                shape = RoundedCornerShape(14.dp),
                color = background
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

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
                    fontSize = 9.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}