package com.gsrathoreniks.scratchify.api.haptic

import com.gsrathoreniks.scratchify.api.config.HapticFeedbackType
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle
import platform.UIKit.UINotificationFeedbackGenerator
import platform.UIKit.UINotificationFeedbackType

/**
 * iOS-specific haptic feedback implementation using UIImpactFeedbackGenerator
 */
@OptIn(ExperimentalForeignApi::class)
internal class IOSHapticFeedbackManager : HapticFeedbackManager {
    
    private val lightImpactGenerator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleLight)
    private val mediumImpactGenerator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
    private val heavyImpactGenerator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy)
    private val notificationGenerator = UINotificationFeedbackGenerator()
    
    init {
        // Prepare generators for better performance
        lightImpactGenerator.prepare()
        mediumImpactGenerator.prepare()
        heavyImpactGenerator.prepare()
        notificationGenerator.prepare()
    }
    
    override fun performHapticFeedback(type: HapticFeedbackType) {
        when (type) {
            HapticFeedbackType.NONE -> return
            HapticFeedbackType.LIGHT -> {
                lightImpactGenerator.impactOccurred()
            }
            HapticFeedbackType.MEDIUM -> {
                mediumImpactGenerator.impactOccurred()
            }
            HapticFeedbackType.HEAVY -> {
                heavyImpactGenerator.impactOccurred()
            }
            HapticFeedbackType.SUCCESS -> {
                notificationGenerator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeSuccess)
            }
            HapticFeedbackType.WARNING -> {
                notificationGenerator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeWarning)
            }
            HapticFeedbackType.ERROR -> {
                notificationGenerator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeError)
            }
        }
    }
    
    override fun isHapticFeedbackAvailable(): Boolean {
        return true // iOS devices generally support haptic feedback
    }
}

/**
 * iOS implementation of the expect function
 */
actual fun createHapticFeedbackManager(): HapticFeedbackManager {
    return IOSHapticFeedbackManager()
}