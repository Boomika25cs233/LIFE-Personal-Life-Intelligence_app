package com.life_personallifeintelligence.ui.privacy

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// COLORS
// ============================================================

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)
private val Green = Color(0xFF35D07F)
private val Orange = Color(0xFFFF9F43)

private val SoftPurple = Color(0xFF2A2140)
private val SoftPink = Color(0xFF38202D)
private val SoftGreen = Color(0xFF122A20)
private val SoftOrange = Color(0xFF342316)

// ============================================================
// PRIVACY SCREEN
// ============================================================

@Composable
fun PrivacyScreen(
    onBackClick: () -> Unit = {}
) {

    var notificationsAccess by remember {
        mutableStateOf(true)
    }

    var smartMemory by remember {
        mutableStateOf(true)
    }

    var activityAnalysis by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ====================================================
        // HEADER
        // ====================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(245.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF26153E),
                            Color(0xFF43245C),
                            Background
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                    text = "PRIVACY & SECURITY",
                    color = Color(0xFFD9C9EA),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "Your privacy matters",
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "You control what LIFE can access and remember.",
                    color = TextGray,
                    fontSize = 11.sp
                )
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

            // =================================================
            // PRIVACY STATUS
            // =================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SoftGreen
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x25FFFFFF)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "🛡️",
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
                            text = "Privacy protection active",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "LIFE only uses enabled sources to provide its intelligence.",
                            color = TextGray,
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // =================================================
            // DATA ACCESS
            // =================================================

            Text(
                text = "Data access",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PrivacyAccessCard(
                icon = "🔔",
                title = "Notification access",
                description = "Allows LIFE to identify important responsibilities from notifications.",
                checked = notificationsAccess,
                accent = Purple,
                onCheckedChange = {
                    notificationsAccess = it
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PrivacyAccessCard(
                icon = "🧠",
                title = "Smart memory",
                description = "Allows LIFE to organize useful information into memories.",
                checked = smartMemory,
                accent = Pink,
                onCheckedChange = {
                    smartMemory = it
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PrivacyAccessCard(
                icon = "📊",
                title = "Activity analysis",
                description = "Allows LIFE to identify patterns and improve reminders.",
                checked = activityAnalysis,
                accent = Orange,
                onCheckedChange = {
                    activityAnalysis = it
                }
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // =================================================
            // WHAT LIFE USES
            // =================================================

            Text(
                text = "What LIFE uses",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PrivacyInfoCard(
                icon = "💬",
                title = "Relevant information",
                description = "Only information needed to identify a responsibility or useful memory."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PrivacyInfoCard(
                icon = "🗂️",
                title = "Your saved memories",
                description = "Memories you intentionally keep inside the LIFE app."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PrivacyInfoCard(
                icon = "⚙️",
                title = "Your preferences",
                description = "Settings that help LIFE personalize reminders and alerts."
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // =================================================
            // PRIVACY PRINCIPLES
            // =================================================

            Text(
                text = "LIFE privacy principles",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PrivacyPrinciple(
                number = "01",
                title = "You stay in control",
                description = "Access can be turned off whenever you choose.",
                background = SoftPurple
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PrivacyPrinciple(
                number = "02",
                title = "Less unnecessary access",
                description = "LIFE should use only information required for its features.",
                background = SoftPink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PrivacyPrinciple(
                number = "03",
                title = "Your memories belong to you",
                description = "You can manage or remove memories from LIFE.",
                background = SoftOrange
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // =================================================
            // SECURITY NOTE
            // =================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "🔐",
                            fontSize = 22.sp
                        )

                        Spacer(
                            modifier = Modifier.width(10.dp)
                        )

                        Text(
                            text = "Security",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )

                    Text(
                        text = "LIFE is designed with privacy as a core principle. Sensitive permissions should always be requested clearly and only when required by a feature.",
                        color = TextGray,
                        fontSize = 10.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "LIFE • Personal Life Intelligence • v1.0",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF665F70),
                fontSize = 9.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}

// ============================================================
// ACCESS CARD
// ============================================================

@Composable
private fun PrivacyAccessCard(
    icon: String,
    title: String,
    description: String,
    checked: Boolean,
    accent: Color,
    onCheckedChange: (Boolean) -> Unit
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
                modifier = Modifier.size(46.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(
                    red = accent.red,
                    green = accent.green,
                    blue = accent.blue,
                    alpha = 0.16f
                )
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
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = description,
                    color = TextGray,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = accent,
                    uncheckedThumbColor = TextGray,
                    uncheckedTrackColor = Color(0xFF38323F)
                )
            )
        }
    }
}

// ============================================================
// INFO CARD
// ============================================================

@Composable
private fun PrivacyInfoCard(
    icon: String,
    title: String,
    description: String
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(19.dp),
        color = CardDark
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(43.dp),
                shape = RoundedCornerShape(13.dp),
                color = SoftPurple
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 19.sp
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
                    modifier = Modifier.height(3.dp)
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

// ============================================================
// PRIVACY PRINCIPLE
// ============================================================

@Composable
private fun PrivacyPrinciple(
    number: String,
    title: String,
    description: String,
    background: Color
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = background
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = number,
                color = Color(0xFFBFA8D7),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column {

                Text(
                    text = title,
                    color = White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
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