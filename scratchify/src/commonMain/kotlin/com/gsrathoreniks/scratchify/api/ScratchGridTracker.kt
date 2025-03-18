package com.gsrathoreniks.scratchify.api

import androidx.compose.ui.geometry.Offset
import kotlin.math.max
import kotlin.math.min


class ScratchGridTracker(
    width: Float,
    height: Float,
    private val resolution: Int = 150
) {
    private val totalCells = resolution * resolution
    private val grid = BooleanArray(totalCells) { false }
    private var scratchedCount: Int = 0

    private val cellWidth: Float = width / resolution
    private val cellHeight: Float = height / resolution

    fun markScratch(center: Offset, radius: Float) {
        val left = center.x - radius
        val top = center.y - radius
        val right = center.x + radius
        val bottom = center.y + radius

        val startCol = max(0, (left / cellWidth).toInt())
        val endCol = min(resolution - 1, (right / cellWidth).toInt())
        val startRow = max(0, (top / cellHeight).toInt())
        val endRow = min(resolution - 1, (bottom / cellHeight).toInt())

        val rSq = radius * radius
        for (row in startRow..endRow) {
            val cellCenterY = (row + 0.5f) * cellHeight
            for (col in startCol..endCol) {
                val cellCenterX = (col + 0.5f) * cellWidth
                val dx = cellCenterX - center.x
                val dy = cellCenterY - center.y
                if (dx * dx + dy * dy <= rSq) {
                    val index = row * resolution + col
                    if (!grid[index]) {
                        grid[index] = true
                        scratchedCount++
                    }
                }
            }
        }
    }

    fun scratchedPercentage(): Float = scratchedCount.toFloat() / totalCells
}
