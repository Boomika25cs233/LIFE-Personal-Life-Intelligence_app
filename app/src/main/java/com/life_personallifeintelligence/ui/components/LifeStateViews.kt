package com.life_personallifeintelligence.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// LIFE PREMIUM DARK COLORS
// ============================================================

private val Background = Color(0xFF09070E)
private val CardDark = Color(0xFF17131F)
private val Purple = Color(0xFF9B6CFF)
private val Pink = Color(0xFFFF5F9E)
private val Blue = Color(0xFF62A8FF)
private val Green = Color(0xFF55D98A)
private val Orange = Color(0xFFFF9A62)

private val White = Color(0xFFFDFBFF)
private val TextGray = Color(0xFFA9A1B2)

private val SoftPurple = Color(0xFF2A2140)
private val SoftPink = Color(0xFF38202D)
private val SoftBlue = Color(0xFF1B2D43)
private val SoftGreen = Color(0xFF19372B)
private val SoftOrange = Color(0xFF3A281E)


// ============================================================
// LOADING VIEW
// ============================================================

@Composable
fun LifeLoadingView(
    message: String = "LIFE is thinking..."
) {

    LifeStateContainer {

        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = SoftPurple
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = Purple,
                    strokeWidth = 3.dp
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = message,
            color = White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = "Just a moment...",
            color = TextGray,
            fontSize = 11.sp
        )
    }
}


// ============================================================
// EMPTY VIEW
// ============================================================

@Composable
fun LifeEmptyView(
    title: String = "Nothing here yet",
    description: String = "LIFE hasn't found anything to show.",
    buttonText: String = "＋ Add Memory",
    onAdd: () -> Unit = {}
) {

    LifeStateContainer {

        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = SoftPurple
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "✦",
                    color = Purple,
                    fontSize = 30.sp
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = title,
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = description,
            color = TextGray,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(19.dp)
        )

        Button(
            onClick = onAdd,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            )
        ) {

            Text(
                text = buttonText,
                color = White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ============================================================
// ERROR VIEW
// ============================================================

@Composable
fun LifeErrorView(
    title: String = "Something went wrong",
    description: String = "LIFE couldn't complete that action.",
    buttonText: String = "Try again",
    onRetry: () -> Unit = {}
) {

    LifeStateContainer {

        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = SoftPink
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "!",
                    color = Pink,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = title,
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = description,
            color = TextGray,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(19.dp)
        )

        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink
            )
        ) {

            Text(
                text = buttonText,
                color = White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ============================================================
// OFFLINE VIEW
// ============================================================

@Composable
fun LifeOfflineView(
    title: String = "You're offline",
    description: String = "Some LIFE features may be unavailable without an internet connection.",
    buttonText: String = "Try again",
    onRetry: () -> Unit = {}
) {

    LifeStateContainer {

        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = SoftOrange
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "⌁",
                    color = Orange,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = title,
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = description,
            color = TextGray,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(19.dp)
        )

        OutlinedButton(
            onClick = onRetry,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Orange
            )
        ) {

            Text(
                text = buttonText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ============================================================
// PERMISSION VIEW
// ============================================================

@Composable
fun LifePermissionView(
    title: String = "Permission needed",
    description: String = "Allow access so LIFE can help you remember important things.",
    buttonText: String = "Grant permission",
    onGrantPermission: () -> Unit = {}
) {

    LifeStateContainer {

        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = SoftBlue
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "🔐",
                    fontSize = 29.sp
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = title,
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = description,
            color = TextGray,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(19.dp)
        )

        Button(
            onClick = onGrantPermission,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue
            )
        ) {

            Text(
                text = buttonText,
                color = White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ============================================================
// GENERIC LIFE STATE CONTAINER
// ============================================================

@Composable
private fun LifeStateContainer(
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 30.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

