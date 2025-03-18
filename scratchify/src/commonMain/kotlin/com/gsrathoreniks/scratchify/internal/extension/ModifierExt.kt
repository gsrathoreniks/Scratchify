package com.gsrathoreniks.scratchify.internal.extension

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.gsrathoreniks.scratchify.api.ScratchGridTracker
import com.gsrathoreniks.scratchify.api.ScratchifyController


fun Modifier.applyScratchDetection(
    controller: ScratchifyController,
    gridTracker: ScratchGridTracker,
    brushRadiusPx: Float
) = pointerInput(Unit) {
    detectDragGestures { change, _ ->
        val position = change.position
        if (position.isValidForScratch(size)) {
            if (controller.appendScratchedOffsets(position)) {
                gridTracker.markScratch(position, brushRadiusPx)
                controller.updateScratchProgress(gridTracker.scratchedPercentage())
            }
        }
    }
}


fun Modifier.applyScratchEffect(
    controller: ScratchifyController,
    brushRadiusPx: Float
): Modifier = this.drawWithCache {
    onDrawWithContent {
        drawContent()
        controller.scratchedOffsets.forEach { point ->
            drawCircle(
                color = Color.Transparent,
                radius = brushRadiusPx,
                center = point,
                blendMode = BlendMode.Clear
            )
        }
    }
}
