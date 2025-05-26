package com.example.apptvseries.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apptvseries.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: LogoSize = LogoSize.Medium
) {
    val logoSize = when (size) {
        LogoSize.Small -> 32.dp
        LogoSize.Medium -> 48.dp
        LogoSize.Large -> 64.dp
        LogoSize.XLarge -> 120.dp
    }

    Card(
        modifier = modifier.size(logoSize),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.tv_series_logo), // Remplacez par le nom de votre fichier
            contentDescription = "TV Series App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        )
    }
}

@Composable
fun AppLogoWithBackground(
    modifier: Modifier = Modifier,
    size: LogoSize = LogoSize.Large
) {
    val logoSize = when (size) {
        LogoSize.Small -> 40.dp
        LogoSize.Medium -> 56.dp
        LogoSize.Large -> 80.dp
        LogoSize.XLarge -> 140.dp
    }

    Box(
        modifier = modifier
            .size(logoSize)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tv_series_logo), // Remplacez par le nom de votre fichier
            contentDescription = "TV Series App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

enum class LogoSize {
    Small, Medium, Large, XLarge
}