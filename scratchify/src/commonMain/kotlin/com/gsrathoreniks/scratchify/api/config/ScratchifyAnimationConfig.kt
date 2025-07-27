package com.gsrathoreniks.scratchify.api.config

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

/**
 * Defines different types of reveal animations
 */
enum class RevealAnimationType {
    NONE,           // Instant reveal
    FADE,           // Fade out overlay
    SCALE,          // Scale down overlay
    SLIDE_UP,       // Slide overlay up
    SLIDE_DOWN,     // Slide overlay down
    SLIDE_LEFT,     // Slide overlay left
    SLIDE_RIGHT,    // Slide overlay right
    BOUNCE,         // Bounce effect
    ZOOM_OUT        // Zoom out effect
}

/**
 * Configuration for reveal animations
 */
data class ScratchifyAnimationConfig(
    val revealAnimationType: RevealAnimationType = RevealAnimationType.FADE,
    val animationDurationMs: Int = 500,
    val easing: Easing = FastOutSlowInEasing,
    val enableProgressAnimation: Boolean = true,
    val progressAnimationDurationMs: Int = 150
)