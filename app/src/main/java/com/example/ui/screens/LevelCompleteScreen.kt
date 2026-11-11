package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AdBannerComposable
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.StarEmpty
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LevelCompleteScreen(
  levelNumber: Int,
  starsEarned: Int,
  score: Int,
  bonusCoins: Int,
  xpEarned: Int,
  onNextLevelClick: () -> Unit,
  onReplayClick: () -> Unit,
  onHomeClick: () -> Unit
) {
  val scale1 = remember { Animatable(0f) }
  val scale2 = remember { Animatable(0f) }
  val scale3 = remember { Animatable(0f) }

  LaunchedEffect(starsEarned) {
    if (starsEarned >= 1) {
      scale1.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
    }
    if (starsEarned >= 2) {
      scale2.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
    }
    if (starsEarned >= 3) {
      scale3.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
      .padding(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Spacer(modifier = Modifier.height(20.dp))

    // Celebration Header
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = "VICTORY!",
        color = SolarGold,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 2.sp
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Level $levelNumber Completed",
        color = ElectricCyan,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Animated 3 Stars
      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Star 1
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = "Star 1",
          tint = if (starsEarned >= 1) SolarGold else StarEmpty,
          modifier = Modifier
            .size(54.dp)
            .scale(if (starsEarned >= 1) scale1.value else 0.7f)
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Star 2 (Higher Center)
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = "Star 2",
          tint = if (starsEarned >= 2) SolarGold else StarEmpty,
          modifier = Modifier
            .size(72.dp)
            .scale(if (starsEarned >= 2) scale2.value else 0.7f)
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Star 3
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = "Star 3",
          tint = if (starsEarned >= 3) SolarGold else StarEmpty,
          modifier = Modifier
            .size(54.dp)
            .scale(if (starsEarned >= 3) scale3.value else 0.7f)
        )
      }
    }

    // Score & Rewards Breakdown Card
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, DeepNavyCardBorder),
      modifier = Modifier
        .testTag("level_results_card")
        .fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          RewardStatItem(label = "SCORE", value = "$score", emoji = "🎯", color = ElectricCyan)
          RewardStatItem(label = "COINS", value = "+$bonusCoins", emoji = "🪙", color = SolarGold)
          RewardStatItem(label = "EXP", value = "+$xpEarned", emoji = "⚡", color = NeonEmerald)
        }
      }
    }

    // Action Buttons
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      if (levelNumber < 100) {
        Button(
          onClick = onNextLevelClick,
          colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .testTag("next_level_button")
            .fillMaxWidth()
            .height(54.dp)
        ) {
          Text(
            text = "NEXT LEVEL",
            color = DeepNavyDark,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            tint = DeepNavyDark
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onReplayClick,
          colors = ButtonDefaults.buttonColors(containerColor = DeepNavyCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, DeepNavyCardBorder),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .testTag("replay_level_button")
            .weight(1f)
            .height(48.dp)
        ) {
          Icon(imageVector = Icons.Default.Refresh, contentDescription = "Replay", tint = TextPrimary)
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "REPLAY", color = TextPrimary, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = onHomeClick,
          colors = ButtonDefaults.buttonColors(containerColor = DeepNavyCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, DeepNavyCardBorder),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .testTag("home_from_results_button")
            .weight(1f)
            .height(48.dp)
        ) {
          Text(text = "MAIN MENU", color = TextPrimary, fontWeight = FontWeight.Bold)
        }
      }
    }

    AdBannerComposable()
  }
}

@Composable
private fun RewardStatItem(
  label: String,
  value: String,
  emoji: String,
  color: androidx.compose.ui.graphics.Color
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = emoji, fontSize = 24.sp)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = value, color = color, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
    Text(text = label, color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
  }
}
