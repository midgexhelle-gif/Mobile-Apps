package com.example.model

enum class BrainRank(val title: String, val minXp: Int, val badgeEmoji: String) {
  ROOKIE("Rookie Brain", 0, "🌱"),
  QUICK_THINKER("Quick Thinker", 500, "⚡"),
  LOGIC_MASTER("Logic Master", 1500, "🎯"),
  PUZZLE_CHAMPION("Puzzle Champion", 3500, "🏆"),
  BRAIN_GENIUS("Brain Genius", 7500, "🧠"),
  GRANDMASTER("Grandmaster", 15000, "👑");

  companion object {
    fun fromXp(xp: Int): BrainRank {
      return entries.lastOrNull { xp >= it.minXp } ?: ROOKIE
    }
  }
}

data class PlayerProfile(
  val username: String = "BrainChampion",
  val coins: Int = 120, // generous starting coins for player satisfaction
  val xp: Int = 0,
  val currentLevel: Int = 1,
  val totalStars: Int = 0,
  val streakDays: Int = 1,
  val lastPlayDateEpochDay: Long = 0,
  val totalSolvedCount: Int = 0,
  val consecutiveCorrect: Int = 0,
  val bestStreak: Int = 0,
  val bestTimeAttackScore: Int = 0,
  val selectedAvatar: String = "⚡",
  val unlockedAvatars: Set<String> = setOf("⚡", "🧠", "🎯", "🧩"),
  val soundEnabled: Boolean = true,
  val musicEnabled: Boolean = true,
  val hapticsEnabled: Boolean = true,
  val completedPuzzleIds: Set<String> = emptySet(),
  val completedLevelNumbers: Set<Int> = emptySet()
) {
  val rank: BrainRank
    get() = BrainRank.fromXp(xp)

  val nextRank: BrainRank?
    get() {
      val currentIndex = BrainRank.entries.indexOf(rank)
      return if (currentIndex < BrainRank.entries.size - 1) BrainRank.entries[currentIndex + 1] else null
    }

  val rankProgress: Float
    get() {
      val next = nextRank ?: return 1.0f
      val range = next.minXp - rank.minXp
      val earned = xp - rank.minXp
      return (earned.toFloat() / range.toFloat()).coerceIn(0f, 1f)
    }
}

data class LevelInfo(
  val levelNumber: Int,
  val difficulty: Difficulty,
  val puzzleIds: List<String>,
  val requiredStars: Int,
  val isUnlocked: Boolean,
  val isCompleted: Boolean,
  val starsEarned: Int,
  val bestScore: Int
)

data class Achievement(
  val id: String,
  val title: String,
  val description: String,
  val target: Int,
  val currentProgress: Int,
  val rewardCoins: Int,
  val isClaimed: Boolean,
  val emoji: String
) {
  val isCompleted: Boolean
    get() = currentProgress >= target
}

data class LeaderboardEntry(
  val rank: Int,
  val username: String,
  val avatar: String,
  val score: Int,
  val leagueTitle: String,
  val isCurrentPlayer: Boolean = false
)

data class DailyChallengeInfo(
  val epochDay: Long,
  val dateFormatted: String,
  val puzzleIds: List<String>,
  val isCompleted: Boolean,
  val scoreEarned: Int,
  val rewardClaimed: Boolean
)
