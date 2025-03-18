package com.gsrathoreniks.scratchify.api.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScratchifyBrushConfig(
    val brushSize: Dp = 4.dp,
    val brushColor: Color = Color.Cyan,
    val opacity: Float = 1f,
)