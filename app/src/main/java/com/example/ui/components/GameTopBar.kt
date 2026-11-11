package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextPrimary

@Composable
fun GameTopBar(
  title: String,
  coins: Int,
  streakDays: Int = 1,
  soundEnabled: Boolean = true,
  onBackClick: (() -> Unit)? = null,
  onCoinClick: (() -> Unit)? = null,
  onSoundToggle: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(DeepNavyDark, DeepNavySurface)
        )
      )
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Left: Back button or Logo mark
      Row(verticalAlignment = Alignment.CenterVertically) {
        if (onBackClick != null) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier
              .testTag("top_bar_back_button")
              .size(40.dp)
              .clip(CircleShape)
              .background(DeepNavyCard)
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextPrimary
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
          text = title,
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }

      // Right: Streak, Coin Chip, Sound
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Streak Chip
        if (streakDays > 0) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(DeepNavyCard)
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(text = "🔥", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "$streakDays",
              color = FlameOrange,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
        }

        // Coin Chip
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .testTag("top_bar_coin_chip")
            .clip(RoundedCornerShape(16.dp))
            .background(DeepNavyCard)
            .clickable(enabled = onCoinClick != null) { onCoinClick?.invoke() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(text = "🪙", fontSize = 14.sp)
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "$coins",
            color = SolarGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
          if (onCoinClick != null) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Add Coins",
              tint = SolarGold,
              modifier = Modifier.size(14.dp)
            )
          }
        }

        // Sound Toggle
        if (onSoundToggle != null) {
          Spacer(modifier = Modifier.width(8.dp))
          IconButton(
            onClick = onSoundToggle,
            modifier = Modifier
              .testTag("top_bar_sound_toggle")
              .size(36.dp)
              .clip(CircleShape)
              .background(DeepNavyCard)
          ) {
            Icon(
              imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
              contentDescription = "Toggle Sound",
              tint = if (soundEnabled) ElectricCyan else DeepNavyCardBorder,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}
