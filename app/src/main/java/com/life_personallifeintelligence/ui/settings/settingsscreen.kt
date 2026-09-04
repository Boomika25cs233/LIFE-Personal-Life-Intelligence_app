package com.life_personallifeintelligence.ui.settings

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// PREMIUM DARK COLORS
// ============================================================

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val CardDark2 = Color(0xFF211B2A)

private val Purple = Color(0xFF9B6CFF)
private val Red = Color(0xFFFF5252)

private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)
private val TextMuted = Color(0xFF6E677B)

// ============================================================
// SETTINGS SCREEN
// ============================================================

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onAppearanceClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onDataSourcesClick: () -> Unit = {},
    onWhatLifeKnowsClick: () -> Unit = {},
    onFamilyPermissionsClick: () -> Unit = {},
    onPauseLifeClick: () -> Unit = {},
    onDeleteMemoriesClick: () -> Unit = {}
) {

    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }
    var pauseLifeEnabled by remember { mutableStateOf(false) }

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
                .height(200.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF26153E),
                            Color(0xFF381B54),
                            Color(0xFF15101D),
                            Background
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 22.dp
                    )
            ) {

                // BACK BUTTON

                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            onBackClick()
                        },
                    shape = CircleShape,
                    color = Color(0x25FFFFFF)
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
                    modifier = Modifier.height(20.dp)
                )

                // TITLE & SUBTITLE

                Text(
                    text = "Settings",
                    color = White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Manage your intelligence preferences and privacy",
                    color = TextGray,
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {

            // ================================================
            // SECTION: ACCOUNT & PREFERENCES
            // ================================================

            SettingsSectionHeader(title = "ACCOUNT & PREFERENCES")

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItemCard(
                icon = "👤",
                title = "Profile",
                subtitle = "Personalize your LIFE experience",
                iconBg = Color(0xFF2D1E4A),
                onClick = onProfileClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsToggleCard(
                icon = "🔔",
                title = "Notifications",
                subtitle = "Manage reminders and proactive alerts",
                iconBg = Color(0xFF2D1E4A),
                checked = notificationsEnabled,
                onCheckedChange = {
                    notificationsEnabled = it
                    onNotificationsClick()
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsToggleCard(
                icon = "🎨",
                title = "Appearance",
                subtitle = "Dark mode and visual preferences",
                iconBg = Color(0xFF2D1E4A),
                checked = darkModeEnabled,
                onCheckedChange = {
                    darkModeEnabled = it
                    onAppearanceClick()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ================================================
            // SECTION: INTELLIGENCE & PRIVACY
            // ================================================

            SettingsSectionHeader(title = "INTELLIGENCE & PRIVACY")

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItemCard(
                icon = "🔐",
                title = "Privacy Controls",
                subtitle = "Manage app permissions and access",
                iconBg = Color(0xFF1E2D4A),
                onClick = onPrivacyClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItemCard(
                icon = "📱",
                title = "Data Sources",
                subtitle = "Choose what LIFE can learn from",
                iconBg = Color(0xFF1E2D4A),
                onClick = onDataSourcesClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItemCard(
                icon = "🧠",
                title = "What LIFE Knows",
                subtitle = "Review and edit learned intelligence",
                iconBg = Color(0xFF1E2D4A),
                onClick = onWhatLifeKnowsClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItemCard(
                icon = "👨‍👩‍👧",
                title = "Family Permissions",
                subtitle = "Manage shared responsibilities",
                iconBg = Color(0xFF1E2D4A),
                onClick = onFamilyPermissionsClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ================================================
            // SECTION: CONTROL & DATA
            // ================================================

            SettingsSectionHeader(title = "CONTROL & DATA")

            Spacer(modifier = Modifier.height(10.dp))

            SettingsToggleCard(
                icon = "⏸️",
                title = "Pause LIFE",
                subtitle = "Temporarily suspend learning & insights",
                iconBg = Color(0xFF382D1E),
                checked = pauseLifeEnabled,
                onCheckedChange = {
                    pauseLifeEnabled = it
                    onPauseLifeClick()
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItemCard(
                icon = "🗑️",
                title = "Delete Stored Memories",
                subtitle = "Wipe all saved memories and history",
                iconBg = Color(0xFF381E21),
                titleColor = Red,
                onClick = onDeleteMemoriesClick
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ============================================================
// HELPER COMPONENTS
// ============================================================

@Composable
private fun SettingsSectionHeader(title: String) {

    Text(
        text = title,
        color = TextMuted,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp
    )
}

@Composable
private fun SettingsItemCard(
    icon: String,
    title: String,
    subtitle: String,
    iconBg: Color,
    titleColor: Color = White,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = iconBg
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

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    color = TextGray,
                    fontSize = 13.sp
                )
            }

            Text(
                text = "›",
                color = TextGray,
                fontSize = 22.sp
            )
        }
    }
}

@Composable
private fun SettingsToggleCard(
    icon: String,
    title: String,
    subtitle: String,
    iconBg: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = iconBg
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

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    color = TextGray,
                    fontSize = 13.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = Purple,
                    uncheckedThumbColor = TextGray,
                    uncheckedTrackColor = CardDark2
                )
            )
        }
    }
}
