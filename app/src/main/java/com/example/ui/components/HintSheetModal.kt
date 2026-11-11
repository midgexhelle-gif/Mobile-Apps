package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.window.Dialog
import com.example.model.Puzzle
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HintSheetModal(
  puzzle: Puzzle,
  currentCoins: Int,
  unlockedHintLevels: Set<Int>, // 1, 2, 3
  onUnlockWithCoins: (tier: Int, cost: Int) -> Unit,
  onWatchAdForHint: () -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Box(
      modifier = Modifier
        .testTag("hint_modal_dialog")
        .fillMaxWidth()
        .clip(RoundedCornerShape(24.dp))
        .background(DeepNavySurface)
        .border(1.5.dp, DeepNavyCardBorder, RoundedCornerShape(24.dp))
        .padding(20.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        // Modal Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "💡", fontSize = 22.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "PUZZLE HINTS",
              color = SolarGold,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextSecondary
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tier 1 Hint
        HintTierCard(
          tier = 1,
          title = "Small Nudge",
          cost = 15,
          isUnlocked = 1 in unlockedHintLevels,
          content = puzzle.hint1,
          canAfford = currentCoins >= 15,
          onUnlock = { onUnlockWithCoins(1, 15) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Tier 2 Hint
        HintTierCard(
          tier = 2,
          title = "Detailed Clue",
          cost = 25,
          isUnlocked = 2 in unlockedHintLevels,
          content = puzzle.hint2,
          canAfford = currentCoins >= 25,
          onUnlock = { onUnlockWithCoins(2, 25) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Tier 3 Hint
        HintTierCard(
          tier = 3,
          title = "Strong Solution Clue",
          cost = 40,
          isUnlocked = 3 in unlockedHintLevels,
          content = puzzle.hint3,
          canAfford = currentCoins >= 40,
          onUnlock = { onUnlockWithCoins(3, 40) }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Watch Ad for Free Hint button
        Button(
          onClick = onWatchAdForHint,
          colors = ButtonDefaults.buttonColors(
            containerColor = DeepNavyCard
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.6f)),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .testTag("watch_ad_for_hint_button")
            .fillMaxWidth()
            .height(48.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Tv,
            contentDescription = "Rewarded Ad",
            tint = ElectricCyan,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Watch Ad for Free Hint",
            color = ElectricCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}

@Composable
private fun HintTierCard(
  tier: Int,
  title: String,
  cost: Int,
  isUnlocked: Boolean,
  content: String,
  canAfford: Boolean,
  onUnlock: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(if (isUnlocked) DeepNavyDark else DeepNavyCard)
      .border(1.dp, if (isUnlocked) NeonEmerald.copy(alpha = 0.5f) else DeepNavyCardBorder, RoundedCornerShape(14.dp))
      .padding(12.dp)
  ) {
    if (isUnlocked) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Tier $tier: $title", color = NeonEmerald, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = content, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
      }
    } else {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(text = "Tier $tier: $title", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
          Text(text = "Tap to reveal hint clue", color = TextPrimary, fontSize = 13.sp)
        }

        Button(
          onClick = onUnlock,
          enabled = canAfford,
          colors = ButtonDefaults.buttonColors(
            containerColor = SolarGold,
            disabledContainerColor = DeepNavyCardBorder
          ),
          shape = RoundedCornerShape(10.dp),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(text = "🪙 $cost", color = DeepNavyDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
