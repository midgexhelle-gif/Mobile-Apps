package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.DailyChallengeInfo
import com.example.model.LevelInfo
import com.example.model.PlayerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class GameStateRepository(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("brain_blitz_game_state", Context.MODE_PRIVATE)

  private val _profile = MutableStateFlow(loadProfile())
  val profile: StateFlow<PlayerProfile> = _profile.asStateFlow()

  private val _unlockedLevels = MutableStateFlow(loadUnlockedLevels())
  val unlockedLevels: StateFlow<Set<Int>> = _unlockedLevels.asStateFlow()

  private val _completedLevels = MutableStateFlow(loadCompletedLevels())
  val completedLevels: StateFlow<Set<Int>> = _completedLevels.asStateFlow()

  private val _levelStars = MutableStateFlow(loadLevelStars())
  val levelStars: StateFlow<Map<Int, Int>> = _levelStars.asStateFlow()

  private val _levelScores = MutableStateFlow(loadLevelScores())
  val levelScores: StateFlow<Map<Int, Int>> = _levelScores.asStateFlow()

  private val _claimedAchievements = MutableStateFlow(loadClaimedAchievements())
  val claimedAchievements: StateFlow<Set<String>> = _claimedAchievements.asStateFlow()

  private val _categoriesPlayed = MutableStateFlow(loadCategoriesPlayed())
  val categoriesPlayed: StateFlow<Set<String>> = _categoriesPlayed.asStateFlow()

  private val _codeBreakingSolved = MutableStateFlow(prefs.getInt("stat_code_breaking_count", 0))
  val codeBreakingSolved: StateFlow<Int> = _codeBreakingSolved.asStateFlow()

  private val _fastSolvedCount = MutableStateFlow(prefs.getInt("stat_fast_solve_count", 0))
  val fastSolvedCount: StateFlow<Int> = _fastSolvedCount.asStateFlow()

  private val _dailyCompletedDates = MutableStateFlow(loadDailyCompletedDates())
  val dailyCompletedDates: StateFlow<Set<Long>> = _dailyCompletedDates.asStateFlow()

  init {
    checkDailyStreak()
  }

  private fun loadProfile(): PlayerProfile {
    val username = prefs.getString("prof_username", "BrainMaster") ?: "BrainMaster"
    val coins = prefs.getInt("prof_coins", 120)
    val xp = prefs.getInt("prof_xp", 0)
    val currentLevel = prefs.getInt("prof_current_level", 1)
    val totalStars = prefs.getInt("prof_total_stars", 0)
    val streakDays = prefs.getInt("prof_streak_days", 1)
    val lastPlayEpochDay = prefs.getLong("prof_last_play_epoch", 0L)
    val totalSolvedCount = prefs.getInt("prof_total_solved", 0)
    val consecutiveCorrect = prefs.getInt("prof_consec_correct", 0)
    val bestStreak = prefs.getInt("prof_best_streak", 0)
    val bestTimeAttack = prefs.getInt("prof_best_time_attack", 0)
    val avatar = prefs.getString("prof_selected_avatar", "⚡") ?: "⚡"
    val unlockedAvatars = prefs.getStringSet("prof_unlocked_avatars", setOf("⚡", "🧠", "🎯", "🧩")) ?: setOf("⚡")
    val sound = prefs.getBoolean("pref_sound", true)
    val music = prefs.getBoolean("pref_music", true)
    val haptics = prefs.getBoolean("pref_haptics", true)
    val solvedPuzzles = prefs.getStringSet("prof_solved_puzzles", emptySet()) ?: emptySet()
    val completedLevels = prefs.getStringSet("prof_completed_levels_set", emptySet())
      ?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()

    return PlayerProfile(
      username = username,
      coins = coins,
      xp = xp,
      currentLevel = currentLevel,
      totalStars = totalStars,
      streakDays = streakDays,
      lastPlayDateEpochDay = lastPlayEpochDay,
      totalSolvedCount = totalSolvedCount,
      consecutiveCorrect = consecutiveCorrect,
      bestStreak = bestStreak,
      bestTimeAttackScore = bestTimeAttack,
      selectedAvatar = avatar,
      unlockedAvatars = unlockedAvatars,
      soundEnabled = sound,
      musicEnabled = music,
      hapticsEnabled = haptics,
      completedPuzzleIds = solvedPuzzles,
      completedLevelNumbers = completedLevels
    )
  }

  private fun loadUnlockedLevels(): Set<Int> {
    val set = prefs.getStringSet("unlocked_levels", setOf("1")) ?: setOf("1")
    return set.mapNotNull { it.toIntOrNull() }.toSet().ifEmpty { setOf(1) }
  }

  private fun loadCompletedLevels(): Set<Int> {
    val set = prefs.getStringSet("completed_levels", emptySet()) ?: emptySet()
    return set.mapNotNull { it.toIntOrNull() }.toSet()
  }

  private fun loadLevelStars(): Map<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    for (i in 1..100) {
      val stars = prefs.getInt("level_stars_$i", -1)
      if (stars >= 0) map[i] = stars
    }
    return map
  }

  private fun loadLevelScores(): Map<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    for (i in 1..100) {
      val score = prefs.getInt("level_score_$i", -1)
      if (score >= 0) map[i] = score
    }
    return map
  }

  private fun loadClaimedAchievements(): Set<String> {
    return prefs.getStringSet("claimed_achievements", emptySet()) ?: emptySet()
  }

  private fun loadCategoriesPlayed(): Set<String> {
    return prefs.getStringSet("categories_played", emptySet()) ?: emptySet()
  }

  private fun loadDailyCompletedDates(): Set<Long> {
    return prefs.getStringSet("daily_completed_dates", emptySet())
      ?.mapNotNull { it.toLongOrNull() }?.toSet() ?: emptySet()
  }

  private fun checkDailyStreak() {
    val todayEpoch = try {
      LocalDate.now().toEpochDay()
    } catch (_: Exception) {
      System.currentTimeMillis() / (1000 * 60 * 60 * 24)
    }

    val current = _profile.value
    val last = current.lastPlayDateEpochDay
    if (last == 0L) {
      updateProfile(current.copy(lastPlayDateEpochDay = todayEpoch, streakDays = 1))
    } else if (todayEpoch == last + 1) {
      val newStreak = current.streakDays + 1
      val streakBonus = if (newStreak % 7 == 0) 150 else 20
      updateProfile(current.copy(
        lastPlayDateEpochDay = todayEpoch,
        streakDays = newStreak,
        coins = current.coins + streakBonus
      ))
    } else if (todayEpoch > last + 1) {
      updateProfile(current.copy(lastPlayDateEpochDay = todayEpoch, streakDays = 1))
    }
  }

  fun updateProfile(newProfile: PlayerProfile) {
    _profile.value = newProfile
    prefs.edit()
      .putString("prof_username", newProfile.username)
      .putInt("prof_coins", newProfile.coins)
      .putInt("prof_xp", newProfile.xp)
      .putInt("prof_current_level", newProfile.currentLevel)
      .putInt("prof_total_stars", newProfile.totalStars)
      .putInt("prof_streak_days", newProfile.streakDays)
      .putLong("prof_last_play_epoch", newProfile.lastPlayDateEpochDay)
      .putInt("prof_total_solved", newProfile.totalSolvedCount)
      .putInt("prof_consec_correct", newProfile.consecutiveCorrect)
      .putInt("prof_best_streak", newProfile.bestStreak)
      .putInt("prof_best_time_attack", newProfile.bestTimeAttackScore)
      .putString("prof_selected_avatar", newProfile.selectedAvatar)
      .putStringSet("prof_unlocked_avatars", newProfile.unlockedAvatars)
      .putBoolean("pref_sound", newProfile.soundEnabled)
      .putBoolean("pref_music", newProfile.musicEnabled)
      .putBoolean("pref_haptics", newProfile.hapticsEnabled)
      .putStringSet("prof_solved_puzzles", newProfile.completedPuzzleIds)
      .putStringSet("prof_completed_levels_set", newProfile.completedLevelNumbers.map { it.toString() }.toSet())
      .apply()
  }

  fun addCoins(amount: Int) {
    val cur = _profile.value
    updateProfile(cur.copy(coins = (cur.coins + amount).coerceAtLeast(0)))
  }

  fun spendCoins(amount: Int): Boolean {
    val cur = _profile.value
    if (cur.coins >= amount) {
      updateProfile(cur.copy(coins = cur.coins - amount))
      return true
    }
    return false
  }

  fun recordPuzzleSolved(puzzleId: String, categoryId: String, timeTakenSec: Int, isCorrect: Boolean) {
    val cur = _profile.value
    val newSolved = cur.completedPuzzleIds + puzzleId
    val newStreak = if (isCorrect) cur.consecutiveCorrect + 1 else 0
    val bestStreak = maxOf(cur.bestStreak, newStreak)
    val newXp = if (isCorrect) cur.xp + 25 else cur.xp
    val newCoins = if (isCorrect) cur.coins + 10 + (if (timeTakenSec <= 10) 5 else 0) else cur.coins

    updateProfile(cur.copy(
      completedPuzzleIds = newSolved,
      consecutiveCorrect = newStreak,
      bestStreak = bestStreak,
      totalSolvedCount = if (isCorrect) cur.totalSolvedCount + 1 else cur.totalSolvedCount,
      xp = newXp,
      coins = newCoins
    ))

    if (isCorrect) {
      // Record category
      val newCats = _categoriesPlayed.value + categoryId
      _categoriesPlayed.value = newCats
      prefs.edit().putStringSet("categories_played", newCats).apply()

      if (categoryId.equals("code_breaking", ignoreCase = true)) {
        val newCb = _codeBreakingSolved.value + 1
        _codeBreakingSolved.value = newCb
        prefs.edit().putInt("stat_code_breaking_count", newCb).apply()
      }

      if (timeTakenSec <= 10) {
        val newFast = _fastSolvedCount.value + 1
        _fastSolvedCount.value = newFast
        prefs.edit().putInt("stat_fast_solve_count", newFast).apply()
      }
    }
  }

  fun completeLevel(levelNumber: Int, starsEarned: Int, score: Int, bonusCoins: Int) {
    val curUnlocked = _unlockedLevels.value.toMutableSet()
    val curCompleted = _completedLevels.value.toMutableSet()
    val curStars = _levelStars.value.toMutableMap()
    val curScores = _levelScores.value.toMutableMap()

    curCompleted.add(levelNumber)
    curUnlocked.add(levelNumber)
    curUnlocked.add(levelNumber + 1) // unlock next level

    val oldStars = curStars[levelNumber] ?: 0
    val bestStars = maxOf(oldStars, starsEarned)
    curStars[levelNumber] = bestStars

    val oldScore = curScores[levelNumber] ?: 0
    val bestScore = maxOf(oldScore, score)
    curScores[levelNumber] = bestScore

    _unlockedLevels.value = curUnlocked
    _completedLevels.value = curCompleted
    _levelStars.value = curStars
    _levelScores.value = curScores

    val totalStars = curStars.values.sum()
    val editor = prefs.edit()
    editor.putStringSet("unlocked_levels", curUnlocked.map { it.toString() }.toSet())
    editor.putStringSet("completed_levels", curCompleted.map { it.toString() }.toSet())
    editor.putInt("level_stars_$levelNumber", bestStars)
    editor.putInt("level_score_$levelNumber", bestScore)
    editor.apply()

    val curProfile = _profile.value
    updateProfile(curProfile.copy(
      currentLevel = maxOf(curProfile.currentLevel, levelNumber + 1),
      totalStars = totalStars,
      coins = curProfile.coins + bonusCoins,
      xp = curProfile.xp + (starsEarned * 50),
      completedLevelNumbers = curCompleted
    ))
  }

  fun recordTimeAttackScore(score: Int) {
    val cur = _profile.value
    if (score > cur.bestTimeAttackScore) {
      updateProfile(cur.copy(bestTimeAttackScore = score))
    }
  }

  fun claimAchievement(achievementId: String, rewardCoins: Int) {
    val claimed = _claimedAchievements.value.toMutableSet()
    if (claimed.add(achievementId)) {
      _claimedAchievements.value = claimed
      prefs.edit().putStringSet("claimed_achievements", claimed).apply()
      addCoins(rewardCoins)
    }
  }

  fun markDailyChallengeCompleted(epochDay: Long, scoreEarned: Int) {
    val dates = _dailyCompletedDates.value.toMutableSet()
    if (dates.add(epochDay)) {
      _dailyCompletedDates.value = dates
      prefs.edit().putStringSet("daily_completed_dates", dates.map { it.toString() }.toSet()).apply()
      addCoins(50) // Daily challenge completion bonus
    }
  }

  fun resetAllProgress() {
    prefs.edit().clear().apply()
    _profile.value = loadProfile()
    _unlockedLevels.value = setOf(1)
    _completedLevels.value = emptySet()
    _levelStars.value = emptyMap()
    _levelScores.value = emptyMap()
    _claimedAchievements.value = emptySet()
    _categoriesPlayed.value = emptySet()
    _codeBreakingSolved.value = 0
    _fastSolvedCount.value = 0
    _dailyCompletedDates.value = emptySet()
  }
}
