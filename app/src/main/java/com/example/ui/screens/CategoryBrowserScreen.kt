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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PuzzleLibrary
import com.example.model.PlayerProfile
import com.example.model.PuzzleCategory
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CategoryBrowserScreen(
  profile: PlayerProfile,
  onCategorySelected: (PuzzleCategory) -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  val allCategories = PuzzleCategory.entries

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "CATEGORIES (15)",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    LazyColumn(
      modifier = Modifier
        .testTag("categories_list")
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(allCategories) { category ->
        val count = PuzzleLibrary.getByCategory(category).size
        CategoryCardItem(
          category = category,
          puzzleCount = count,
          onClick = { onCategorySelected(category) }
        )
      }
    }

    AdBannerComposable()
  }
}

@Composable
private fun CategoryCardItem(
  category: PuzzleCategory,
  puzzleCount: Int,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
    border = BorderStroke(1.dp, DeepNavyCardBorder),
    modifier = Modifier
      .testTag("category_card_${category.id}")
      .fillMaxWidth()
      .clickable { onClick() }
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(category.primaryColor).copy(alpha = 0.2f))
            .border(1.dp, Color(category.primaryColor), RoundedCornerShape(12.dp))
        ) {
          Text(text = category.emoji, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
          Text(
            text = category.displayName,
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = category.shortDesc,
            color = TextSecondary,
            fontSize = 12.sp,
            maxLines = 1
          )
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "$puzzleCount puzzles",
          color = ElectricCyan,
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = "Open",
          tint = TextMuted,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}
