package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelInfo

object LevelConfig {

  // Generates 100 progressive campaign levels
  fun generateLevels(
    unlockedLevels: Set<Int>,
    completedLevels: Set<Int>,
    levelStarsMap: Map<Int, Int>,
    levelScoresMap: Map<Int, Int>
  ): List<LevelInfo> {
    val allPuzzles = PuzzleLibrary.allPuzzles
    val levels = ArrayList<LevelInfo>(100)

    for (lvl in 1..100) {
      val difficulty = Difficulty.fromLevel(lvl)
      val requiredStars = when (difficulty) {
        Difficulty.BEGINNER -> 0
        Difficulty.EASY -> (lvl - 10) * 2
        Difficulty.MEDIUM -> 30 + (lvl - 25) * 2
        Difficulty.HARD -> 70 + (lvl - 45) * 2
        Difficulty.EXPERT -> 110 + (lvl - 65) * 2
        Difficulty.GENIUS -> 150 + (lvl - 85) * 2
      }

      // Assign 5 deterministic puzzles per level from matching or adjacent difficulty pools
      val pool = allPuzzles.filter { it.difficulty == difficulty }.ifEmpty { allPuzzles }
      val startOffset = ((lvl - 1) * 3) % pool.size
      val selectedPuzzles = mutableListOf<String>()
      for (i in 0 until 5) {
        val p = pool[(startOffset + i) % pool.size]
        selectedPuzzles.add(p.id)
      }

      val isUnlocked = lvl == 1 || lvl in unlockedLevels || (lvl - 1) in completedLevels
      val isCompleted = lvl in completedLevels
      val stars = levelStarsMap[lvl] ?: if (isCompleted) 1 else 0
      val score = levelScoresMap[lvl] ?: 0

      levels.add(
        LevelInfo(
          levelNumber = lvl,
          difficulty = difficulty,
          puzzleIds = selectedPuzzles,
          requiredStars = requiredStars,
          isUnlocked = isUnlocked,
          isCompleted = isCompleted,
          starsEarned = stars,
          bestScore = score
        )
      )
    }

    return levels
  }
}
