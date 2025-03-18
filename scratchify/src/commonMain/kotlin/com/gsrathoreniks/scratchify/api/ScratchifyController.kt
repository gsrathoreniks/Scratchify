package com.gsrathoreniks.scratchify.api

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.gsrathoreniks.scratchify.api.config.ScratchifyConfig

class ScratchifyController(private val config: ScratchifyConfig = ScratchifyConfig()) {

    private val _scratchedOffsets = mutableStateListOf<Offset>()

    val scratchedOffsets by derivedStateOf { _scratchedOffsets.toSet() }

    var scratchProgress by mutableStateOf(0f)

    var onScratchStarted: (() -> Unit)? = null
    var onScratchCompleted: (() -> Unit)? = null
    var onScratchProgress: ((Float) -> Unit)? = null

    private var isScratching = false

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
        }

        scratchProgress = clampedProgress
        onScratchProgress?.invoke(clampedProgress)

        if (clampedProgress >= 1f || clampedProgress >= config.revealFullAtPercent) {
            onScratchCompleted?.invoke()
        }
    }

    fun resetScratch() {
        _scratchedOffsets.clear()
        scratchProgress = 0f
        isScratching = false
    }
}