package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ads.AdManager
import com.example.ads.RewardType
import com.example.audio.SoundManager
import com.example.data.AchievementsData
import com.example.data.LevelConfig
import com.example.data.PuzzleLibrary
import com.example.data.GameStateRepository
import com.example.model.LevelInfo
import com.example.model.Puzzle
import com.example.model.PuzzleCategory
import com.example.ui.components.AdVideoOverlayModal
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.CategoryBrowserScreen
import com.example.ui.screens.DailyChallengeScreen
import com.example.ui.screens.GamePlayScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.LevelCompleteScreen
import com.example.ui.screens.LevelSelectScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StoreScreen
import com.example.ui.screens.TimeAttackScreen

sealed class Screen {
  object Home : Screen()
  object LevelSelect : Screen()
  data class GamePlay(val levelNumber: Int, val puzzles: List<Puzzle>) : Screen()
  data class LevelComplete(
    val levelNumber: Int,
    val starsEarned: Int,
    val score: Int,
    val bonusCoins: Int,
    val xpEarned: Int
  ) : Screen()
  object TimeAttack : Screen()
  object DailyChallenge : Screen()
  object Categories : Screen()
  data class CategoryPlay(val category: PuzzleCategory) : Screen()
  object Leaderboard : Screen()
  object Achievements : Screen()
  object Store : Screen()
  object Profile : Screen()
  object Settings : Screen()
}

