package com.life_personallifeintelligence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.life_personallifeintelligence.navigation.AppNavigation
import com.life_personallifeintelligence.ui.theme.LIFEPERSONALLIFEINTELLIGENCETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LIFEPERSONALLIFEINTELLIGENCETheme {
                AppNavigation()
            }
        }
    }
}