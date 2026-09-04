package com.life_personallifeintelligence.ui.profile

import android.content.Context
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)
private val SoftPurple = Color(0xFF2A2140)
private val SoftPink = Color(0xFF38202D)

private const val PREFS_NAME = "life_profile"
private const val KEY_NAME = "name"
private const val KEY_ABOUT = "about"

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {}
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val preferences = remember {
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    var name by remember {
        mutableStateOf(
            preferences.getString(
                KEY_NAME,
                "Boomika"
            ) ?: "Boomika"
        )
    }

    var about by remember {
        mutableStateOf(
            preferences.getString(
                KEY_ABOUT,
                "My LIFE profile"
            ) ?: "My LIFE profile"
        )
    }

    var savedMessage by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ========================================================
        // HEADER
        // ========================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
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
                    text = "PROFILE",
                    color = Color(0xFFD9C9EA),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "Your LIFE profile",
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "Tell LIFE a little about yourself.",
                    color = TextGray,
                    fontSize = 11.sp
                )
            }
        }

        // ========================================================
        // CONTENT
        // ========================================================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            // ====================================================
            // PROFILE CARD
            // ====================================================

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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Purple,
                                        Pink
                                    )
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = name
                                .trim()
                                .firstOrNull()
                                ?.uppercase()
                                ?: "B",
                            color = White,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    Text(
                        text = name.ifBlank {
                            "Your Name"
                        },
                        color = White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = about.ifBlank {
                            "My LIFE profile"
                        },
                        color = TextGray,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // ====================================================
            // PERSONAL INFORMATION
            // ====================================================

            Text(
                text = "Personal information",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            ProfileInput(
                label = "Name",
                value = name,
                onValueChange = {
                    name = it
                    savedMessage = false
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            ProfileInput(
                label = "About you",
                value = about,
                onValueChange = {
                    about = it
                    savedMessage = false
                }
            )

            Spacer(
                modifier = Modifier.height(27.dp)
            )

            // ====================================================
            // PERSONALIZATION
            // ====================================================

            Text(
                text = "LIFE personalization",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            PersonalizationCard(
                icon = "🧠",
                title = "Smart memory",
                description =
                    "LIFE learns useful patterns from your activity.",
                background = SoftPurple
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            PersonalizationCard(
                icon = "💜",
                title = "Your preferences",
                description =
                    "Use your profile to make reminders more relevant.",
                background = SoftPink
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // ====================================================
            // SAVE BUTTON
            // ====================================================

            Button(
                onClick = {

                    preferences.edit()
                        .putString(KEY_NAME, name.trim())
                        .putString(KEY_ABOUT, about.trim())
                        .apply()

                    savedMessage = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple
                )
            ) {

                Text(
                    text = "Save Profile",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (savedMessage) {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "✓ Profile saved successfully",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF35D07F),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text =
                    "LIFE • Personal Life Intelligence • v1.0",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF665F70),
                fontSize = 9.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}

// ============================================================
// PROFILE INPUT
// ============================================================

@Composable
private fun ProfileInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = label,
                fontSize = 11.sp
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Purple,
            unfocusedBorderColor = Color(0xFF3A3442),
            focusedLabelColor = Purple,
            unfocusedLabelColor = TextGray,
            focusedTextColor = White,
            unfocusedTextColor = White,
            cursorColor = Purple
        )
    )
}

// ============================================================
// PERSONALIZATION CARD
// ============================================================

@Composable
private fun PersonalizationCard(
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
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = description,
                    color = TextGray,
                    fontSize = 10.sp
                )
            }
        }
    }
}