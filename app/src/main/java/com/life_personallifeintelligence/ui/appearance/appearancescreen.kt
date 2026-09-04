package com.life_personallifeintelligence.ui.appearance

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
import com.life_personallifeintelligence.ui.theme.ThemeManager


// ============================================================
// APPEARANCE SCREEN
// ============================================================

@Composable
fun AppearanceScreen(
    onBackClick: () -> Unit = {}
) {

    // Current selected theme
    val selectedTheme = ThemeManager.themeMode

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        // ====================================================
        // HEADER
        // ====================================================

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
                color = MaterialTheme.colorScheme.surfaceVariant,
                onClick = onBackClick
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "‹",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 31.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "PERSONALIZE",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Appearance",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Make LIFE feel comfortable for you.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )
        }


        // ====================================================
        // PREVIEW CARD
        // ====================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        RoundedCornerShape(26.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(20.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "LIFE ✦",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = when (selectedTheme) {
                            "dark" -> "Dark appearance"
                            "light" -> "Light appearance"
                            else -> "System appearance"
                        },
                        color = Color.White.copy(
                            alpha = 0.8f
                        ),
                        fontSize = 11.sp
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ====================================================
        // THEME SECTION
        // ====================================================

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        ) {

            Text(
                text = "Theme",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Choose how LIFE should look.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // =================================================
            // DARK
            // =================================================

            ThemeOptionCard(
                title = "Dark",
                subtitle = "Easy on the eyes in low light",
                icon = "🌙",
                selected = selectedTheme == "dark",
                onClick = {
                    ThemeManager.setTheme("dark")
                }
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // =================================================
            // LIGHT
            // =================================================

            ThemeOptionCard(
                title = "Light",
                subtitle = "Bright and clean appearance",
                icon = "☀️",
                selected = selectedTheme == "light",
                onClick = {
                    ThemeManager.setTheme("light")
                }
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // =================================================
            // SYSTEM
            // =================================================

            ThemeOptionCard(
                title = "System",
                subtitle = "Follow your device settings",
                icon = "📱",
                selected = selectedTheme == "system",
                onClick = {
                    ThemeManager.setTheme("system")
                }
            )


            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // =================================================
            // CURRENT STATUS
            // =================================================

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color =
                    MaterialTheme.colorScheme.surfaceVariant
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(46.dp),
                        shape = RoundedCornerShape(14.dp),
                        color =
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.15f
                            )
                    ) {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text = "✦",
                                color =
                                    MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Column {

                        Text(
                            text = "Appearance active",
                            color =
                                MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = when (selectedTheme) {
                                "dark" ->
                                    "Dark mode is selected."

                                "light" ->
                                    "Light mode is selected."

                                else ->
                                    "LIFE follows your device."
                            },
                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(35.dp)
            )


            Text(
                text = "LIFE • Personal Life Intelligence",
                modifier = Modifier.fillMaxWidth(),
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 9.sp,
                textAlign =
                    androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


// ============================================================
// THEME OPTION CARD
// ============================================================

@Composable
private fun ThemeOptionCard(
    title: String,
    subtitle: String,
    icon: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val primary =
        MaterialTheme.colorScheme.primary

    val background =
        if (selected) {
            primary.copy(alpha = 0.12f)
        } else {
            MaterialTheme.colorScheme.surface
        }

    val borderColor =
        if (selected) {
            primary
        } else {
            MaterialTheme.colorScheme.outline.copy(
                alpha = 0.35f
            )
        }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        color = background,
        border = ButtonDefaults.outlinedButtonBorder(
            enabled = true
        ).copy(
            brush = Brush.linearGradient(
                listOf(
                    borderColor,
                    borderColor
                )
            )
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(15.dp),
                color = primary.copy(
                    alpha = 0.12f
                )
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 21.sp
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
                    text = title,
                    color =
                        MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = subtitle,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                )
            }


            // =================================================
            // RADIO INDICATOR
            // =================================================

            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(
                        color =
                            if (selected)
                                primary
                            else
                                Color.Transparent,
                        shape = CircleShape
                    ),
                contentAlignment =
                    Alignment.Center
            ) {

                if (selected) {

                    Text(
                        text = "✓",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}