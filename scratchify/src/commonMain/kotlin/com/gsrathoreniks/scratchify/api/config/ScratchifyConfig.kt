package com.gsrathoreniks.scratchify.api.config

data class ScratchifyConfig(
    val revealFullAtPercent: Float = 0.75f,
    val isScratchingEnabled: Boolean = true,
    val brushConfig: ScratchifyBrushConfig = ScratchifyBrushConfig()
)