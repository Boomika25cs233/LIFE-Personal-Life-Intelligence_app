package com.life_personallifeintelligence.ui.notifications

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
private val TextMuted = Color(0xFF716A78)

private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)
private val Green = Color(0xFF35D07F)
private val Orange = Color(0xFFFF9F43)

private val SoftPurple = Color(0xFF2A2140)
private val SoftPink = Color(0xFF38202D)
private val SoftOrange = Color(0xFF342316)

// ============================================================
// NOTIFICATIONS SCREEN
// ============================================================

@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit = {}
) {

    var notificationsEnabled by remember {
        mutableStateOf(true)
    }

    var urgentAlerts by remember {
        mutableStateOf(true)
    }

    var upcomingAlerts by remember {
        mutableStateOf(true)
    }

    var followUpAlerts by remember {
        mutableStateOf(true)
    }

    var soundEnabled by remember {
        mutableStateOf(true)
    }

    var vibrationEnabled by remember {
        mutableStateOf(true)
    }

    var quietHours by remember {
        mutableStateOf(false)
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
                .height(235.dp)
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
                    text = "NOTIFICATIONS",
                    color = Color(0xFFD9C9EA),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "Stay informed",
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "LIFE will remind you about what matters.",
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
            // MASTER NOTIFICATION
            // =================================================

            NotificationCard(
                icon = "🔔",
                title = "LIFE notifications",
                description = "Allow LIFE to send important reminders.",
                background = SoftPurple,
                checked = notificationsEnabled,
                onCheckedChange = {
                    notificationsEnabled = it
                }
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // =================================================
            // REMINDER TYPES
            // =================================================

            Text(
                text = "Reminder alerts",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            SettingSwitchCard(
                icon = "🚨",
                title = "Urgent responsibilities",
                description = "Alert me when something needs immediate attention.",
                checked = urgentAlerts,
                accent = Orange,
                enabled = notificationsEnabled,
                onCheckedChange = {
                    urgentAlerts = it
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            SettingSwitchCard(
                icon = "📅",
                title = "Upcoming memories",
                description = "Remind me before important events and deadlines.",
                checked = upcomingAlerts,
                accent = Purple,
                enabled = notificationsEnabled,
                onCheckedChange = {
                    upcomingAlerts = it
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            SettingSwitchCard(
                icon = "🔁",
                title = "Follow-up reminders",
                description = "Keep reminding me until an important item is completed.",
                checked = followUpAlerts,
                accent = Pink,
                enabled = notificationsEnabled,
                onCheckedChange = {
                    followUpAlerts = it
                }
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // =================================================
            // NOTIFICATION STYLE
            // =================================================

            Text(
                text = "Notification style",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            SettingSwitchCard(
                icon = "🔊",
                title = "Notification sound",
                description = "Play a sound for important LIFE alerts.",
                checked = soundEnabled,
                accent = Purple,
                enabled = notificationsEnabled,
                onCheckedChange = {
                    soundEnabled = it
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            SettingSwitchCard(
                icon = "📳",
                title = "Vibration",
                description = "Vibrate when LIFE sends an important alert.",
                checked = vibrationEnabled,
                accent = Pink,
                enabled = notificationsEnabled,
                onCheckedChange = {
                    vibrationEnabled = it
                }
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // =================================================
            // QUIET HOURS
            // =================================================

            Text(
                text = "Quiet time",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            SettingSwitchCard(
                icon = "🌙",
                title = "Quiet hours",
                description = "Reduce non-urgent notifications during your rest time.",
                checked = quietHours,
                accent = Green,
                enabled = notificationsEnabled,
                onCheckedChange = {
                    quietHours = it
                }
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // =================================================
            // INTELLIGENCE NOTE
            // =================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(17.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(45.dp),
                        shape = RoundedCornerShape(14.dp),
                        color = SoftPink
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "✦",
                                fontSize = 21.sp,
                                color = Pink
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
                            text = "LIFE Intelligence",
                            color = White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "LIFE is designed to avoid unnecessary alerts and focus on things that actually matter.",
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
// MASTER NOTIFICATION CARD
// ============================================================

@Composable
private fun NotificationCard(
    icon: String,
    title: String,
    description: String,
    background: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = background
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(15.dp),
                color = Color(0x25FFFFFF)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 22.sp
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = description,
                    color = TextGray,
                    fontSize = 10.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = Purple,
                    uncheckedThumbColor = TextGray,
                    uncheckedTrackColor = Color(0xFF38323F)
                )
            )
        }
    }
}

// ============================================================
// SETTING SWITCH CARD
// ============================================================

@Composable
private fun SettingSwitchCard(
    icon: String,
    title: String,
    description: String,
    checked: Boolean,
    accent: Color,
    enabled: Boolean,
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
                modifier = Modifier.size(45.dp),
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
                    color = if (enabled) White else TextGray,
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
                enabled = enabled,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = accent,
                    uncheckedThumbColor = TextGray,
                    uncheckedTrackColor = Color(0xFF38323F),
                    disabledCheckedThumbColor = TextMuted,
                    disabledCheckedTrackColor = Color(0xFF29242F)
                )
            )
        }
    }
}

