package com.life_personallifeintelligence.ui.dataSources

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
private val SoftGreen = Color(0xFF122A20)


@Composable
fun DataSourcesScreen(
    onBackClick: () -> Unit = {}
) {

    var notificationsEnabled by remember {
        mutableStateOf(true)
    }

    var smsEnabled by remember {
        mutableStateOf(false)
    }

    var calendarEnabled by remember {
        mutableStateOf(false)
    }

    var locationEnabled by remember {
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
                text = "DATA SOURCES",
                color = Color(0xFFD9C9EA),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "What LIFE can access",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Choose which sources LIFE can use to understand your responsibilities.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }


        // ============================================================
        // INTELLIGENCE STATUS
        // ============================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardDark
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = SoftPurple
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "✦",
                            color = Purple,
                            fontSize = 23.sp
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
                        text = "LIFE Intelligence",
                        color = White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "You control what LIFE can access.",
                        color = TextGray,
                        fontSize = 10.sp
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ============================================================
        // SOURCES
        // ============================================================

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        ) {

            Text(
                text = "Connected sources",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )


            DataSourceCard(
                icon = "🔔",
                title = "Notifications",
                description = "Detect useful information from app notifications.",
                enabled = notificationsEnabled,
                onToggle = {
                    notificationsEnabled = it
                }
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            DataSourceCard(
                icon = "💬",
                title = "Messages",
                description = "Identify important responsibilities from messages.",
                enabled = smsEnabled,
                onToggle = {
                    smsEnabled = it
                }
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            DataSourceCard(
                icon = "📅",
                title = "Calendar",
                description = "Use upcoming events and appointments.",
                enabled = calendarEnabled,
                onToggle = {
                    calendarEnabled = it
                }
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            DataSourceCard(
                icon = "📍",
                title = "Location",
                description = "Suggest reminders when location is relevant.",
                enabled = locationEnabled,
                onToggle = {
                    locationEnabled = it
                }
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // ========================================================
            // PRIVACY NOTE
            // ========================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SoftGreen
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
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
                            text = "Your control matters",
                            color = White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "You can disable any source at any time. LIFE should only use information that helps you.",
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
// DATA SOURCE CARD
// ============================================================================

@Composable
private fun DataSourceCard(
    icon: String,
    title: String,
    description: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
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
                color = SoftPurple
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
                    fontSize = 9.sp,
                    lineHeight = 13.sp
                )
            }

            Switch(
                checked = enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = Green,
                    uncheckedThumbColor = TextGray,
                    uncheckedTrackColor = Color(0xFF35303B)
                )
            )
        }
    }
}

