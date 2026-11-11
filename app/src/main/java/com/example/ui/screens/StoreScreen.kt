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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

data class StoreAvatarItem(val emoji: String, val name: String, val costCoins: Int)

@Composable
fun StoreScreen(
  profile: PlayerProfile,
  onWatchAdForCoins: () -> Unit,
  onUnlockAvatar: (emoji: String, cost: Int) -> Unit,
  onSelectAvatar: (emoji: String) -> Unit,
  onBackClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  val avatarCatalog = listOf(
    StoreAvatarItem("⚡", "Electric Spark", 0),
    StoreAvatarItem("🧠", "Mega Cortex", 0),
    StoreAvatarItem("🎯", "Bullseye Master", 0),
    StoreAvatarItem("🧩", "Enigma Solver", 0),
    StoreAvatarItem("👑", "Grand Monarch", 100),
    StoreAvatarItem("🕵️", "Sherlock Hat", 120),
    StoreAvatarItem("🚀", "Cosmic Voyager", 150),
    StoreAvatarItem("🔥", "Inferno Flame", 180),
    StoreAvatarItem("💎", "Diamond Mind", 200),
    StoreAvatarItem("🦾", "Cyborg Logic", 250),
    StoreAvatarItem("🦊", "Clever Fox", 300),
    StoreAvatarItem("🦉", "Wise Owl", 350)
  )

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "REWARDS STORE",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = null,
      onSoundToggle = onSoundToggle
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState)
        .padding(16.dp)
    ) {
      // Free Coins Rewarded Ad Banner
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.5.dp, SolarGold.copy(alpha = 0.6f)),
        modifier = Modifier
          .testTag("store_watch_ad_card")
          .fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(46.dp)
                  .clip(CircleShape)
                  .background(SolarGold.copy(alpha = 0.2f))
              ) {
                Text(text = "🪙", fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "FREE COINS",
                  color = SolarGold,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.ExtraBold,
                  letterSpacing = 1.sp
                )
                Text(
                  text = "Watch official test ad for +50 Brain Coins",
                  color = TextSecondary,
                  fontSize = 12.sp
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = onWatchAdForCoins,
            colors = ButtonDefaults.buttonColors(containerColor = SolarGold),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .testTag("watch_ad_for_50_coins_button")
              .fillMaxWidth()
              .height(48.dp)
          ) {
            Icon(imageVector = Icons.Default.Tv, contentDescription = "Watch Ad", tint = DeepNavyDark)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "WATCH TEST AD (+50 🪙)",
              color = DeepNavyDark,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "COSMETIC AVATARS",
        color = TextPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Avatar Grid
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        avatarCatalog.chunked(2).forEach { rowItems ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            rowItems.forEach { item ->
              val isUnlocked = item.emoji in profile.unlockedAvatars
              val isSelected = profile.selectedAvatar == item.emoji
              val canAfford = profile.coins >= item.costCoins

              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                  containerColor = if (isSelected) DeepNavyCard else DeepNavySurface
                ),
                border = BorderStroke(
                  1.dp,
                  when {
                    isSelected -> ElectricCyan
                    isUnlocked -> DeepNavyCardBorder
                    else -> DeepNavyCardBorder.copy(alpha = 0.5f)
                  }
                ),
                modifier = Modifier
                  .weight(1f)
                  .height(115.dp)
                  .clickable {
                    if (isUnlocked) {
                      onSelectAvatar(item.emoji)
                    } else if (canAfford) {
                      onUnlockAvatar(item.emoji, item.costCoins)
                    }
                  }
              ) {
                Column(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = item.emoji, fontSize = 32.sp)

                  Text(
                    text = item.name,
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                  )

                  when {
                    isSelected -> {
                      Text(text = "EQUIPPED", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    isUnlocked -> {
                      Text(text = "EQUIP", color = NeonEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    else -> {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                          text = "🪙 ${item.costCoins}",
                          color = if (canAfford) SolarGold else TextMuted,
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Bold
                        )
                      }
                    }
                  }
                }
              }
            }
            if (rowItems.size == 1) {
              Spacer(modifier = Modifier.weight(1f))
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    AdBannerComposable()
  }
}
