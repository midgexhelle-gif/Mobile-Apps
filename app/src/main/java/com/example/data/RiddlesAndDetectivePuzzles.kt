package com.example.data

import com.example.model.Difficulty
import com.example.model.Puzzle
import com.example.model.PuzzleCategory
import com.example.model.VisualType

object RiddlesAndDetectivePuzzles {
  val list: List<Puzzle> = listOf(
    // BRAIN RIDDLES
    Puzzle(
      id = "rid_01",
      category = PuzzleCategory.RIDDLES,
      difficulty = Difficulty.BEGINNER,
      question = "The more you take, the more you leave behind. What am I?",
      options = listOf("Footsteps", "Money", "Time", "Photographs"),
      correctOptionIndex = 0,
      explanation = "Every step you take leaves another footprint behind you.",
      hint1 = "Think about walking on sand or snow.",
      hint2 = "You leave them on the ground.",
      hint3 = "They are footsteps.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "rid_02",
      category = PuzzleCategory.RIDDLES,
      difficulty = Difficulty.BEGINNER,
      question = "What has keys, but no locks; space, but no room; and you can enter, but never go inside?",
      options = listOf("A keyboard", "A piano", "A map", "A treasure chest"),
      correctOptionIndex = 0,
      explanation = "A computer keyboard has letter keys, a Space bar, and an Enter key, but no physical room.",
      hint1 = "You type on it every day.",
      hint2 = "Look down at a computer.",
      hint3 = "It has a Spacebar and Enter key: a keyboard.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "rid_03",
      category = PuzzleCategory.RIDDLES,
      difficulty = Difficulty.EASY,
      question = "I speak without a mouth and hear without ears. I have no body, but I come alive with wind. What am I?",
      options = listOf("An echo", "A cloud", "A shadow", "A whistle"),
      correctOptionIndex = 0,
      explanation = "An echo has no physical mouth or body, yet repeats sound across valleys and caves.",
      hint1 = "Think about sounds bouncing off mountains.",
      hint2 = "It repeats what you shout.",
      hint3 = "It is an echo.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "rid_04",
      category = PuzzleCategory.RIDDLES,
      difficulty = Difficulty.MEDIUM,
      question = "What can travel around the entire world while staying in a single corner?",
      options = listOf("A postage stamp", "A compass", "The moon", "The equator"),
      correctOptionIndex = 0,
      explanation = "A postage stamp stays in the top-right corner of an envelope as it travels across the globe.",
      hint1 = "It sticks onto letters.",
      hint2 = "Placed on an envelope corner.",
      hint3 = "A postage stamp.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "rid_05",
      category = PuzzleCategory.RIDDLES,
      difficulty = Difficulty.HARD,
      question = "I am not alive, but I grow; I don't have lungs, but I need air; I don't have a mouth, but water kills me. What am I?",
      options = listOf("Fire", "A sponge", "Rust", "Ice"),
      correctOptionIndex = 0,
      explanation = "Fire consumes oxygen (needs air), expands and grows, and is extinguished by water.",
      hint1 = "It produces heat and light.",
      hint2 = "Oxygen fuels it; water puts it out.",
      hint3 = "Fire.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    ),

    // DETECTIVE CHALLENGES
    Puzzle(
      id = "det_01",
      category = PuzzleCategory.DETECTIVE,
      difficulty = Difficulty.EASY,
      question = "THE MUSEUM HEIST\nThree suspects are questioned about a stolen jewel:\n\n" +
        "• Alex says: 'Bob stole it.'\n" +
        "• Bob says: 'Alex is lying.'\n" +
        "• Charlie says: 'I did not steal it.'\n\n" +
        "If EXACTLY ONE of them is telling the truth, who stole the jewel?",
      visualType = VisualType.DETECTIVE_CASE,
      visualData = "Alex vs Bob vs Charlie",
      options = listOf("Charlie", "Alex", "Bob", "None of them"),
      correctOptionIndex = 0,
      explanation = "Alex and Bob make contradictory statements, so exactly one of {Alex, Bob} is telling the truth. Since only ONE person tells the truth, Charlie MUST be lying! Charlie said 'I didn't steal it.' Therefore Charlie is the thief!",
      hint1 = "Notice that Alex and Bob directly contradict each other.",
      hint2 = "One of Alex or Bob must be the sole truth-teller.",
      hint3 = "This means Charlie's statement is false, proving Charlie stole it.",
      timeLimitSeconds = 50,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "det_02",
      category = PuzzleCategory.DETECTIVE,
      difficulty = Difficulty.MEDIUM,
      question = "THE ALIBI CRACKER\nA robbery occurred on Sunday, June 14 at 2:00 PM. Four suspects gave alibis:\n\n" +
        "• Chef: 'I was baking fresh croissants for Sunday breakfast.'\n" +
        "• Butler: 'I was sorting the mail delivered this afternoon.'\n" +
        "• Gardener: 'I was trimming the rose bushes in the courtyard.'\n" +
        "• Driver: 'I was polishing the limousine in the garage.'\n\n" +
        "Whose alibi contains a fatal flaw?",
      visualType = VisualType.DETECTIVE_CASE,
      visualData = "Sunday Afternoon Alibi",
      options = listOf("The Butler", "The Chef", "The Gardener", "The Driver"),
      correctOptionIndex = 0,
      explanation = "Standard postal services do NOT deliver ordinary mail on Sunday afternoon. The Butler's alibi is fabricated!",
      hint1 = "Look at the day of the week: Sunday.",
      hint2 = "Does standard mail get delivered on Sundays?",
      hint3 = "The Butler claimed he was sorting Sunday mail delivery.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "det_03",
      category = PuzzleCategory.DETECTIVE,
      difficulty = Difficulty.HARD,
      question = "THE LAB EXPERIMENT\nFour scientists test a serum: A, B, C, D.\n• If A was successful, then B failed.\n• Either B succeeded or C succeeded, but not both.\n• D succeeded if and only if A failed.\n• Exactly three of them succeeded.\nWho was the single failure?",
      options = listOf("Scientist A", "Scientist B", "Scientist C", "Scientist D"),
      correctOptionIndex = 0,
      explanation = "If A succeeded: B fails. Exactly one of {B, C} succeeds, so C succeeds. D succeeds iff A fails, so D fails. But that leaves only A and C successful (2 successes, contradiction). Thus A must fail! If A fails: D succeeds. Exactly one of {B, C} succeeds? If B and C both... wait, if A fails, successes are B, C, D = 3 successes! A is the single failure.",
      hint1 = "Try assuming A is one of the successes.",
      hint2 = "If A succeeds, D fails, leaving at most 2 successes.",
      hint3 = "Therefore A failed, and B, C, D succeeded.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    ),

    // TRICK QUESTIONS
    Puzzle(
      id = "trk_01",
      category = PuzzleCategory.TRICK_QUESTIONS,
      difficulty = Difficulty.BEGINNER,
      question = "A farmer has 17 sheep. A terrible storm strikes and all but 9 of them die. How many sheep does the farmer have left alive?",
      options = listOf("9 sheep", "8 sheep", "0 sheep", "17 sheep"),
      correctOptionIndex = 0,
      explanation = "'All BUT 9 die' means exactly 9 survived!",
      hint1 = "Read the wording carefully.",
      hint2 = "Pay attention to 'all but nine'.",
      hint3 = "9 sheep are left.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "trk_02",
      category = PuzzleCategory.TRICK_QUESTIONS,
      difficulty = Difficulty.BEGINNER,
      question = "How many months of the calendar year have 28 days?",
      options = listOf("All 12 months", "Only 1 (February)", "6 months", "7 months"),
      correctOptionIndex = 0,
      explanation = "Every single month has at least 28 days! (February has 28 or 29, and all other months have 30 or 31).",
      hint1 = "Does January have 28 days?",
      hint2 = "Every month reaches the 28th day.",
      hint3 = "All 12 months have 28 days.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "trk_03",
      category = PuzzleCategory.TRICK_QUESTIONS,
      difficulty = Difficulty.EASY,
      question = "You overtake the person who is running in 2nd place in a marathon. What position are you now in?",
      options = listOf("2nd place", "1st place", "3rd place", "Last place"),
      correctOptionIndex = 0,
      explanation = "When you overtake the person in 2nd place, you take their spot in 2nd place! (The 1st place runner is still ahead of you).",
      hint1 = "You took the spot of the person you passed.",
      hint2 = "Who is still in front of you?",
      hint3 = "The 1st place runner is still leading, so you are in 2nd.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),

    // SCIENCE AND GENERAL KNOWLEDGE
    Puzzle(
      id = "sci_01",
      category = PuzzleCategory.SCIENCE,
      difficulty = Difficulty.BEGINNER,
      question = "Which gas makes up the largest percentage of Earth's atmosphere by volume?",
      options = listOf("Nitrogen (~78%)", "Oxygen (~21%)", "Carbon Dioxide (~0.04%)", "Argon (~0.93%)"),
      correctOptionIndex = 0,
      explanation = "Nitrogen makes up approximately 78% of dry atmosphere, followed by oxygen at 21%.",
      hint1 = "It is not oxygen.",
      hint2 = "It is an inert gas essential for amino acids.",
      hint3 = "Nitrogen at roughly 78%.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "sci_02",
      category = PuzzleCategory.SCIENCE,
      difficulty = Difficulty.EASY,
      question = "Which planet in our solar system rotates clockwise on its axis (retrograde rotation)?",
      options = listOf("Venus", "Mars", "Jupiter", "Neptune"),
      correctOptionIndex = 0,
      explanation = "Venus rotates clockwise (from east to west) on its axis, contrary to most planets in our solar system.",
      hint1 = "The hottest planet in the solar system.",
      hint2 = "Often called the Morning Star.",
      hint3 = "Venus.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "sci_03",
      category = PuzzleCategory.SCIENCE,
      difficulty = Difficulty.MEDIUM,
      question = "If you drop a heavy cannonball and a light wooden ball in a complete vacuum with zero air resistance, which hits the ground first?",
      options = listOf("Both hit at the exact same instant", "The cannonball hits first", "The wooden ball hits first", "Neither falls"),
      correctOptionIndex = 0,
      explanation = "In a vacuum, gravitational acceleration g is identical for all masses regardless of weight, so both hit at the exact same instant (Galileo's principle).",
      hint1 = "Recall Apollo 15's hammer and feather experiment on the Moon.",
      hint2 = "Gravity accelerates all masses equally in a vacuum.",
      hint3 = "Both land at the exact same moment.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),

    // MEMORY GAMES
    Puzzle(
      id = "mem_01",
      category = PuzzleCategory.MEMORY,
      difficulty = Difficulty.BEGINNER,
      question = "FLASH SEQUENCE RECALL\nStudy this 5-item color pattern:\n\n🔵 BLUE — 🟡 YELLOW — 🔴 RED — 🟢 GREEN — 🔵 BLUE\n\nWhich color was in the 4th position?",
      visualType = VisualType.SEQUENCE_BOXES,
      visualData = "🔵 🟡 🔴 🟢 🔵",
      options = listOf("🟢 GREEN", "🔴 RED", "🟡 YELLOW", "🔵 BLUE"),
      correctOptionIndex = 0,
      explanation = "Position 1: Blue, 2: Yellow, 3: Red, 4: Green, 5: Blue. The 4th item is Green.",
      hint1 = "Blue was 1st and 5th.",
      hint2 = "Yellow was 2nd, Red was 3rd.",
      hint3 = "Green is in the 4th spot.",
      timeLimitSeconds = 40,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "mem_02",
      category = PuzzleCategory.MEMORY,
      difficulty = Difficulty.EASY,
      question = "DIGIT SPAN RECALL\nMemorize the 6-digit sequence:\n\n   9 — 4 — 7 — 1 — 3 — 8\n\nWhat is the sequence read in REVERSE order?",
      options = listOf("8 — 3 — 1 — 7 — 4 — 9", "8 — 1 — 3 — 7 — 4 — 9", "8 — 3 — 7 — 1 — 4 — 9", "9 — 4 — 7 — 1 — 3 — 8"),
      correctOptionIndex = 0,
      explanation = "Reversing 9, 4, 7, 1, 3, 8 gives 8, 3, 1, 7, 4, 9.",
      hint1 = "Start from the last digit: 8.",
      hint2 = "Next are 3, then 1.",
      hint3 = "8, 3, 1, 7, 4, 9.",
      timeLimitSeconds = 40,
      coinReward = 12,
      pointValue = 110
    )
  )
}
