package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanGlow
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AnimatedTimerBar(
  timeRemainingSeconds: Int,
  totalTimeSeconds: Int,
  modifier: Modifier = Modifier
) {
  val fraction = (timeRemainingSeconds.toFloat() / totalTimeSeconds.toFloat()).coerceIn(0f, 1f)

  val animatedFraction by animateFloatAsState(
    targetValue = fraction,
    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
    label = "timer_fraction"
  )

  val isUrgent = timeRemainingSeconds <= 10 && timeRemainingSeconds > 0

  val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1.0f,
    targetValue = if (isUrgent) 1.08f else 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 500),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_scale"
  )

  val timerColor by animateColorAsState(
    targetValue = when {
      timeRemainingSeconds <= 10 -> NeonCoral
      timeRemainingSeconds <= 20 -> SolarGold
      else -> ElectricCyan
    },
    label = "timer_color"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = if (isUrgent) "⚠️ HURRY!" else "⏱️ TIME LEFT",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = if (isUrgent) NeonCoral else TextSecondary,
          letterSpacing = 1.sp
        )
      }

      Text(
        text = "${timeRemainingSeconds}s",
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        color = timerColor,
        modifier = Modifier
          .testTag("timer_countdown_text")
          .scale(if (isUrgent) pulseScale else 1.0f)
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    // Progress Bar Track
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(10.dp)
        .clip(RoundedCornerShape(5.dp))
        .background(DeepNavyCard)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth(animatedFraction)
          .fillMaxHeight()
          .clip(RoundedCornerShape(5.dp))
          .background(
            brush = Brush.horizontalGradient(
              colors = when {
                timeRemainingSeconds <= 10 -> listOf(NeonCoral, AmberGlow)
                timeRemainingSeconds <= 20 -> listOf(SolarGold, AmberGlow)
                else -> listOf(ElectricCyan, ElectricCyanGlow)
              }
            )
          )
      )
    }
  }
}
