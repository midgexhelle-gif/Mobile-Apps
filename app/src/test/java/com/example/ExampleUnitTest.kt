package com.example

import com.example.data.AchievementsData
import com.example.data.LevelConfig
import com.example.data.PuzzleLibrary
import com.example.model.Difficulty
import com.example.model.PlayerProfile
import com.example.model.PuzzleCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun puzzleLibrary_hasAtLeast200VerifiedPuzzles() {
    val puzzles = PuzzleLibrary.allPuzzles
    assertTrue("Expected at least 200 puzzles, found ${puzzles.size}", puzzles.size >= 200)

    // Ensure every single puzzle has a valid question, 4 options, and correct answer index
    for (p in puzzles) {
      assertTrue("Puzzle ${p.id} question is blank", p.question.isNotBlank())
      assertEquals("Puzzle ${p.id} should have 4 options", 4, p.options.size)
      assertTrue("Puzzle ${p.id} correctOptionIndex ${p.correctOptionIndex} is out of bounds", p.correctOptionIndex in p.options.indices)
      assertTrue("Puzzle ${p.id} explanation is blank", p.explanation.isNotBlank())
      assertTrue("Puzzle ${p.id} hint1 is blank", p.hint1.isNotBlank())
    }
  }

  @Test
  fun puzzleLibrary_coversAll15Categories() {
    val allCategories = PuzzleCategory.entries
    assertEquals(15, allCategories.size)

    for (cat in allCategories) {
      val inCat = PuzzleLibrary.getByCategory(cat)
      assertTrue("Category ${cat.displayName} has no puzzles", inCat.isNotEmpty())
    }
  }

  @Test
  fun levelConfig_generates100ProgressiveLevels() {
    val levels = LevelConfig.generateLevels(
      unlockedLevels = setOf(1),
      completedLevels = emptySet(),
      levelStarsMap = emptyMap(),
      levelScoresMap = emptyMap()
    )

    assertEquals(100, levels.size)
    assertTrue("Level 1 should be unlocked by default", levels[0].isUnlocked)
    assertEquals(Difficulty.BEGINNER, levels[0].difficulty)
    assertEquals(Difficulty.EASY, levels[15].difficulty)
    assertEquals(Difficulty.MEDIUM, levels[30].difficulty)
    assertEquals(Difficulty.HARD, levels[50].difficulty)
    assertEquals(Difficulty.EXPERT, levels[70].difficulty)
    assertEquals(Difficulty.GENIUS, levels[90].difficulty)
  }

  @Test
  fun achievements_hasAtLeast25Achievements() {
    val profile = PlayerProfile()
    val achievements = AchievementsData.buildAchievements(
      profile = profile,
      claimedIds = emptySet(),
      threeStarLevelCount = 0,
      categoriesPlayedCount = 0,
      dailyCompletedCount = 0,
      codeBreakingSolvedCount = 0,
      fastSolvedCount = 0
    )

    assertTrue("Expected >= 25 achievements, found ${achievements.size}", achievements.size >= 25)
  }
}
