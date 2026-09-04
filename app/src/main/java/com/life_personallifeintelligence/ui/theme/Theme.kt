package com.life_personallifeintelligence.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


// ============================================================
// DARK COLOR SCHEME
// ============================================================

private val DarkColorScheme = darkColorScheme(

    primary = Purple80,

    secondary = PurpleGrey80,

    tertiary = Pink80
)


// ============================================================
// LIGHT COLOR SCHEME
// ============================================================

private val LightColorScheme = lightColorScheme(

    primary = Purple40,

    secondary = PurpleGrey40,

    tertiary = Pink40

    /*
    Other default colors can be overridden here.

    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


// ============================================================
// LIFE APP THEME
// ============================================================

@Composable
fun LIFEPERSONALLIFEINTELLIGENCETheme(

    dynamicColor: Boolean = true,

    content: @Composable () -> Unit

) {

    // ----------------------------------------------------------
    // SYSTEM THEME
    // ----------------------------------------------------------

    val systemDarkTheme = isSystemInDarkTheme()


    // ----------------------------------------------------------
    // SELECT THEME
    // ----------------------------------------------------------

    val darkTheme = when (ThemeManager.themeMode) {

        "dark" -> true

        "light" -> false

        else -> systemDarkTheme
    }


    // ----------------------------------------------------------
    // COLOR SCHEME
    // ----------------------------------------------------------

    val colorScheme = when {

        // Android 12+ Dynamic Colors
        dynamicColor &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            val context = LocalContext.current

            if (darkTheme) {

                dynamicDarkColorScheme(context)

            } else {

                dynamicLightColorScheme(context)
            }
        }

        // LIFE Dark Theme
        darkTheme -> {

            DarkColorScheme
        }

        // LIFE Light Theme
        else -> {

            LightColorScheme
        }
    }


    // ----------------------------------------------------------
    // MATERIAL THEME
    // ----------------------------------------------------------

    MaterialTheme(

        colorScheme = colorScheme,

        typography = Typography,

        content = content
    )
}