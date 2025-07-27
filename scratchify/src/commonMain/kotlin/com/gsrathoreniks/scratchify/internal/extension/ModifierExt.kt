package com.gsrathoreniks.scratchify.internal.extension

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import com.gsrathoreniks.scratchify.api.ScratchGridTracker
import com.gsrathoreniks.scratchify.api.ScratchifyController
import com.gsrathoreniks.scratchify.api.config.BrushShape
import com.gsrathoreniks.scratchify.api.config.createDiamondPath
import com.gsrathoreniks.scratchify.api.config.createHeartPath
import com.gsrathoreniks.scratchify.api.config.createStarPath


fun Modifier.applyScratchDetection(
    controller: ScratchifyController,
    gridTracker: ScratchGridTracker,
    brushRadiusPx: Float,
    enableTapToScratch: Boolean = true
) = pointerInput(Unit) {
    // Handle drag gestures (original functionality)
    detectDragGestures { change, _ ->
        val position = change.position
        if (position.isValidForScratch(size)) {
            if (controller.appendScratchedOffsets(position)) {
                gridTracker.markScratch(position, brushRadiusPx)
                controller.updateScratchProgress(gridTracker.scratchedPercentage())
            }
        }
    }
}.then(
    if (enableTapToScratch) {
        pointerInput(Unit) {
            // Handle tap gestures (new functionality)
            detectTapGestures { offset ->
                if (offset.isValidForScratch(size)) {
                    if (controller.appendScratchedOffsets(offset)) {
                        gridTracker.markScratch(offset, brushRadiusPx)
                        controller.updateScratchProgress(gridTracker.scratchedPercentage())
                    }
                }
            }
        }
    } else {
        Modifier
    }
)


fun Modifier.applyScratchEffect(
    controller: ScratchifyController,
    brushRadiusPx: Float,
    brushColor: Color = Color.Transparent,
    brushOpacity: Float = 1f,
    brushShape: BrushShape = BrushShape.Circle
): Modifier = this.drawWithCache {
    onDrawWithContent {
        drawContent()
        val finalColor = if (brushColor == Color.Transparent) Color.Transparent else brushColor.copy(alpha = brushOpacity)
        val blendMode = if (brushColor == Color.Transparent) BlendMode.Clear else BlendMode.SrcOver
        
        controller.scratchedOffsets.forEach { point ->
            drawBrushShape(
                shape = brushShape,
                center = point,
                size = brushRadiusPx * 2f,
                color = finalColor,
                blendMode = blendMode
            )
        }
    }
}

/**
 * Extension function to draw different brush shapes
 */
private fun DrawScope.drawBrushShape(
    shape: BrushShape,
    center: Offset,
    size: Float,
    color: Color,
    blendMode: BlendMode
) {
    when (shape) {
        is BrushShape.Circle -> {
            drawCircle(
                color = color,
                radius = size / 2f,
                center = center,
                blendMode = blendMode
            )
        }
        is BrushShape.Square -> {
            drawRect(
                color = color,
                topLeft = Offset(center.x - size / 2f, center.y - size / 2f),
                size = Size(size, size),
                blendMode = blendMode
            )
        }
        is BrushShape.Star -> {
            val starPath = createStarPath(shape.points, size / 2f, size / 4f)
            translate(center.x, center.y) {
                drawPath(
                    path = starPath,
                    color = color,
                    blendMode = blendMode
                )
            }
        }
        is BrushShape.Heart -> {
            val heartPath = createHeartPath(size)
            translate(center.x, center.y) {
                drawPath(
                    path = heartPath,
                    color = color,
                    blendMode = blendMode
                )
            }
        }
        is BrushShape.Diamond -> {
            val diamondPath = createDiamondPath(size)
            translate(center.x, center.y) {
                drawPath(
                    path = diamondPath,
                    color = color,
                    blendMode = blendMode
                )
            }
        }
        is BrushShape.Custom -> {
            translate(center.x, center.y) {
                drawPath(
                    path = shape.path,
                    color = color,
                    blendMode = blendMode
                )
            }
        }
    }
}
