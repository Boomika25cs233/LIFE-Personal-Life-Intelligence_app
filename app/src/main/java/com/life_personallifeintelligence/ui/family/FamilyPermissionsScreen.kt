package com.life_personallifeintelligence.ui.family

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


@Composable
fun FamilyPermissionsScreen(
    onBackClick: () -> Unit = {}
) {

    var familyAccess by remember {
        mutableStateOf(false)
    }

    var completionUpdates by remember {
        mutableStateOf(true)
    }

    var importantAlerts by remember {
        mutableStateOf(true)
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
                text = "FAMILY",
                color = Color(0xFFD9C9EA),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Family permissions",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Choose how family members can support your LIFE.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }


        // ============================================================
        // FAMILY ACCESS
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

            Column(
                modifier = Modifier.padding(18.dp)
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
                                text = "👨‍👩‍👧",
                                fontSize = 22.sp
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
                            text = "Family access",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = if (familyAccess)
                                "Family support is enabled"
                            else
                                "No family member connected",
                            color = if (familyAccess)
                                Green
                            else
                                TextGray,
                            fontSize = 10.sp
                        )
                    }

                    Switch(
                        checked = familyAccess,
                        onCheckedChange = {
                            familyAccess = it
                        },
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


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ============================================================
        // PERMISSIONS
        // ============================================================

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        ) {

            Text(
                text = "Support permissions",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )


            PermissionCard(
                icon = "✓",
                title = "Completion updates",
                description = "Allow family members to see when important responsibilities are completed.",
                enabled = completionUpdates,
                onToggle = {
                    completionUpdates = it
                }
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )


            PermissionCard(
                icon = "🔔",
                title = "Important alerts",
                description = "Share selected high-priority reminders with trusted family members.",
                enabled = importantAlerts,
                onToggle = {
                    importantAlerts = it
                }
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // ========================================================
            // SAFETY CARD
            // ========================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SoftGreen
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
                            text = "Privacy first",
                            color = White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Family members only receive information you choose to share.",
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
// PERMISSION CARD
// ============================================================================

@Composable
private fun PermissionCard(
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
                color = SoftPink
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = icon,
                        color = Pink,
                        fontSize = 19.sp
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