@Composable
fun BrainBlitzApp(repository: GameStateRepository) {
  val context = LocalContext.current
  val soundManager = remember { SoundManager(context) }

  val profile by repository.profile.collectAsStateWithLifecycle()
  val unlockedLevels by repository.unlockedLevels.collectAsStateWithLifecycle()
  val completedLevels by repository.completedLevels.collectAsStateWithLifecycle()
  val levelStars by repository.levelStars.collectAsStateWithLifecycle()
  val levelScores by repository.levelScores.collectAsStateWithLifecycle()
  val claimedAchievements by repository.claimedAchievements.collectAsStateWithLifecycle()
  val categoriesPlayed by repository.categoriesPlayed.collectAsStateWithLifecycle()
  val codeBreakingSolved by repository.codeBreakingSolved.collectAsStateWithLifecycle()
  val fastSolvedCount by repository.fastSolvedCount.collectAsStateWithLifecycle()
  val dailyCompletedDates by repository.dailyCompletedDates.collectAsStateWithLifecycle()

  val activeAdState by AdManager.activeAd.collectAsStateWithLifecycle()

  // Navigation Stack
  val navStack = remember { mutableStateListOf<Screen>(Screen.Home) }
  val currentScreen = navStack.lastOrNull() ?: Screen.Home

  fun navigateTo(screen: Screen) {
    soundManager.vibrateTap(profile.hapticsEnabled)
    navStack.add(screen)
  }

  fun navigateBack() {
    soundManager.vibrateTap(profile.hapticsEnabled)
    if (navStack.size > 1) {
      navStack.removeAt(navStack.lastIndex)
    }
  }

  val allLevels: List<LevelInfo> = remember(unlockedLevels, completedLevels, levelStars, levelScores) {
    LevelConfig.generateLevels(unlockedLevels, completedLevels, levelStars, levelScores)
  }

  val threeStarLevelCount = remember(levelStars) {
    levelStars.values.count { it >= 3 }
  }

  val achievements = remember(
    profile, claimedAchievements, threeStarLevelCount,
    categoriesPlayed, dailyCompletedDates, codeBreakingSolved, fastSolvedCount
  ) {
    AchievementsData.buildAchievements(
      profile = profile,
      claimedIds = claimedAchievements,
      threeStarLevelCount = threeStarLevelCount,
      categoriesPlayedCount = categoriesPlayed.size,
      dailyCompletedCount = dailyCompletedDates.size,
      codeBreakingSolvedCount = codeBreakingSolved,
      fastSolvedCount = fastSolvedCount
    )
  }

  // Handle Android Back button
  if (currentScreen !is Screen.Home) {
    BackHandler {
      navigateBack()
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    Crossfade(targetState = currentScreen, label = "screen_crossfade") { screen ->
      when (screen) {
        is Screen.Home -> {
          HomeScreen(
            profile = profile,
            onPlayNowClick = {
              val targetLevel = allLevels.firstOrNull { it.levelNumber == profile.currentLevel }
                ?: allLevels.first()
              val puzzles = targetLevel.puzzleIds.mapNotNull { PuzzleLibrary.getById(it) }
                .ifEmpty { PuzzleLibrary.allPuzzles.take(5) }
              navigateTo(Screen.GamePlay(targetLevel.levelNumber, puzzles))
            },
            onDailyChallengeClick = { navigateTo(Screen.DailyChallenge) },
            onTimeAttackClick = { navigateTo(Screen.TimeAttack) },
            onCategoriesClick = { navigateTo(Screen.Categories) },
            onLeaderboardClick = { navigateTo(Screen.Leaderboard) },
            onAchievementsClick = { navigateTo(Screen.Achievements) },
            onStoreClick = { navigateTo(Screen.Store) },
            onSettingsClick = { navigateTo(Screen.Settings) },
            onProfileClick = { navigateTo(Screen.Profile) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.LevelSelect -> {
          LevelSelectScreen(
            levels = allLevels,
            profile = profile,
            onLevelSelected = { levelNum ->
              val lvl = allLevels.firstOrNull { it.levelNumber == levelNum } ?: allLevels.first()
              val puzzles = lvl.puzzleIds.mapNotNull { PuzzleLibrary.getById(it) }
                .ifEmpty { PuzzleLibrary.allPuzzles.take(5) }
              navigateTo(Screen.GamePlay(lvl.levelNumber, puzzles))
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.GamePlay -> {
          GamePlayScreen(
            levelNumber = screen.levelNumber,
            puzzles = screen.puzzles,
            profile = profile,
            soundManager = soundManager,
            onPuzzleSolved = { puzzle, timeTaken, isCorrect ->
              repository.recordPuzzleSolved(puzzle.id, puzzle.category.id, timeTaken, isCorrect)
            },
            onLevelComplete = { stars, score, bonusCoins ->
              repository.completeLevel(screen.levelNumber, stars, score, bonusCoins)
              // Optionally show interstitial ad between completed levels
              AdManager.showInterstitialAd {
                navigateTo(
                  Screen.LevelComplete(
                    levelNumber = screen.levelNumber,
                    starsEarned = stars,
                    score = score,
                    bonusCoins = bonusCoins,
                    xpEarned = stars * 50
                  )
                )
              }
            },
            onUnlockHint = { tier, cost ->
              repository.spendCoins(cost)
            },
            onWatchAdForHint = {
              AdManager.showRewardedAd(
                rewardType = RewardType.EXTRA_HINT,
                onRewardEarned = {
                  soundManager.playCoinCollect(profile.soundEnabled)
                }
              )
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.LevelComplete -> {
          LevelCompleteScreen(
            levelNumber = screen.levelNumber,
            starsEarned = screen.starsEarned,
            score = screen.score,
            bonusCoins = screen.bonusCoins,
            xpEarned = screen.xpEarned,
            onNextLevelClick = {
              val nextNum = screen.levelNumber + 1
              val nextLvl = allLevels.firstOrNull { it.levelNumber == nextNum }
              if (nextLvl != null) {
                val nextPuzzles = nextLvl.puzzleIds.mapNotNull { PuzzleLibrary.getById(it) }
                  .ifEmpty { PuzzleLibrary.allPuzzles.take(5) }
                navStack.clear()
                navStack.add(Screen.Home)
                navStack.add(Screen.GamePlay(nextNum, nextPuzzles))
              } else {
                navStack.clear()
                navStack.add(Screen.Home)
              }
            },
            onReplayClick = {
              val lvl = allLevels.firstOrNull { it.levelNumber == screen.levelNumber } ?: allLevels.first()
              val puzzles = lvl.puzzleIds.mapNotNull { PuzzleLibrary.getById(it) }
                .ifEmpty { PuzzleLibrary.allPuzzles.take(5) }
              navStack.removeAt(navStack.lastIndex)
              navStack.add(Screen.GamePlay(screen.levelNumber, puzzles))
            },
            onHomeClick = {
              navStack.clear()
              navStack.add(Screen.Home)
            }
          )
        }

        is Screen.TimeAttack -> {
          TimeAttackScreen(
            profile = profile,
            soundManager = soundManager,
            onGameOver = { score, correctCount ->
              repository.recordTimeAttackScore(score)
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.DailyChallenge -> {
          DailyChallengeScreen(
            profile = profile,
            completedDates = dailyCompletedDates,
            onStartDailyPuzzles = {
              val todayEpoch = try {
                java.time.LocalDate.now().toEpochDay()
              } catch (_: Exception) {
                System.currentTimeMillis() / (1000 * 60 * 60 * 24)
              }
              val dailyPuzzles = PuzzleLibrary.getDailyChallengePuzzles(todayEpoch)
              navigateTo(Screen.GamePlay(levelNumber = 999, puzzles = dailyPuzzles))
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.Categories -> {
          CategoryBrowserScreen(
            profile = profile,
            onCategorySelected = { cat ->
              val catPuzzles = PuzzleLibrary.getByCategory(cat).ifEmpty { PuzzleLibrary.allPuzzles.take(5) }
              navigateTo(Screen.GamePlay(levelNumber = 0, puzzles = catPuzzles))
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.CategoryPlay -> {
          val puzzles = PuzzleLibrary.getByCategory(screen.category).ifEmpty { PuzzleLibrary.allPuzzles.take(5) }
          GamePlayScreen(
            levelNumber = 0,
            puzzles = puzzles,
            profile = profile,
            soundManager = soundManager,
            onPuzzleSolved = { puzzle, timeTaken, isCorrect ->
              repository.recordPuzzleSolved(puzzle.id, puzzle.category.id, timeTaken, isCorrect)
            },
            onLevelComplete = { stars, score, bonusCoins ->
              navigateBack()
            },
            onUnlockHint = { tier, cost -> repository.spendCoins(cost) },
            onWatchAdForHint = {
              AdManager.showRewardedAd(
                rewardType = RewardType.EXTRA_HINT,
                onRewardEarned = { soundManager.playCoinCollect(profile.soundEnabled) }
              )
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.Leaderboard -> {
          LeaderboardScreen(
            profile = profile,
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.Achievements -> {
          AchievementsScreen(
            achievements = achievements,
            profile = profile,
            onClaimReward = { achId, coins ->
              repository.claimAchievement(achId, coins)
              soundManager.playAchievementUnlocked(profile.soundEnabled)
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.Store -> {
          StoreScreen(
            profile = profile,
            onWatchAdForCoins = {
              AdManager.showRewardedAd(
                rewardType = RewardType.FREE_COINS,
                onRewardEarned = {
                  repository.addCoins(50)
                  soundManager.playCoinCollect(profile.soundEnabled)
                }
              )
            },
            onUnlockAvatar = { emoji, cost ->
              if (repository.spendCoins(cost)) {
                val newUnlocked = profile.unlockedAvatars + emoji
                repository.updateProfile(profile.copy(unlockedAvatars = newUnlocked, selectedAvatar = emoji))
                soundManager.playCoinCollect(profile.soundEnabled)
              }
            },
            onSelectAvatar = { emoji ->
              repository.updateProfile(profile.copy(selectedAvatar = emoji))
            },
            onBackClick = { navigateBack() },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.Profile -> {
          ProfileScreen(
            profile = profile,
            onUpdateUsername = { newName ->
              repository.updateProfile(profile.copy(username = newName))
            },
            onSelectAvatar = { avatar ->
              repository.updateProfile(profile.copy(selectedAvatar = avatar))
            },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) },
            onSoundToggle = {
              repository.updateProfile(profile.copy(soundEnabled = !profile.soundEnabled))
            }
          )
        }

        is Screen.Settings -> {
          SettingsScreen(
            profile = profile,
            onToggleSound = { repository.updateProfile(profile.copy(soundEnabled = it)) },
            onToggleMusic = { repository.updateProfile(profile.copy(musicEnabled = it)) },
            onToggleHaptics = { repository.updateProfile(profile.copy(hapticsEnabled = it)) },
            onResetProgress = { repository.resetAllProgress() },
            onBackClick = { navigateBack() },
            onCoinClick = { navigateTo(Screen.Store) }
          )
        }
      }
    }

    // Rewarded Video & Interstitial Overlay Modal
    if (activeAdState.isShowing) {
      AdVideoOverlayModal(
        adState = activeAdState,
        onCompleteReward = {
          AdManager.completeReward()
        },
        onDismiss = {
          AdManager.dismissAd()
        }
      )
    }
  }
}
