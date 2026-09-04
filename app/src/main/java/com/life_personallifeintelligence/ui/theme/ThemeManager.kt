package com.life_personallifeintelligence.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeManager {

    var themeMode by mutableStateOf("system")
        private set

    fun setTheme(mode: String) {
        themeMode = mode
    }
}