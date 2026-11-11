package com.example.model

enum class Difficulty(val displayName: String, val levelRange: String, val baseTimeLimit: Int, val multiplier: Float) {
  BEGINNER("Beginner", "Levels 1–10", 60, 1.0f),
  EASY("Easy", "Levels 11–25", 50, 1.2f),
  MEDIUM("Medium", "Levels 26–45", 40, 1.5f),
  HARD("Hard", "Levels 46–65", 30, 2.0f),
  EXPERT("Expert", "Levels 66–85", 25, 2.5f),
  GENIUS("Genius", "Levels 86–100", 20, 3.0f);

  companion object {
    fun fromLevel(level: Int): Difficulty = when (level) {
      in 1..10 -> BEGINNER
      in 11..25 -> EASY
      in 26..45 -> MEDIUM
      in 46..65 -> HARD
      in 66..85 -> EXPERT
      else -> GENIUS
    }
  }
}

enum class PuzzleCategory(
  val id: String,
  val displayName: String,
  val shortDesc: String,
  val emoji: String,
  val primaryColor: Long,
  val accentColor: Long
) {
  LOGIC(
    id = "logic",
    displayName = "Logic Puzzles",
    shortDesc = "Odd ones out, patterns, deductions",
    emoji = "🧩",
    primaryColor = 0xFF00E5FF,
    accentColor = 0xFF0284C7
  ),
  CODE_BREAKING(
    id = "code_breaking",
    displayName = "Code Breaking",
    shortDesc = "Vault locks, Mastermind & PINs",
    emoji = "🔐",
    primaryColor = 0xFFFFC107,
    accentColor = 0xFFB45309
  ),
  WORD_CHALLENGES(
    id = "word_challenges",
    displayName = "Word Challenges",
    shortDesc = "Anagrams, associations, missing letters",
    emoji = "🔤",
    primaryColor = 0xFF00E676,
    accentColor = 0xFF047857
  ),
  VISUAL(
    id = "visual",
    displayName = "Visual Puzzles",
    shortDesc = "Pattern matrices, illusions, object counts",
    emoji = "👁️",
    primaryColor = 0xFFD500F9,
    accentColor = 0xFF7E22CE
  ),
  MATH(
    id = "math",
    displayName = "Mathematical Puzzles",
    shortDesc = "Number grids, arithmetic riddles, triangles",
    emoji = "➗",
    primaryColor = 0xFFFF6D00,
    accentColor = 0xFFC2410C
  ),
  BINARY_CIPHER(
    id = "binary_cipher",
    displayName = "Binary & Secret Ciphers",
    shortDesc = "Caesar ciphers, ASCII, substitution",
    emoji = "💻",
    primaryColor = 0xFF06B6D4,
    accentColor = 0xFF0E7490
  ),
  SPATIAL(
    id = "spatial",
    displayName = "Spatial Reasoning",
    shortDesc = "Rotations, mazes, pathway geometry",
    emoji = "📐",
    primaryColor = 0xFF3B82F6,
    accentColor = 0xFF1D4ED8
  ),
  MEMORY(
    id = "memory",
    displayName = "Memory Games",
    shortDesc = "Number recall, sequence replay, flash cards",
    emoji = "🧠",
    primaryColor = 0xFFEC4899,
    accentColor = 0xFFBE185D
  ),
  RIDDLES(
    id = "riddles",
    displayName = "Brain Riddles",
    shortDesc = "Lateral thinking and clever enigmas",
    emoji = "💡",
    primaryColor = 0xFFF59E0B,
    accentColor = 0xFFB45309
  ),
  DETECTIVE(
    id = "detective",
    displayName = "Detective Challenges",
    shortDesc = "Identify the liar, solve mysteries",
    emoji = "🕵️",
    primaryColor = 0xFF8B5CF6,
    accentColor = 0xFF6D28D9
  ),
  MATCHSTICK(
    id = "matchstick",
    displayName = "Matchstick Puzzles",
    shortDesc = "Move one match to fix equations",
    emoji = "🪵",
    primaryColor = 0xFFEF4444,
    accentColor = 0xFFB91C1C
  ),
  SEQUENCES(
    id = "sequences",
    displayName = "Sequence Challenges",
    shortDesc = "Fibonacci, alternating formula series",
    emoji = "🔢",
    primaryColor = 0xFF10B981,
    accentColor = 0xFF047857
  ),
  TRICK_QUESTIONS(
    id = "trick_questions",
    displayName = "Trick Questions",
    shortDesc = "Carefully worded logic traps",
    emoji = "🎭",
    primaryColor = 0xFFA855F7,
    accentColor = 0xFF7E22CE
  ),
  SCIENCE(
    id = "science",
    displayName = "Science & Nature",
    shortDesc = "Physics, astronomy, biology reasoning",
    emoji = "🔬",
    primaryColor = 0xFF14B8A6,
    accentColor = 0xFF0F766E
  ),
  ADVANCED(
    id = "advanced",
    displayName = "Advanced Brain Teasers",
    shortDesc = "Multi-step complex deduction",
    emoji = "⚡",
    primaryColor = 0xFFFF5252,
    accentColor = 0xFF991B1B
  );

  companion object {
    fun fromId(id: String): PuzzleCategory =
      entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: LOGIC
  }
}

enum class VisualType {
  NONE,
  CODE_LOCK,
  NUMBER_GRID,
  WORD_ANAGRAM,
  MATCHSTICK,
  SEQUENCE_BOXES,
  DETECTIVE_CASE
}

data class Puzzle(
  val id: String,
  val category: PuzzleCategory,
  val difficulty: Difficulty,
  val question: String,
  val visualType: VisualType = VisualType.NONE,
  val visualData: String = "",
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String,
  val hint1: String,
  val hint2: String,
  val hint3: String,
  val timeLimitSeconds: Int = difficulty.baseTimeLimit,
  val coinReward: Int = 10,
  val pointValue: Int = 100
) {
  val correctAnswer: String
    get() = if (correctOptionIndex in options.indices) options[correctOptionIndex] else ""
}
