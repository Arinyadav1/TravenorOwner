package com.example.travenorowner.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.travenorowner.ui.theme.AppColors

@Composable
fun TravenorIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconTint: Color = Color.White,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .clip(
                shape = CircleShape
            )
            .size(44.dp),
        onClick = { onClick() },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = AppColors.primaryBlue
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(18.dp)
        )
    }
}