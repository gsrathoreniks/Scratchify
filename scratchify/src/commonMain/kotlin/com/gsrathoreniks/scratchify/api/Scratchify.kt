package com.gsrathoreniks.scratchify.api

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import com.gsrathoreniks.scratchify.api.config.ScratchifyConfig
import com.gsrathoreniks.scratchify.internal.extension.applyScratchDetection
import com.gsrathoreniks.scratchify.internal.extension.applyScratchEffect

@Composable
fun Scratchify(
    modifier: Modifier = Modifier,
    config: ScratchifyConfig = ScratchifyConfig(),
    controller: ScratchifyController = remember { ScratchifyController(config) },
    contentToReveal: @Composable () -> Unit,
    overlayContent: @Composable () -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        contentToReveal()
        val shouldShowOverlay = config.isScratchingEnabled &&
                controller.scratchProgress < config.revealFullAtPercent

        AnimatedVisibility( visible = shouldShowOverlay ) {
            // Brush size per touch event (standard index finger size).
            val density = LocalDensity.current
            val brushRadiusPx = remember(density) {
                with(density) { config.brushConfig.brushSize.toPx() }
            }
            // Create a grid tracker using the overlay dimensions.
            val overlayWidthPx = with(density) { maxWidth.toPx() }
            val overlayHeightPx = with(density) { maxHeight.toPx() }
            val gridTracker = remember(overlayWidthPx, overlayHeightPx) {
                ScratchGridTracker(
                    overlayWidthPx,
                    overlayHeightPx,
                    resolution = 150
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .applyScratchDetection(
                        gridTracker = gridTracker,
                        controller = controller,
                        brushRadiusPx = brushRadiusPx,
                    )
                    .applyScratchEffect(controller = controller, brushRadiusPx = brushRadiusPx)
            ) {
                overlayContent()
            }
        }
    }
}



