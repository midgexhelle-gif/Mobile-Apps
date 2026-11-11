package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Achievement
import com.example.model.PlayerProfile
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.DeepNavySurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AchievementsScreen(
  achievements: List<Achievement>,
  profile: PlayerProfile,
  onClaimReward: (achievementId: String, rewardCoins: Int) -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  val completedCount = achievements.count { it.isCompleted }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "ACHIEVEMENTS ($completedCount/${achievements.size})",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    LazyColumn(
      modifier = Modifier
        .testTag("achievements_list")
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(achievements) { achievement ->
        AchievementCardItem(
          achievement = achievement,
          onClaim = { onClaimReward(achievement.id, achievement.rewardCoins) }
        )
      }
    }

    AdBannerComposable()
  }
}

@Composable
private fun AchievementCardItem(
  achievement: Achievement,
  onClaim: () -> Unit
) {
  val isCompleted = achievement.isCompleted
  val isClaimed = achievement.isClaimed
  val fraction = (achievement.currentProgress.toFloat() / achievement.target.toFloat()).coerceIn(0f, 1f)

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
    border = BorderStroke(
      1.dp,
      when {
        isClaimed -> DeepNavyCardBorder
        isCompleted -> NeonEmerald
        else -> DeepNavyCardBorder
      }
    ),
    modifier = Modifier
      .testTag("achievement_card_${achievement.id}")
      .fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(DeepNavyCard)
              .border(1.dp, if (isCompleted) NeonEmerald else DeepNavyCardBorder, CircleShape)
          ) {
            Text(text = achievement.emoji, fontSize = 22.sp)
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = achievement.title,
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = achievement.description,
              color = TextSecondary,
              fontSize = 12.sp
            )
          }
        }

        // Claim button or status
        when {
          isClaimed -> {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(DeepNavyCard)
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(text = "CLAIMED", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
          isCompleted -> {
            Button(
              onClick = onClaim,
              colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("claim_btn_${achievement.id}")
            ) {
              Text(
                text = "CLAIM +${achievement.rewardCoins}🪙",
                color = DeepNavyDark,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
              )
            }
          }
          else -> {
            Text(
              text = "+${achievement.rewardCoins} 🪙",
              color = SolarGold,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Progress bar and counter
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "${achievement.currentProgress.coerceAtMost(achievement.target)} / ${achievement.target}",
          color = TextMuted,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = "${(fraction * 100).toInt()}%",
          color = if (isCompleted) NeonEmerald else ElectricCyan,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      LinearProgressIndicator(
        progress = { fraction },
        color = if (isCompleted) NeonEmerald else ElectricCyan,
        trackColor = DeepNavyCard,
        modifier = Modifier
          .fillMaxWidth()
          .height(5.dp)
          .clip(RoundedCornerShape(3.dp))
      )
    }
  }
}
