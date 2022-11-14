package com.camacho.cucsur.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.camacho.cucsur.R

@Composable
fun SplashMorty() {
    Box(modifier = Modifier.fillMaxSize()) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.morty))
        Column(modifier = Modifier.align(Alignment.Center)) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            Text(
                text = "Rick & Morty",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )
        }

    }
}