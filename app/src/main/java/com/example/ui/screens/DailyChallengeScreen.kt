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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import java.time.LocalDate

@Composable
fun DailyChallengeScreen(
  profile: PlayerProfile,
  completedDates: Set<Long>,
  onStartDailyPuzzles: () -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  val todayEpoch = try {
    LocalDate.now().toEpochDay()
  } catch (_: Exception) {
    System.currentTimeMillis() / (1000 * 60 * 60 * 24)
  }

  val isTodayCompleted = todayEpoch in completedDates
  val dailyPuzzles = remember(todayEpoch) {
    PuzzleLibrary.getDailyChallengePuzzles(todayEpoch)
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "DAILY CHALLENGE",
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
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Today's Date Banner
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(FlameOrange.copy(alpha = 0.2f))
            .border(1.dp, FlameOrange.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Today, contentDescription = "Date", tint = FlameOrange, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "PUZZLE SET OF THE DAY",
              color = FlameOrange,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Big Status Card
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
          border = androidx.compose.foundation.BorderStroke(1.dp, DeepNavyCardBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = if (isTodayCompleted) "🏆 COMPLETED FOR TODAY!" else "5 EXCLUSIVE DAILY PUZZLES",
              color = if (isTodayCompleted) NeonEmerald else SolarGold,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = if (isTodayCompleted)
                "Awesome job! Come back tomorrow to keep your ${profile.streakDays}-day streak burning."
              else
                "Solve all 5 puzzles today to earn +50 Brain Coins and increase your Daily Streak!",
              color = TextSecondary,
              fontSize = 13.sp,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5 Puzzle Category Previews
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              dailyPuzzles.forEachIndexed { i, p ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                      .size(42.dp)
                      .clip(CircleShape)
                      .background(if (isTodayCompleted) NeonEmerald.copy(alpha = 0.2f) else DeepNavyCard)
                      .border(1.dp, if (isTodayCompleted) NeonEmerald else DeepNavyCardBorder, CircleShape)
                  ) {
                    if (isTodayCompleted) {
                      Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Done", tint = NeonEmerald, modifier = Modifier.size(20.dp))
                    } else {
                      Text(text = p.category.emoji, fontSize = 18.sp)
                    }
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(text = "#${i + 1}", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }

      // Bottom Start Action
      Button(
        onClick = onStartDailyPuzzles,
        enabled = !isTodayCompleted,
        colors = ButtonDefaults.buttonColors(
          containerColor = FlameOrange,
          disabledContainerColor = DeepNavyCard
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .testTag("start_daily_challenge_button")
          .fillMaxWidth()
          .height(54.dp)
      ) {
        if (isTodayCompleted) {
          Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Finished", tint = TextMuted)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "COMPLETED (+50 COINS EARNED)", color = TextMuted, fontWeight = FontWeight.Bold)
        } else {
          Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start", tint = Color.White)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "START TODAY'S CHALLENGE", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }
      }
    }

    AdBannerComposable()
  }
}
