package com.gsrathoreniks.scratchify.api

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.gsrathoreniks.scratchify.api.config.ScratchifyConfig
import com.gsrathoreniks.scratchify.api.haptic.HapticFeedbackManager
import com.gsrathoreniks.scratchify.api.haptic.createHapticFeedbackManager

/**
 * Represents the saved state of a scratch card
 */
data class ScratchState(
    val scratchedOffsets: Set<Offset> = emptySet(),
    val scratchProgress: Float = 0f,
    val isRevealed: Boolean = false
)

class ScratchifyController(private val config: ScratchifyConfig = ScratchifyConfig()) {

    private val _scratchedOffsets = mutableStateListOf<Offset>()

    val scratchedOffsets by derivedStateOf { _scratchedOffsets.toSet() }

    var scratchProgress by mutableStateOf(0f)

    var onScratchStarted: (() -> Unit)? = null
    var onScratchCompleted: (() -> Unit)? = null
    var onScratchProgress: ((Float) -> Unit)? = null
    var onInstantReveal: (() -> Unit)? = null

    private var isScratching = false
    private var isRevealed = false
    private val hapticManager: HapticFeedbackManager by lazy { createHapticFeedbackManager() }
    private var lastHapticProgress: Float = 0f

    fun appendScratchedOffsets(offset: Offset): Boolean {
        if (!_scratchedOffsets.contains(offset)) {
            return _scratchedOffsets.add(offset)
        }
        return false
    }


    fun incrementScratchProgress(progress: Float) {
        updateScratchProgress(progress = scratchProgress + progress)
    }

    fun updateScratchProgress(progress: Float) {
        val clampedProgress = progress.coerceIn(0f, 1f)

        if (scratchProgress == 0f && clampedProgress > 0f) {
            onScratchStarted?.invoke()
            // Haptic feedback on scratch start
            if (config.hapticConfig.isEnabled) {
                hapticManager.performHapticFeedback(config.hapticConfig.onScratchStarted)
            }
        }

        scratchProgress = clampedProgress
        onScratchProgress?.invoke(clampedProgress)

        // Progressive haptic feedback
        if (config.hapticConfig.isEnabled && 
            config.hapticConfig.onScratchProgress != com.gsrathoreniks.scratchify.api.config.HapticFeedbackType.NONE) {
            val progressDiff = clampedProgress - lastHapticProgress
            if (progressDiff >= config.hapticConfig.progressHapticInterval) {
                hapticManager.performHapticFeedback(config.hapticConfig.onScratchProgress)
                lastHapticProgress = clampedProgress
            }
        }

        if (clampedProgress >= 1f || clampedProgress >= config.revealFullAtPercent) {
            onScratchCompleted?.invoke()
            // Haptic feedback on completion
            if (config.hapticConfig.isEnabled) {
                hapticManager.performHapticFeedback(config.hapticConfig.onScratchCompleted)
            }
        }
    }

    fun resetScratch() {
        _scratchedOffsets.clear()
        scratchProgress = 0f
        isScratching = false
        isRevealed = false
        lastHapticProgress = 0f
    }

    /**
     * Instantly reveals the scratch card content without any scratching animation
     */
    fun revealInstantly() {
        if (!isRevealed) {
            if (scratchProgress == 0f) {
                onScratchStarted?.invoke()
            }
            scratchProgress = 1f
            isRevealed = true
            onInstantReveal?.invoke()
            onScratchCompleted?.invoke()
            // Haptic feedback for instant reveal
            if (config.hapticConfig.isEnabled) {
                hapticManager.performHapticFeedback(config.hapticConfig.onInstantReveal)
            }
        }
    }

    /**
     * Checks if the content has been revealed (either through scratching or instant reveal)
     */
    fun isContentRevealed(): Boolean {
        return scratchProgress >= config.revealFullAtPercent || isRevealed
    }

    /**
     * Saves the current scratch state to a ScratchState object
     */
    fun saveState(): ScratchState {
        return ScratchState(
            scratchedOffsets = scratchedOffsets,
            scratchProgress = scratchProgress,
            isRevealed = isRevealed
        )
    }

    /**
     * Restores the scratch state from a ScratchState object
     */
    fun restoreState(state: ScratchState) {
        _scratchedOffsets.clear()
        _scratchedOffsets.addAll(state.scratchedOffsets)
        scratchProgress = state.scratchProgress
        isRevealed = state.isRevealed
        
        // Trigger appropriate callbacks based on restored state
        if (state.scratchProgress > 0f) {
            onScratchStarted?.invoke()
            onScratchProgress?.invoke(state.scratchProgress)
        }
        
        if (state.isRevealed || state.scratchProgress >= config.revealFullAtPercent) {
            onScratchCompleted?.invoke()
        }
    }
}