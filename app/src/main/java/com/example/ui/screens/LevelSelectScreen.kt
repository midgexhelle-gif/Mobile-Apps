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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import com.example.model.Difficulty
import com.example.model.LevelInfo
import com.example.model.PlayerProfile
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.DeepNavySurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.StarEmpty
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LevelSelectScreen(
  levels: List<LevelInfo>,
  profile: PlayerProfile,
  onLevelSelected: (Int) -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  var selectedTabTier by remember { mutableIntStateOf(0) }
  val tiers = Difficulty.entries

  val filteredLevels = remember(selectedTabTier, levels) {
    val diff = tiers[selectedTabTier]
    levels.filter { it.difficulty == diff }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "CAMPAIGN (100)",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    // Tier Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTabTier,
      containerColor = DeepNavySurface,
      contentColor = ElectricCyan,
      edgePadding = 16.dp,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabTier]),
          color = ElectricCyan,
          height = 3.dp
        )
      }
    ) {
      tiers.forEachIndexed { index, difficulty ->
        Tab(
          selected = selectedTabTier == index,
          onClick = { selectedTabTier = index },
          text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = difficulty.displayName,
                fontWeight = if (selectedTabTier == index) FontWeight.Bold else FontWeight.Normal,
                fontSize = 13.sp
              )
              Text(
                text = difficulty.levelRange,
                fontSize = 10.sp,
                color = if (selectedTabTier == index) ElectricCyan else TextMuted
              )
            }
          }
        )
      }
    }

    // Grid of Level Cards
    LazyVerticalGrid(
      columns = GridCells.Fixed(4),
      modifier = Modifier
        .testTag("level_select_grid")
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(filteredLevels) { level ->
        LevelGridItem(
          level = level,
          onClick = {
            if (level.isUnlocked) {
              onLevelSelected(level.levelNumber)
            }
          }
        )
      }
    }

    AdBannerComposable()
  }
}

@Composable
private fun LevelGridItem(
  level: LevelInfo,
  onClick: () -> Unit
) {
  val isUnlocked = level.isUnlocked
  val isCompleted = level.isCompleted

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = when {
        isCompleted -> DeepNavySurfaceVariant
        isUnlocked -> DeepNavySurface
        else -> DeepNavyDark
      }
    ),
    border = BorderStroke(
      1.dp,
      when {
        isCompleted -> NeonEmerald.copy(alpha = 0.5f)
        isUnlocked -> ElectricCyan.copy(alpha = 0.5f)
        else -> DeepNavyCardBorder.copy(alpha = 0.3f)
      }
    ),
    modifier = Modifier
      .testTag("level_item_${level.levelNumber}")
      .height(96.dp)
      .clickable(enabled = isUnlocked) { onClick() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(6.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Level Number or Lock
      if (isUnlocked) {
        Text(
          text = "${level.levelNumber}",
          color = if (isCompleted) NeonEmerald else TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold
        )
      } else {
        Icon(
          imageVector = Icons.Default.Lock,
          contentDescription = "Locked",
          tint = TextMuted,
          modifier = Modifier.size(20.dp)
        )
      }

      // Stars earned (1-3)
      if (isUnlocked) {
        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          for (i in 1..3) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Star",
              tint = if (i <= level.starsEarned) SolarGold else StarEmpty,
              modifier = Modifier.size(12.dp)
            )
          }
        }
      } else {
        Text(
          text = "Need ${level.requiredStars}⭐",
          color = TextMuted,
          fontSize = 9.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}
