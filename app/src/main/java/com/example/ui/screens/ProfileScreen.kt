package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlayerProfile
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
  profile: PlayerProfile,
  onUpdateUsername: (String) -> Unit,
  onSelectAvatar: (String) -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  val scrollState = rememberScrollState()
  var editingName by remember { mutableStateOf(false) }
  var nameInput by remember(profile.username) { mutableStateOf(profile.username) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "PLAYER PROFILE",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState)
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Big Avatar & Badge
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(88.dp)
          .clip(CircleShape)
          .background(DeepNavyCard)
          .border(2.5.dp, ElectricCyan, CircleShape)
      ) {
        Text(text = profile.selectedAvatar, fontSize = 48.sp)
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = profile.username,
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(4.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = profile.rank.badgeEmoji, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = profile.rank.title,
          color = ElectricCyan,
          fontSize = 15.sp,
          fontWeight = FontWeight.ExtraBold
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Rank XP Progress
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.dp, DeepNavyCardBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Current: ${profile.xp} XP", color = TextSecondary, fontSize = 12.sp)
            Text(
              text = profile.nextRank?.let { "Next: ${it.minXp} XP (${it.title})" } ?: "Max Rank",
              color = SolarGold,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          LinearProgressIndicator(
            progress = { profile.rankProgress },
            color = ElectricCyan,
            trackColor = DeepNavyCard,
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp))
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "LIFETIME CAREER STATS",
        color = TextPrimary,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.align(Alignment.Start)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Stats 2x3 Grid
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard("Puzzles Solved", "${profile.totalSolvedCount}", "🧩", Modifier.weight(1f))
          StatCard("Highest Level", "Lvl ${profile.currentLevel}", "🏆", Modifier.weight(1f))
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard("Stars Earned", "${profile.totalStars} ⭐", "⭐", Modifier.weight(1f))
          StatCard("Max Combo Streak", "${profile.bestStreak}x", "🔥", Modifier.weight(1f))
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard("Daily Streak", "${profile.streakDays} Days", "⚡", Modifier.weight(1f))
          StatCard("Best Time Attack", "${profile.bestTimeAttackScore} pts", "⏱️", Modifier.weight(1f))
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Unlocked Avatars Selector
      Text(
        text = "YOUR UNLOCKED AVATARS",
        color = TextPrimary,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.align(Alignment.Start)
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        profile.unlockedAvatars.forEach { avatar ->
          val isEquipped = profile.selectedAvatar == avatar
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(50.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(if (isEquipped) DeepNavyCard else DeepNavySurface)
              .border(
                1.5.dp,
                if (isEquipped) ElectricCyan else DeepNavyCardBorder,
                RoundedCornerShape(12.dp)
              )
              .clickable { onSelectAvatar(avatar) }
          ) {
            Text(text = avatar, fontSize = 24.sp)
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    AdBannerComposable()
  }
}

@Composable
private fun StatCard(label: String, value: String, emoji: String, modifier: Modifier = Modifier) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
    border = BorderStroke(1.dp, DeepNavyCardBorder),
    modifier = modifier.height(78.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = value, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = label, color = TextSecondary, fontSize = 11.sp)
    }
  }
}
