package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SoundManager(private val context: Context) {

  private var toneGenerator: ToneGenerator? = null
  private val scope = CoroutineScope(Dispatchers.Default)

  private val vibrator: Vibrator? by lazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
      vm?.defaultVibrator
    } else {
      @Suppress("DEPRECATION")
      context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
  }

  init {
    try {
      toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
    } catch (_: Exception) {
      toneGenerator = null
    }
  }

  fun playCorrectSound(soundEnabled: Boolean) {
    if (!soundEnabled) return
    scope.launch {
      try {
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 120)
        delay(130)
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 180)
      } catch (_: Exception) {}
    }
  }

  fun playWrongSound(soundEnabled: Boolean) {
    if (!soundEnabled) return
    scope.launch {
      try {
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_NACK, 250)
      } catch (_: Exception) {}
    }
  }

  fun playTimerTick(soundEnabled: Boolean) {
    if (!soundEnabled) return
    scope.launch {
      try {
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 60)
      } catch (_: Exception) {}
    }
  }

  fun playCoinCollect(soundEnabled: Boolean) {
    if (!soundEnabled) return
    scope.launch {
      try {
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_D, 90)
        delay(80)
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_0, 120)
      } catch (_: Exception) {}
    }
  }

  fun playLevelComplete(soundEnabled: Boolean) {
    if (!soundEnabled) return
    scope.launch {
      try {
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_1, 100)
        delay(110)
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_3, 100)
        delay(110)
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_5, 100)
        delay(110)
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_8, 250)
      } catch (_: Exception) {}
    }
  }

  fun playAchievementUnlocked(soundEnabled: Boolean) {
    if (!soundEnabled) return
    scope.launch {
      try {
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_A, 120)
        delay(130)
        toneGenerator?.startTone(ToneGenerator.TONE_DTMF_B, 200)
      } catch (_: Exception) {}
    }
  }

  fun vibrateTap(hapticsEnabled: Boolean) {
    if (!hapticsEnabled) return
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator?.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
      } else {
        @Suppress("DEPRECATION")
        vibrator?.vibrate(25)
      }
    } catch (_: Exception) {}
  }

  fun vibrateSuccess(hapticsEnabled: Boolean) {
    if (!hapticsEnabled) return
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val timings = longArrayOf(0, 40, 50, 80)
        val amplitudes = intArrayOf(0, 180, 0, 255)
        vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
      } else {
        @Suppress("DEPRECATION")
        vibrator?.vibrate(100)
      }
    } catch (_: Exception) {}
  }

  fun vibrateError(hapticsEnabled: Boolean) {
    if (!hapticsEnabled) return
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val timings = longArrayOf(0, 70, 60, 70)
        val amplitudes = intArrayOf(0, 220, 0, 220)
        vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
      } else {
        @Suppress("DEPRECATION")
        vibrator?.vibrate(150)
      }
    } catch (_: Exception) {}
  }
}
