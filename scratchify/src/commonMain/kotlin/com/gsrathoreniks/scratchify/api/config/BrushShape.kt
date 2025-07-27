package com.gsrathoreniks.scratchify.api.config

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Sealed class representing different brush shapes for scratching
 */
sealed class BrushShape {
    
    /**
     * Circular brush shape (default)
     */
    data object Circle : BrushShape()
    
    /**
     * Square brush shape
     */
    data object Square : BrushShape()
    
    /**
     * Star brush shape
     */
    data class Star(val points: Int = 5) : BrushShape()
    
    /**
     * Heart brush shape
     */
    data object Heart : BrushShape()
    
    /**
     * Diamond brush shape
     */
    data object Diamond : BrushShape()
    
    /**
     * Custom brush shape defined by a Path
     */
    data class Custom(val path: Path, val size: Dp = 20.dp) : BrushShape()
}

/**
 * Extension function to create a star path
 */
fun createStarPath(points: Int, outerRadius: Float, innerRadius: Float): Path {
    val path = Path()
    val anglePerPoint = (2 * kotlin.math.PI / points).toFloat()
    
    for (i in 0 until points * 2) {
        val radius = if (i % 2 == 0) outerRadius else innerRadius
        val angle = i * anglePerPoint / 2
        val x = (radius * kotlin.math.cos(angle))
        val y = (radius * kotlin.math.sin(angle))
        
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    return path
}

/**
 * Extension function to create a heart path
 */
fun createHeartPath(size: Float): Path {
    val path = Path()
    val halfSize = size / 2f
    
    path.moveTo(0f, halfSize / 2f)
    path.cubicTo(-halfSize, -halfSize / 2f, -halfSize, halfSize / 2f, 0f, halfSize)
    path.cubicTo(halfSize, halfSize / 2f, halfSize, -halfSize / 2f, 0f, halfSize / 2f)
    
    return path
}

/**
 * Extension function to create a diamond path
 */
fun createDiamondPath(size: Float): Path {
    val path = Path()
    val halfSize = size / 2f
    
    path.moveTo(0f, -halfSize)
    path.lineTo(halfSize, 0f)
    path.lineTo(0f, halfSize)
    path.lineTo(-halfSize, 0f)
    path.close()
    
    return path
}