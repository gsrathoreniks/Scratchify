package com.gsrathoreniks.scratchify.internal.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

fun Offset.isValidForScratch(size: IntSize): Boolean {
    return x in 0f..size.width.toFloat() && y in 0f..size.height.toFloat()
}