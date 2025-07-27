package com.gsrathoreniks.scratchify.api

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import com.gsrathoreniks.scratchify.api.config.RevealAnimationType
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

        // Animated reveal based on configuration
        val floatAnimationSpec = tween<Float>(
            durationMillis = config.animationConfig.animationDurationMs,
            easing = config.animationConfig.easing
        )
        
        val intOffsetAnimationSpec = tween<androidx.compose.ui.unit.IntOffset>(
            durationMillis = config.animationConfig.animationDurationMs,
            easing = config.animationConfig.easing
        )
        
        val overlayAlpha by animateFloatAsState(
            targetValue = if (shouldShowOverlay) 1f else 0f,
            animationSpec = floatAnimationSpec,
            label = "overlay_alpha"
        )
        
        val overlayScale by animateFloatAsState(
            targetValue = if (shouldShowOverlay) 1f else 0f,
            animationSpec = floatAnimationSpec,
            label = "overlay_scale"
        )

        AnimatedVisibility(
            visible = shouldShowOverlay,
            exit = when (config.animationConfig.revealAnimationType) {
                RevealAnimationType.NONE -> fadeOut(tween(0))
                RevealAnimationType.FADE -> fadeOut(floatAnimationSpec)
                RevealAnimationType.SCALE -> scaleOut(floatAnimationSpec)
                RevealAnimationType.SLIDE_UP -> slideOutVertically(intOffsetAnimationSpec) { -it }
                RevealAnimationType.SLIDE_DOWN -> slideOutVertically(intOffsetAnimationSpec) { it }
                RevealAnimationType.SLIDE_LEFT -> slideOutHorizontally(intOffsetAnimationSpec) { -it }
                RevealAnimationType.SLIDE_RIGHT -> slideOutHorizontally(intOffsetAnimationSpec) { it }
                RevealAnimationType.BOUNCE -> scaleOut(floatAnimationSpec)
                RevealAnimationType.ZOOM_OUT -> scaleOut(floatAnimationSpec)
            }
        ) {
            // Brush size per touch event (standard index finger size).
            val density = LocalDensity.current
            val brushRadiusPx = remember(density) {
                with(density) { config.brushConfig.brushSize.toPx() }
            }
            // Create a grid tracker using the overlay dimensions.
            val overlayWidthPx = with(density) { maxWidth.toPx() }
            val overlayHeightPx = with(density) { maxHeight.toPx() }
            val gridTracker = remember(overlayWidthPx, overlayHeightPx, config.gridResolution) {
                ScratchGridTracker(
                    overlayWidthPx,
                    overlayHeightPx,
                    resolution = config.gridResolution
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .let { modifier ->
                        when (config.animationConfig.revealAnimationType) {
                            RevealAnimationType.SCALE, RevealAnimationType.BOUNCE, RevealAnimationType.ZOOM_OUT -> 
                                modifier.scale(overlayScale)
                            RevealAnimationType.FADE -> 
                                modifier.alpha(overlayAlpha)
                            else -> modifier
                        }
                    }
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .applyScratchDetection(
                        gridTracker = gridTracker,
                        controller = controller,
                        brushRadiusPx = brushRadiusPx,
                        enableTapToScratch = config.enableTapToScratch
                    )
                    .applyScratchEffect(
                        controller = controller, 
                        brushRadiusPx = brushRadiusPx,
                        brushColor = config.brushConfig.brushColor,
                        brushOpacity = config.brushConfig.opacity,
                        brushShape = config.brushConfig.brushShape
                    )
            ) {
                overlayContent()
            }
        }
    }
}



