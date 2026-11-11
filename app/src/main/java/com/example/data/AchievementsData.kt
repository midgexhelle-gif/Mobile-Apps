package com.example.data

import com.example.model.Achievement
import com.example.model.PlayerProfile

object AchievementsData {

  fun buildAchievements(
    profile: PlayerProfile,
    claimedIds: Set<String>,
    threeStarLevelCount: Int,
    categoriesPlayedCount: Int,
    dailyCompletedCount: Int,
    codeBreakingSolvedCount: Int,
    fastSolvedCount: Int
  ): List<Achievement> {
    return listOf(
      Achievement(
        id = "ach_01",
        title = "First Victory",
        description = "Solve your very first puzzle",
        target = 1,
        currentProgress = profile.totalSolvedCount,
        rewardCoins = 25,
        isClaimed = "ach_01" in claimedIds,
        emoji = "🎯"
      ),
      Achievement(
        id = "ach_02",
        title = "Speed Demon",
        description = "Solve a puzzle in under 10 seconds",
        target = 1,
        currentProgress = fastSolvedCount,
        rewardCoins = 30,
        isClaimed = "ach_02" in claimedIds,
        emoji = "⚡"
      ),
      Achievement(
        id = "ach_03",
        title = "Perfect Ten",
        description = "Reach a streak of 10 consecutive correct answers",
        target = 10,
        currentProgress = profile.bestStreak,
        rewardCoins = 75,
        isClaimed = "ach_03" in claimedIds,
        emoji = "🔥"
      ),
      Achievement(
        id = "ach_04",
        title = "Brain Explorer",
        description = "Solve puzzles in at least 5 different categories",
        target = 5,
        currentProgress = categoriesPlayedCount,
        rewardCoins = 50,
        isClaimed = "ach_04" in claimedIds,
        emoji = "🧭"
      ),
      Achievement(
        id = "ach_05",
        title = "Puzzle Enthusiast",
        description = "Solve 25 total puzzles",
        target = 25,
        currentProgress = profile.totalSolvedCount,
        rewardCoins = 50,
        isClaimed = "ach_05" in claimedIds,
        emoji = "🧩"
      ),
      Achievement(
        id = "ach_06",
        title = "Puzzle Addict",
        description = "Solve 100 total puzzles",
        target = 100,
        currentProgress = profile.totalSolvedCount,
        rewardCoins = 150,
        isClaimed = "ach_06" in claimedIds,
        emoji = "🧠"
      ),
      Achievement(
        id = "ach_07",
        title = "Master of 200",
        description = "Solve 200 total puzzles",
        target = 200,
        currentProgress = profile.totalSolvedCount,
        rewardCoins = 300,
        isClaimed = "ach_07" in claimedIds,
        emoji = "👑"
      ),
      Achievement(
        id = "ach_08",
        title = "Level 5 Climber",
        description = "Complete Level 5",
        target = 5,
        currentProgress = profile.completedLevelNumbers.size,
        rewardCoins = 30,
        isClaimed = "ach_08" in claimedIds,
        emoji = "🧗"
      ),
      Achievement(
        id = "ach_09",
        title = "Unstoppable",
        description = "Complete 20 campaign levels",
        target = 20,
        currentProgress = profile.completedLevelNumbers.size,
        rewardCoins = 100,
        isClaimed = "ach_09" in claimedIds,
        emoji = "🚀"
      ),
      Achievement(
        id = "ach_10",
        title = "Half Century",
        description = "Complete 50 campaign levels",
        target = 50,
        currentProgress = profile.completedLevelNumbers.size,
        rewardCoins = 250,
        isClaimed = "ach_10" in claimedIds,
        emoji = "🎖️"
      ),
      Achievement(
        id = "ach_11",
        title = "Centurion",
        description = "Conquer all 100 campaign levels",
        target = 100,
        currentProgress = profile.completedLevelNumbers.size,
        rewardCoins = 1000,
        isClaimed = "ach_11" in claimedIds,
        emoji = "🏆"
      ),
      Achievement(
        id = "ach_12",
        title = "Daily Spark",
        description = "Complete your first Daily Challenge",
        target = 1,
        currentProgress = dailyCompletedCount,
        rewardCoins = 40,
        isClaimed = "ach_12" in claimedIds,
        emoji = "☀️"
      ),
      Achievement(
        id = "ach_13",
        title = "Daily Champion",
        description = "Complete 7 Daily Challenges",
        target = 7,
        currentProgress = dailyCompletedCount,
        rewardCoins = 150,
        isClaimed = "ach_13" in claimedIds,
        emoji = "📅"
      ),
      Achievement(
        id = "ach_14",
        title = "Streak Keeper",
        description = "Maintain a 3-day daily streak",
        target = 3,
        currentProgress = profile.streakDays,
        rewardCoins = 60,
        isClaimed = "ach_14" in claimedIds,
        emoji = "⚡"
      ),
      Achievement(
        id = "ach_15",
        title = "Weekly Warrior",
        description = "Maintain a 7-day daily streak",
        target = 7,
        currentProgress = profile.streakDays,
        rewardCoins = 200,
        isClaimed = "ach_15" in claimedIds,
        emoji = "🌟"
      ),
      Achievement(
        id = "ach_16",
        title = "Code Cracker",
        description = "Solve 5 Code Breaking puzzles",
        target = 5,
        currentProgress = codeBreakingSolvedCount,
        rewardCoins = 50,
        isClaimed = "ach_16" in claimedIds,
        emoji = "🔐"
      ),
      Achievement(
        id = "ach_17",
        title = "Code Master",
        description = "Solve 25 Code Breaking puzzles",
        target = 25,
        currentProgress = codeBreakingSolvedCount,
        rewardCoins = 200,
        isClaimed = "ach_17" in claimedIds,
        emoji = "🕵️"
      ),
      Achievement(
        id = "ach_18",
        title = "Perfectionist",
        description = "Earn 3 stars in 10 levels",
        target = 10,
        currentProgress = threeStarLevelCount,
        rewardCoins = 100,
        isClaimed = "ach_18" in claimedIds,
        emoji = "⭐"
      ),
      Achievement(
        id = "ach_19",
        title = "Constellation",
        description = "Earn 3 stars in 30 levels",
        target = 30,
        currentProgress = threeStarLevelCount,
        rewardCoins = 300,
        isClaimed = "ach_19" in claimedIds,
        emoji = "✨"
      ),
      Achievement(
        id = "ach_20",
        title = "Time Attacker",
        description = "Score at least 500 points in Time Attack mode",
        target = 500,
        currentProgress = profile.bestTimeAttackScore,
        rewardCoins = 60,
        isClaimed = "ach_20" in claimedIds,
        emoji = "⏱️"
      ),
      Achievement(
        id = "ach_21",
        title = "Blitz Maestro",
        description = "Score at least 1,500 points in Time Attack mode",
        target = 1500,
        currentProgress = profile.bestTimeAttackScore,
        rewardCoins = 180,
        isClaimed = "ach_21" in claimedIds,
        emoji = "🌪️"
      ),
      Achievement(
        id = "ach_22",
        title = "Coin Hoarder",
        description = "Accumulate 500 Brain Coins balance",
        target = 500,
        currentProgress = profile.coins,
        rewardCoins = 100,
        isClaimed = "ach_22" in claimedIds,
        emoji = "💰"
      ),
      Achievement(
        id = "ach_23",
        title = "Brain Tycoon",
        description = "Accumulate 1,500 Brain Coins balance",
        target = 1500,
        currentProgress = profile.coins,
        rewardCoins = 250,
        isClaimed = "ach_23" in claimedIds,
        emoji = "💎"
      ),
      Achievement(
        id = "ach_24",
        title = "Rank Up: Quick Thinker",
        description = "Reach Quick Thinker rank (500 XP)",
        target = 500,
        currentProgress = profile.xp,
        rewardCoins = 50,
        isClaimed = "ach_24" in claimedIds,
        emoji = "⚡"
      ),
      Achievement(
        id = "ach_25",
        title = "Rank Up: Logic Master",
        description = "Reach Logic Master rank (1,500 XP)",
        target = 1500,
        currentProgress = profile.xp,
        rewardCoins = 150,
        isClaimed = "ach_25" in claimedIds,
        emoji = "🎯"
      ),
      Achievement(
        id = "ach_26",
        title = "Grandmaster Ascendant",
        description = "Reach the ultimate Grandmaster rank (15,000 XP)",
        target = 15000,
        currentProgress = profile.xp,
        rewardCoins = 1000,
        isClaimed = "ach_26" in claimedIds,
        emoji = "👑"
      )
    )
  }
}
