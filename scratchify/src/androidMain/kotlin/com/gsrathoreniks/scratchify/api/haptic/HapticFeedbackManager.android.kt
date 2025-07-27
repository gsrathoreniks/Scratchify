package com.gsrathoreniks.scratchify.api.haptic

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.gsrathoreniks.scratchify.api.config.HapticFeedbackType

/**
 * Android-specific haptic feedback implementation
 */
internal class AndroidHapticFeedbackManager(private val context: Context) : HapticFeedbackManager {
    
    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }
    
    override fun performHapticFeedback(type: HapticFeedbackType) {
        vibrator?.let { vib ->
            if (vib.hasVibrator()) {
                when (type) {
                    HapticFeedbackType.NONE -> return
                    HapticFeedbackType.LIGHT -> vibrate(vib, 25, 50)
                    HapticFeedbackType.MEDIUM -> vibrate(vib, 50, 100)
                    HapticFeedbackType.HEAVY -> vibrate(vib, 100, 150)
                    HapticFeedbackType.SUCCESS -> vibrate(vib, 80, 120)
                    HapticFeedbackType.WARNING -> vibrate(vib, 60, 80)
                    HapticFeedbackType.ERROR -> vibrate(vib, 150, 200)
                }
            }
        }
    }
    
    private fun vibrate(vibrator: Vibrator, duration: Long, amplitude: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(duration, amplitude.coerceIn(1, 255))
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
    
    override fun isHapticFeedbackAvailable(): Boolean {
        return vibrator?.hasVibrator() == true
    }
}

/**
 * Global context holder for Android haptic feedback
 */
object AndroidHapticContext {
    var context: Context? = null
}

/**
 * Android implementation of the expect function
 */
actual fun createHapticFeedbackManager(): HapticFeedbackManager {
    return AndroidHapticContext.context?.let { context ->
        AndroidHapticFeedbackManager(context)
    } ?: NoOpHapticFeedbackManager
}