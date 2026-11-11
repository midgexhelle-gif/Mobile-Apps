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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlayerProfile
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.CosmicPurple
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.DeepNavySurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanGlow
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
  profile: PlayerProfile,
  onPlayNowClick: () -> Unit,
  onDailyChallengeClick: () -> Unit,
  onTimeAttackClick: () -> Unit,
  onCategoriesClick: () -> Unit,
  onLeaderboardClick: () -> Unit,
  onAchievementsClick: () -> Unit,
  onStoreClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onProfileClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    // Top Bar
    GameTopBar(
      title = "BRAIN BLITZ",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onCoinClick = onStoreClick,
      onSoundToggle = onSoundToggle
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
      // Player Quick Profile Banner
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.dp, DeepNavyCardBorder),
        modifier = Modifier
          .testTag("home_player_card")
          .fillMaxWidth()
          .clickable { onProfileClick() }
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Avatar
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(
                  Brush.linearGradient(
                    listOf(ElectricCyan, CosmicPurple)
                  )
                )
                .border(2.dp, ElectricCyan, CircleShape)
            ) {
              Text(text = profile.selectedAvatar, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = profile.username,
                  color = TextPrimary,
                  fontSize = 17.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = profile.rank.badgeEmoji,
                  fontSize = 14.sp
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "${profile.rank.title} • Level ${profile.currentLevel}",
                color = ElectricCyan,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
              )
            }

            // Stars Pill
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(DeepNavyCard)
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(text = "⭐", fontSize = 12.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${profile.totalStars}",
                color = SolarGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Rank XP Progress Bar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "XP: ${profile.xp}",
              color = TextSecondary,
              fontSize = 11.sp
            )
            Text(
              text = profile.nextRank?.let { "Next: ${it.title} (${it.minXp} XP)" } ?: "Max Rank",
              color = TextMuted,
              fontSize = 11.sp
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          LinearProgressIndicator(
            progress = { profile.rankProgress },
            color = ElectricCyan,
            trackColor = DeepNavyCard,
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp))
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // HERO CALL TO ACTION: PLAY NOW
      Button(
        onClick = onPlayNowClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
          .testTag("play_now_button")
          .fillMaxWidth()
          .height(72.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(
            Brush.horizontalGradient(
              listOf(Color(0xFF00E5FF), Color(0xFF2563EB))
            )
          )
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
                .size(44.dp)
                .clip(CircleShape)
                .background(DeepNavyDark.copy(alpha = 0.3f))
            ) {
              Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
              Text(
                text = "PLAY CAMPAIGN",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
              )
              Text(
                text = "Continue Level ${profile.currentLevel} of 100",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 12.sp
              )
            }
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Go",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // FEATURED DAILY CHALLENGE CARD
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.dp, FlameOrange.copy(alpha = 0.5f)),
        modifier = Modifier
          .testTag("featured_daily_card")
          .fillMaxWidth()
          .clickable { onDailyChallengeClick() }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(FlameOrange.copy(alpha = 0.2f))
            ) {
              Text(text = "☀️", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "DAILY CHALLENGE",
                  color = FlameOrange,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.ExtraBold,
                  letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "+50 🪙",
                  color = SolarGold,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "5 fresh puzzles • Daily streak bonus",
                color = TextSecondary,
                fontSize = 12.sp
              )
            }
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Open Daily",
            tint = FlameOrange,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2x2 GAME MODE GRID
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        GameModeCard(
          title = "TIME ATTACK",
          subtitle = "Beat the 60s Clock",
          icon = Icons.Default.Speed,
          accentColor = NeonCoral,
          emoji = "⚡",
          modifier = Modifier.weight(1f),
          testTag = "home_time_attack_card",
          onClick = onTimeAttackClick
        )

        GameModeCard(
          title = "CATEGORIES",
          subtitle = "15 Puzzle Domains",
          icon = Icons.Default.Category,
          accentColor = ElectricCyan,
          emoji = "🧩",
          modifier = Modifier.weight(1f),
          testTag = "home_categories_card",
          onClick = onCategoriesClick
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        GameModeCard(
          title = "LEADERBOARD",
          subtitle = "Global & Leagues",
          icon = Icons.Default.Leaderboard,
          accentColor = SolarGold,
          emoji = "🏆",
          modifier = Modifier.weight(1f),
          testTag = "home_leaderboard_card",
          onClick = onLeaderboardClick
        )

        GameModeCard(
          title = "ACHIEVEMENTS",
          subtitle = "26 Badges & Coins",
          icon = Icons.Default.EmojiEvents,
          accentColor = NeonEmerald,
          emoji = "🎖️",
          modifier = Modifier.weight(1f),
          testTag = "home_achievements_card",
          onClick = onAchievementsClick
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        GameModeCard(
          title = "REWARDS STORE",
          subtitle = "Avatars & Coin Packs",
          icon = Icons.Default.ShoppingBag,
          accentColor = Color(0xFFD500F9),
          emoji = "💎",
          modifier = Modifier.weight(1f),
          testTag = "home_store_card",
          onClick = onStoreClick
        )

        GameModeCard(
          title = "SETTINGS",
          subtitle = "Audio & Privacy",
          icon = Icons.Default.Settings,
          accentColor = TextSecondary,
          emoji = "⚙️",
          modifier = Modifier.weight(1f),
          testTag = "home_settings_card",
          onClick = onSettingsClick
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    // Official AdMob Banner positioned at the bottom of the Home screen
    AdBannerComposable()
  }
}

@Composable
private fun GameModeCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  emoji: String,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
    border = BorderStroke(1.dp, DeepNavyCardBorder),
    modifier = modifier
      .testTag(testTag)
      .height(100.dp)
      .clickable { onClick() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = emoji, fontSize = 20.sp)
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(accentColor)
        )
      }

      Column {
        Text(
          text = title,
          color = TextPrimary,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
        Text(
          text = subtitle,
          color = TextSecondary,
          fontSize = 11.sp,
          maxLines = 1
        )
      }
    }
  }
}
