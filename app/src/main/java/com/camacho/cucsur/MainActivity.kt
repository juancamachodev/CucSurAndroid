package com.camacho.cucsur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.camacho.cucsur.theme.CucSurTheme
import com.camacho.cucsur.ui.CharactersUI

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CucSurTheme {
                CharactersUI()
            }
        }
    }
}