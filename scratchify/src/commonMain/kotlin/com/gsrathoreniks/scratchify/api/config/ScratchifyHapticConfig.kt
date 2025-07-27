package com.gsrathoreniks.scratchify.api.config

/**
 * Defines different types of haptic feedback patterns
 */
enum class HapticFeedbackType {
    NONE,
    LIGHT,
    MEDIUM,
    HEAVY,
    SUCCESS,
    WARNING,
    ERROR
}

/**
 * Configuration for haptic feedback during scratch interactions
 */
data class ScratchifyHapticConfig(
    val isEnabled: Boolean = true,
    val onScratchStarted: HapticFeedbackType = HapticFeedbackType.LIGHT,
    val onScratchProgress: HapticFeedbackType = HapticFeedbackType.NONE,
    val onScratchCompleted: HapticFeedbackType = HapticFeedbackType.SUCCESS,
    val onInstantReveal: HapticFeedbackType = HapticFeedbackType.MEDIUM,
    val progressHapticInterval: Float = 0.25f // Trigger haptic every 25% progress
)