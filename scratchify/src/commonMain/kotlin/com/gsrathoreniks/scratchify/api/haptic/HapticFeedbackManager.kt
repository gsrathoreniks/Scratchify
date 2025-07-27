package com.gsrathoreniks.scratchify.api.haptic

import com.gsrathoreniks.scratchify.api.config.HapticFeedbackType

/**
 * Interface for platform-specific haptic feedback implementations
 */
interface HapticFeedbackManager {
    /**
     * Performs haptic feedback with the specified type
     */
    fun performHapticFeedback(type: HapticFeedbackType)
    
    /**
     * Checks if haptic feedback is available on the current platform
     */
    fun isHapticFeedbackAvailable(): Boolean
}

/**
 * Default implementation that does nothing (for platforms without haptic support)
 */
internal object NoOpHapticFeedbackManager : HapticFeedbackManager {
    override fun performHapticFeedback(type: HapticFeedbackType) {
        // No-op implementation
    }
    
    override fun isHapticFeedbackAvailable(): Boolean = false
}

/**
 * Expect function to get platform-specific haptic feedback manager
 */
expect fun createHapticFeedbackManager(): HapticFeedbackManager