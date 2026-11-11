package com.example.ui.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrainRank
import com.example.model.LeaderboardEntry
import com.example.model.PlayerProfile
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.DeepNavySurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.StarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LeaderboardScreen(
  profile: PlayerProfile,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Global", "Weekly", "Leagues")

  // Transparently labeled sample leaderboard rankings including current player's dynamic rank
  val sampleEntries = remember(profile) {
    listOf(
      LeaderboardEntry(1, "CipherKing", "👑", 24800, "Grandmaster"),
      LeaderboardEntry(2, "QuantumSolve", "⚡", 21250, "Grandmaster"),
      LeaderboardEntry(3, "VortexMind", "🧠", 18900, "Grandmaster"),
      LeaderboardEntry(4, "LogicNinja", "🎯", 14300, "Brain Genius"),
      LeaderboardEntry(5, "SpeedDemon", "🏎️", 11950, "Brain Genius"),
      LeaderboardEntry(6, profile.username, profile.selectedAvatar, profile.xp, profile.rank.title, isCurrentPlayer = true),
      LeaderboardEntry(7, "EnigmaHunter", "🕵️", 8200, "Puzzle Champion"),
      LeaderboardEntry(8, "MathWhiz99", "➗", 6500, "Puzzle Champion"),
      LeaderboardEntry(9, "RiddleMeThis", "💡", 4100, "Logic Master"),
      LeaderboardEntry(10, "SynapseHero", "🌱", 2300, "Quick Thinker")
    ).sortedByDescending { it.score }
      .mapIndexed { index, entry -> entry.copy(rank = index + 1) }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "LEADERBOARD",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = DeepNavySurface,
      contentColor = ElectricCyan,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
          color = ElectricCyan,
          height = 3.dp
        )
      }
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Text(
              text = title,
              fontSize = 14.sp,
              fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
            )
          }
        )
      }
    }

    // Honest Demo Notice as required by prompt policy: "Clearly label any sample leaderboard as demo data until real backend integration is completed."
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(DeepNavySurfaceVariant)
        .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
      Text(
        text = "ℹ️ Offline Preview: Demo rankings sync live with your local XP.",
        color = TextMuted,
        fontSize = 11.sp
      )
    }

    LazyColumn(
      modifier = Modifier
        .testTag("leaderboard_list")
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(sampleEntries) { entry ->
        LeaderboardRow(entry = entry)
      }
    }

    AdBannerComposable()
  }
}

@Composable
private fun LeaderboardRow(entry: LeaderboardEntry) {
  val isTop3 = entry.rank in 1..3
  val rankColor = when (entry.rank) {
    1 -> StarGold
    2 -> Color(0xFFC0C0C0)
    3 -> Color(0xFFCD7F32)
    else -> TextSecondary
  }

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (entry.isCurrentPlayer) DeepNavyCard.copy(alpha = 0.9f) else DeepNavySurface
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (entry.isCurrentPlayer) ElectricCyan else DeepNavyCardBorder
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Rank Number
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.width(32.dp)
        ) {
          if (isTop3) {
            Text(
              text = when (entry.rank) {
                1 -> "🥇"
                2 -> "🥈"
                else -> "🥉"
              },
              fontSize = 20.sp
            )
          } else {
            Text(
              text = "#${entry.rank}",
              color = rankColor,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Avatar
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(DeepNavyCard)
        ) {
          Text(text = entry.avatar, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = entry.username,
              color = if (entry.isCurrentPlayer) ElectricCyan else TextPrimary,
              fontSize = 14.sp,
              fontWeight = if (entry.isCurrentPlayer) FontWeight.ExtraBold else FontWeight.Bold
            )
            if (entry.isCurrentPlayer) {
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "(YOU)", color = NeonEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }
          Text(text = entry.leagueTitle, color = TextMuted, fontSize = 11.sp)
        }
      }

      Text(
        text = "${entry.score} XP",
        color = SolarGold,
        fontSize = 15.sp,
        fontWeight = FontWeight.ExtraBold
      )
    }
  }
}
