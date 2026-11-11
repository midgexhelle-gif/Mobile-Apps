package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.SoundManager
import com.example.model.PlayerProfile
import com.example.model.Puzzle
import com.example.ui.components.AnimatedTimerBar
import com.example.ui.components.GameTopBar
import com.example.ui.components.HintSheetModal
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
fun GamePlayScreen(
  levelNumber: Int,
  puzzles: List<Puzzle>,
  profile: PlayerProfile,
  soundManager: SoundManager,
  onPuzzleSolved: (puzzle: Puzzle, timeTakenSec: Int, isCorrect: Boolean) -> Unit,
  onLevelComplete: (starsEarned: Int, totalScore: Int, bonusCoins: Int) -> Unit,
  onUnlockHint: (tier: Int, cost: Int) -> Boolean,
  onWatchAdForHint: () -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit,
  onSoundToggle: () -> Unit
) {
  var currentPuzzleIndex by remember { mutableIntStateOf(0) }
  val currentPuzzle = puzzles.getOrNull(currentPuzzleIndex) ?: return

  var timeRemaining by remember(currentPuzzle.id) { mutableIntStateOf(currentPuzzle.timeLimitSeconds) }
  var isTimerPaused by remember { mutableStateOf(false) }
  var isSubmitted by remember(currentPuzzle.id) { mutableStateOf(false) }
  var selectedOptionIndex by remember(currentPuzzle.id) { mutableStateOf<Int?>(null) }
  var isCorrect by remember(currentPuzzle.id) { mutableStateOf(false) }

  var showHintModal by remember { mutableStateOf(false) }
  var showPauseDialog by remember { mutableStateOf(false) }
  var unlockedHintLevels by remember(currentPuzzle.id) { mutableStateOf(setOf<Int>()) }

  // Level aggregate tracking
  var levelCorrectCount by remember { mutableIntStateOf(0) }
  var levelTotalScore by remember { mutableIntStateOf(0) }

  // Countdown timer loop
  LaunchedEffect(currentPuzzle.id, isTimerPaused, isSubmitted) {
    if (!isSubmitted && !isTimerPaused) {
      while (timeRemaining > 0) {
        delay(1000)
        timeRemaining--
        if (timeRemaining in 1..5) {
          soundManager.playTimerTick(profile.soundEnabled)
        }
      }
      // Auto-submit when time expires if not already submitted
      if (timeRemaining <= 0 && !isSubmitted) {
        isSubmitted = true
        isCorrect = false
        soundManager.playWrongSound(profile.soundEnabled)
        soundManager.vibrateError(profile.hapticsEnabled)
        onPuzzleSolved(currentPuzzle, currentPuzzle.timeLimitSeconds, false)
      }
    }
  }

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    // Top Bar with Level Progress
    GameTopBar(
      title = "LEVEL $levelNumber (${currentPuzzleIndex + 1}/${puzzles.size})",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = { showPauseDialog = true },
      onCoinClick = onCoinClick,
      onSoundToggle = onSoundToggle
    )

    // Animated Timer Bar
    AnimatedTimerBar(
      timeRemainingSeconds = timeRemaining,
      totalTimeSeconds = currentPuzzle.timeLimitSeconds
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState)
        .padding(vertical = 8.dp)
    ) {
      // Main Puzzle Card
      PuzzleCardView(
        puzzle = currentPuzzle,
        selectedIndex = selectedOptionIndex,
        isSubmitted = isSubmitted,
        onOptionSelected = { index ->
          if (!isSubmitted) {
            selectedOptionIndex = index
            soundManager.vibrateTap(profile.hapticsEnabled)

            // Submit immediately on selection for snappy mobile gameplay
            isSubmitted = true
            val correct = index == currentPuzzle.correctOptionIndex
            isCorrect = correct
            val timeTaken = currentPuzzle.timeLimitSeconds - timeRemaining

            if (correct) {
              soundManager.playCorrectSound(profile.soundEnabled)
              soundManager.vibrateSuccess(profile.hapticsEnabled)
              levelCorrectCount++
              val score = currentPuzzle.pointValue + (if (timeTaken <= 10) 50 else 0)
              levelTotalScore += score
            } else {
              soundManager.playWrongSound(profile.soundEnabled)
              soundManager.vibrateError(profile.hapticsEnabled)
            }

            onPuzzleSolved(currentPuzzle, timeTaken, correct)
          }
        }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Answer Explanation / Solution Card (Shows after submission)
      AnimatedVisibility(
        visible = isSubmitted,
        enter = fadeIn() + slideInVertically()
      ) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) NeonEmerald.copy(alpha = 0.15f) else NeonCoral.copy(alpha = 0.15f)
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCorrect) NeonEmerald else NeonCoral
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = if (isCorrect) "🎉 EXCELLENT!" else "❌ NOT QUITE!",
                color = if (isCorrect) NeonEmerald else NeonCoral,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
              if (isCorrect) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "+${currentPuzzle.coinReward} 🪙",
                  color = SolarGold,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = currentPuzzle.explanation,
              color = TextPrimary,
              fontSize = 14.sp,
              lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Continue / Next Puzzle Button
            Button(
              onClick = {
                if (currentPuzzleIndex < puzzles.size - 1) {
                  currentPuzzleIndex++
                } else {
                  // Level finished! Calculate stars
                  val stars = when {
                    levelCorrectCount >= 5 -> 3
                    levelCorrectCount >= 3 -> 2
                    levelCorrectCount >= 1 -> 1
                    else -> 1
                  }
                  val bonusCoins = stars * 10
                  soundManager.playLevelComplete(profile.soundEnabled)
                  onLevelComplete(stars, levelTotalScore, bonusCoins)
                }
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isCorrect) NeonEmerald else ElectricCyan
              ),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .testTag("next_puzzle_button")
                .fillMaxWidth()
                .height(48.dp)
            ) {
              Text(
                text = if (currentPuzzleIndex < puzzles.size - 1) "NEXT PUZZLE" else "VIEW LEVEL RESULTS",
                color = DeepNavyDark,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next",
                tint = DeepNavyDark,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    // Bottom Action Bar: Hint Button & Pause
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(DeepNavySurface)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Pause Button
      IconButton(
        onClick = {
          isTimerPaused = true
          showPauseDialog = true
        },
        modifier = Modifier
          .testTag("game_pause_button")
          .size(44.dp)
          .clip(CircleShape)
          .background(DeepNavyCard)
      ) {
        Icon(
          imageVector = Icons.Default.Pause,
          contentDescription = "Pause Game",
          tint = TextPrimary
        )
      }

      // Hint Drawer Trigger Button
      Button(
        onClick = { showHintModal = true },
        enabled = !isSubmitted,
        colors = ButtonDefaults.buttonColors(
          containerColor = SolarGold,
          disabledContainerColor = DeepNavyCardBorder
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .testTag("game_hint_button")
          .height(44.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Lightbulb,
          contentDescription = "Hint",
          tint = DeepNavyDark,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "HINTS (${unlockedHintLevels.size}/3)",
          color = DeepNavyDark,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }

  // Hint Sheet Modal
  if (showHintModal) {
    HintSheetModal(
      puzzle = currentPuzzle,
      currentCoins = profile.coins,
      unlockedHintLevels = unlockedHintLevels,
      onUnlockWithCoins = { tier, cost ->
        if (onUnlockHint(tier, cost)) {
          unlockedHintLevels = unlockedHintLevels + tier
          soundManager.playCoinCollect(profile.soundEnabled)
        }
      },
      onWatchAdForHint = {
        showHintModal = false
        onWatchAdForHint()
      },
      onDismiss = { showHintModal = false }
    )
  }

  // Pause Modal
  if (showPauseDialog) {
    Dialog(onDismissRequest = {
      showPauseDialog = false
      isTimerPaused = false
    }) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(DeepNavySurface)
          .border(1.5.dp, DeepNavyCardBorder, RoundedCornerShape(20.dp))
          .padding(24.dp)
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "GAME PAUSED",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(20.dp))

          Button(
            onClick = {
              showPauseDialog = false
              isTimerPaused = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .testTag("resume_game_button")
              .fillMaxWidth()
              .height(48.dp)
          ) {
            Text(text = "RESUME", color = DeepNavyDark, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = {
              showPauseDialog = false
              onBackClick()
            },
            colors = ButtonDefaults.buttonColors(containerColor = DeepNavyCard),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .testTag("exit_game_button")
              .fillMaxWidth()
              .height(48.dp)
          ) {
            Text(text = "QUIT TO MENU", color = NeonCoral, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
