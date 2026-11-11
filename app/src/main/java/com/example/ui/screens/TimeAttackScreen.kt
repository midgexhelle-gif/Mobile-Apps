package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.audio.SoundManager
import com.example.data.PuzzleLibrary
import com.example.model.PlayerProfile
import com.example.model.Puzzle
import com.example.ui.components.AnimatedTimerBar
import com.example.ui.components.GameTopBar
import com.example.ui.components.PuzzleCardView
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun TimeAttackScreen(
  profile: PlayerProfile,
  soundManager: SoundManager,
  onGameOver: (score: Int, correctCount: Int) -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  var timeRemaining by remember { mutableIntStateOf(60) }
  var totalScore by remember { mutableIntStateOf(0) }
  var comboStreak by remember { mutableIntStateOf(0) }
  var bestCombo by remember { mutableIntStateOf(0) }
  var correctCount by remember { mutableIntStateOf(0) }
  var isGameOver by remember { mutableStateOf(false) }

  val shuffledPool = remember { PuzzleLibrary.allPuzzles.shuffled() }
  var puzzleIndex by remember { mutableIntStateOf(0) }
  val activePuzzle = shuffledPool.getOrNull(puzzleIndex % shuffledPool.size)

  var selectedOption by remember(activePuzzle?.id) { mutableStateOf<Int?>(null) }
  var isSubmitted by remember(activePuzzle?.id) { mutableStateOf(false) }

  // 60-Second Master Countdown
  LaunchedEffect(isGameOver) {
    if (!isGameOver) {
      while (timeRemaining > 0) {
        delay(1000)
        timeRemaining--
        if (timeRemaining in 1..5) {
          soundManager.playTimerTick(profile.soundEnabled)
        }
      }
      isGameOver = true
      soundManager.playWrongSound(profile.soundEnabled)
      onGameOver(totalScore, correctCount)
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "TIME ATTACK",
      coins = profile.coins,
      streakDays = comboStreak,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    if (!isGameOver && activePuzzle != null) {
      // Timer Bar
      AnimatedTimerBar(timeRemainingSeconds = timeRemaining, totalTimeSeconds = 60)

      // Score & Combo HUD
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "SCORE: ", color = TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
          Text(text = "$totalScore", color = ElectricCyan, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }

        if (comboStreak > 1) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(FlameOrange)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(
              text = "COMBO x$comboStreak (+${comboStreak * 25} pts)",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      PuzzleCardView(
        puzzle = activePuzzle,
        selectedIndex = selectedOption,
        isSubmitted = isSubmitted,
        onOptionSelected = { index ->
          if (!isSubmitted) {
            selectedOption = index
            isSubmitted = true
            val correct = index == activePuzzle.correctOptionIndex

            if (correct) {
              soundManager.playCorrectSound(profile.soundEnabled)
              soundManager.vibrateSuccess(profile.hapticsEnabled)
              comboStreak++
              bestCombo = maxOf(bestCombo, comboStreak)
              correctCount++
              val points = 100 + (comboStreak * 25)
              totalScore += points
            } else {
              soundManager.playWrongSound(profile.soundEnabled)
              soundManager.vibrateError(profile.hapticsEnabled)
              comboStreak = 0
            }

            // In Time Attack, quickly advance to next puzzle
            puzzleIndex++
          }
        }
      )
    } else {
      // GAME OVER SUMMARY SCREEN
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "⏰ TIME EXPIRED!",
          color = NeonCoral,
          fontSize = 28.sp,
          fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(20.dp))

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
            Text(text = "FINAL SCORE", color = TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "$totalScore",
              color = SolarGold,
              fontSize = 38.sp,
              fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$correctCount", color = NeonEmerald, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Puzzles Solved", color = TextMuted, fontSize = 11.sp)
              }
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$bestCombo", color = FlameOrange, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Max Combo", color = TextMuted, fontSize = 11.sp)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = {
            timeRemaining = 60
            totalScore = 0
            comboStreak = 0
            correctCount = 0
            isGameOver = false
            puzzleIndex = 0
          },
          colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .testTag("play_again_time_attack_button")
            .fillMaxWidth()
            .height(52.dp)
        ) {
          Icon(imageVector = Icons.Default.Refresh, contentDescription = "Play Again", tint = DeepNavyDark)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "PLAY AGAIN (60s)", color = DeepNavyDark, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
          onClick = onBackClick,
          colors = ButtonDefaults.buttonColors(containerColor = DeepNavyCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, DeepNavyCardBorder),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ) {
          Text(text = "BACK TO MENU", color = TextPrimary, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
