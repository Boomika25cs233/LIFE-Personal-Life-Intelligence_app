package com.life_personallifeintelligence.ui.delete

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val Red = Color(0xFFFF5B6E)
private val SoftRed = Color(0xFF351820)
private val SoftPurple = Color(0xFF2A2140)

@Composable
fun DeleteMemoriesScreen(
    onBackClick: () -> Unit = {},
    onDeleteAllClick: () -> Unit = {}
) {

    var showConfirmation by remember {
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
                text = "DATA CONTROL",
                color = Color(0xFFFF9AA5),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Delete memories",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Remove memories stored by LIFE whenever you choose.",
                color = TextGray,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }


        // ============================================================
        // WARNING CARD
        // ============================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = SoftRed
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
                        color = Color(0x332F0A10)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "⚠",
                                color = Red,
                                fontSize = 24.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.width(13.dp)
                    )

                    Column {

                        Text(
                            text = "This action is permanent",
                            color = White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Deleted memories cannot be recovered.",
                            color = Color(0xFFFFB8C0),
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Your account and LIFE settings will remain unchanged.",
                    color = TextGray,
                    fontSize = 10.sp,
                    lineHeight = 15.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // ============================================================
        // DELETE OPTIONS
        // ============================================================

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        ) {

            Text(
                text = "Memory controls",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )


            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = CardDark
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
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
                                text = "💾",
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
                            text = "Your memories",
                            color = White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Remove all memories currently stored by LIFE.",
                            color = TextGray,
                            fontSize = 9.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // ========================================================
            // DELETE BUTTON
            // ========================================================

            Button(
                onClick = {
                    showConfirmation = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red
                )
            ) {

                Text(
                    text = "🗑  Delete all memories",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            Text(
                text = "This does not delete your LIFE profile or app settings.",
                modifier = Modifier.fillMaxWidth(),
                color = TextGray,
                fontSize = 9.sp,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.height(35.dp)
            )


            Text(
                text = "LIFE • Personal Life Intelligence",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF665F70),
                fontSize = 9.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }


    // ================================================================
    // CONFIRMATION DIALOG
    // ================================================================

    if (showConfirmation) {

        AlertDialog(
            onDismissRequest = {
                showConfirmation = false
            },

            containerColor = CardDark,

            title = {
                Text(
                    text = "Delete all memories?",
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text(
                    text = "This action permanently removes all memories stored by LIFE. This cannot be undone.",
                    color = TextGray,
                    fontSize = 12.sp
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        showConfirmation = false

                        onDeleteAllClick()
                    }
                ) {

                    Text(
                        text = "DELETE",
                        color = Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showConfirmation = false
                    }
                ) {

                    Text(
                        text = "CANCEL",
                        color = TextGray
                    )
                }
            }
        )
    }
}