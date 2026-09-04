package com.life_personallifeintelligence.ui.knowledge

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


@Composable
fun WhatLifeKnowsScreen(
    onBackClick: () -> Unit = {}
) {

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
                text = "LIFE INTELLIGENCE",
                color = Color(0xFFD9C9EA),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "What LIFE knows",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "A simple view of the information LIFE uses to make your life easier.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }


        // ============================================================
        // AI CARD
        // ============================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(25.dp),
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
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape,
                        color = SoftPurple
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "✦",
                                color = Purple,
                                fontSize = 25.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.width(13.dp)
                    )

                    Column {

                        Text(
                            text = "LIFE Intelligence",
                            color = White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Learning useful patterns",
                            color = Green,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(17.dp)
                )

                Text(
                    text = "LIFE does not need to know everything about you. It focuses on information that can help you remember responsibilities.",
                    color = TextGray,
                    fontSize = 11.sp,
                    lineHeight = 17.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ============================================================
        // KNOWLEDGE CATEGORIES
        // ============================================================

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        ) {

            Text(
                text = "Your LIFE context",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )


            KnowledgeCard(
                icon = "📅",
                title = "Important dates",
                description = "Birthdays, anniversaries, appointments and deadlines.",
                background = SoftPurple
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            KnowledgeCard(
                icon = "💳",
                title = "Financial responsibilities",
                description = "EMIs, bills, rent and recurring payments.",
                background = SoftGreen
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            KnowledgeCard(
                icon = "🏠",
                title = "Home responsibilities",
                description = "Gas, milk, maintenance and household activities.",
                background = SoftPink
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            KnowledgeCard(
                icon = "📚",
                title = "Study & work",
                description = "Assignments, exams, meetings and important tasks.",
                background = SoftPurple
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // ========================================================
            // PRIVACY
            // ========================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF101B16)
                )
            ) {

                Row(
                    modifier = Modifier.padding(17.dp),
                    verticalAlignment = Alignment.Top
                ) {

                    Text(
                        text = "🔒",
                        fontSize = 20.sp
                    )

                    Spacer(
                        modifier = Modifier.width(11.dp)
                    )

                    Column {

                        Text(
                            text = "You stay in control",
                            color = White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "You can review, change or remove information used by LIFE.",
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
// KNOWLEDGE CARD
// ============================================================================

@Composable
private fun KnowledgeCard(
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
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(15.dp),
                color = background
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 21.sp
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
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

