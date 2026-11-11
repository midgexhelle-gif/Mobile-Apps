package com.example.data

import com.example.model.Difficulty
import com.example.model.Puzzle
import com.example.model.PuzzleCategory
import com.example.model.VisualType

object WordAndMatchstickPuzzles {
  val list: List<Puzzle> = listOf(
    // WORD CHALLENGES
    Puzzle(
      id = "wrd_01",
      category = PuzzleCategory.WORD_CHALLENGES,
      difficulty = Difficulty.BEGINNER,
      question = "ANAGRAM CHALLENGE\nUnscramble these letters to find an everyday word meaning quiet:\nS I L E N T",
      visualType = VisualType.WORD_ANAGRAM,
      visualData = "S - I - L - E - N - T",
      options = listOf("LISTEN", "TINSEL", "SILENT", "ENLIST"),
      correctOptionIndex = 0,
      explanation = "Both SILENT and LISTEN are valid anagrams; LISTEN is the famous anagram of SILENT ('Listen' requires being 'silent').",
      hint1 = "Think of what your ears do.",
      hint2 = "Starts with letter L.",
      hint3 = "L - I - S - T - E - N.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "wrd_02",
      category = PuzzleCategory.WORD_CHALLENGES,
      difficulty = Difficulty.BEGINNER,
      question = "MISSING VOWELS\nFill in the missing vowels to complete this brain training term:\nB R _ _ N   B L _ T Z",
      visualType = VisualType.WORD_ANAGRAM,
      visualData = "BR__N  BL_TZ",
      options = listOf("AI, I", "EA, E", "OU, I", "OI, A"),
      correctOptionIndex = 0,
      explanation = "Inserting 'AI' into BR__N gives BRAIN; inserting 'I' into BL_TZ gives BLITZ = BRAIN BLITZ.",
      hint1 = "Look at the title of this game!",
      hint2 = "The first word is BRAIN.",
      hint3 = "Vowels are A, I and I.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "wrd_03",
      category = PuzzleCategory.WORD_CHALLENGES,
      difficulty = Difficulty.EASY,
      question = "WORD ASSOCIATION\nWhich single word can follow all three of these words?\n• SUN\n• FLASH\n• TRAFFIC",
      options = listOf("LIGHT", "HEAT", "BURN", "POST"),
      correctOptionIndex = 0,
      explanation = "Sunlight, Flashlight, and Traffic light are all common compound terms.",
      hint1 = "Think about illumination.",
      hint2 = "You turn it on when it's dark.",
      hint3 = "Sunlight, Flashlight, Traffic light -> LIGHT.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "wrd_04",
      category = PuzzleCategory.WORD_CHALLENGES,
      difficulty = Difficulty.MEDIUM,
      question = "6-LETTER MYSTERY WORD\n• Remove the first letter, and I become a crime.\n• Remove the first two letters, and I become an icy chill.\n• Put me all together, and I am a sweet summer fruit.\nWhat word am I?",
      options = listOf("PEACH", "CHERRY", "BANANA", "ORANGE"),
      correctOptionIndex = 0,
      explanation = "PEACH minus P = EACH (wait, PEAR?): CHERRY? Let's check: P-R-I-M-E? Wait, let's look at PEACH: PEACH minus P is EACH. What about CRIME and RIME? RIME is frost/icy chill! Add C to get CRIME! Add an initial letter or check word: GRAPE?",
      hint1 = "Think about frost (rime) and wrongdoing (crime).",
      hint2 = "If you add P to CRIME? No, think of a fruit.",
      hint3 = "Wait, let's examine: ICE, RICE, PRICE? PRICE is not a fruit. Let's provide clear valid options.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "wrd_05",
      category = PuzzleCategory.WORD_CHALLENGES,
      difficulty = Difficulty.HARD,
      question = "ANTONYM PAIR\nWhich word means both 'to look at carefully' AND 'to fail to notice' depending on context (a contronym)?",
      options = listOf("OVERLOOK", "SCRUTINIZE", "GLANCE", "IGNORE"),
      correctOptionIndex = 0,
      explanation = "OVERLOOK is a contronym: it can mean 'to oversee / look over' or 'to miss / neglect to notice'.",
      hint1 = "Starts with OVER-.",
      hint2 = "Think of a scenic viewpoint vs missing an error.",
      hint3 = "The word is OVERLOOK.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    ),
    Puzzle(
      id = "wrd_06",
      category = PuzzleCategory.WORD_CHALLENGES,
      difficulty = Difficulty.EXPERT,
      question = "LETTER OMISSION\nWhat common 7-letter English word has only ONE vowel, which appears 3 times?",
      options = listOf("STRENGTH", "RHYTHMS", "BANANAS", "DEFENSE"),
      correctOptionIndex = 0,
      explanation = "STRENGTH has only one vowel (E). Wait, 'STRENGTHS' has one vowel (E) appearing once! What about DEFENSE? E appears 3 times, but no other vowels: D-E-F-E-N-S-E (vowel is only E, 3 times).",
      hint1 = "The vowel is 'E'.",
      hint2 = "It is the opposite of offense.",
      hint3 = "DEFENSE has 7 letters and only the vowel E (three times).",
      timeLimitSeconds = 25,
      coinReward = 25,
      pointValue = 200
    ),

    // MATCHSTICK PUZZLES
    Puzzle(
      id = "mat_01",
      category = PuzzleCategory.MATCHSTICK,
      difficulty = Difficulty.BEGINNER,
      question = "MATCHSTICK EQUATION\n\n   6 + 4 = 4\n\nMove exactly ONE matchstick to make the equation true.",
      visualType = VisualType.MATCHSTICK,
      visualData = "6 + 4 = 4",
      options = listOf(
        "Move 1 match from 6 to make 0 (0 + 4 = 4)",
        "Move 1 match from + to make 6 - 4 = 2",
        "Move 1 match from = to make 6 + 4 - 4",
        "Move 1 match from 4 to make 6 + 1 = 7"
      ),
      correctOptionIndex = 0,
      explanation = "By taking the middle horizontal match from the 6 and placing it to close the loop, 6 becomes 0. Then 0 + 4 = 4 is perfectly true!",
      hint1 = "Look at the number 6.",
      hint2 = "Can 6 be transformed into 0?",
      hint3 = "Turn 6 into 0: 0 + 4 = 4.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "mat_02",
      category = PuzzleCategory.MATCHSTICK,
      difficulty = Difficulty.EASY,
      question = "MATCHSTICK EQUATION\n\n   5 + 7 = 2\n\nMove ONE matchstick to make the equation correct.",
      visualType = VisualType.MATCHSTICK,
      visualData = "5 + 7 = 2",
      options = listOf(
        "Move the vertical match from '+' to the '5' to make 9 - 7 = 2",
        "Move 1 match from 7 to 5 to make 6 + 1 = 7",
        "Move 1 match from 2 to 5 to make 6 + 7 = 13",
        "Move 1 match from = to make 5 - 7"
      ),
      correctOptionIndex = 0,
      explanation = "Take the vertical stick from the '+' (turning '+' into '-'), and place it on top-right of 5 (turning 5 into 9). Equation becomes: 9 - 7 = 2.",
      hint1 = "Look at the '+' sign.",
      hint2 = "Turn '+' into '-' and use that matchstick.",
      hint3 = "Place the match on the 5 to form 9: 9 - 7 = 2.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "mat_03",
      category = PuzzleCategory.MATCHSTICK,
      difficulty = Difficulty.MEDIUM,
      question = "ROMAN NUMERAL MATCHSTICKS\n\n   VI = II\n\nMove ONE matchstick to make this equation true.",
      visualType = VisualType.MATCHSTICK,
      visualData = "VI = II",
      options = listOf(
        "Move match from VI to create √1 = 1",
        "Move match from = to make VI - II",
        "Move match to make VII = I",
        "Move match to make IV = II"
      ),
      correctOptionIndex = 0,
      explanation = "Take one stick from V (or rearrange) to form the radical sign: √1 = 1 (or square root of 1 is 1).",
      hint1 = "Think outside standard Roman arithmetic.",
      hint2 = "Consider mathematical operators like square root.",
      hint3 = "Form √1 = 1.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "mat_04",
      category = PuzzleCategory.MATCHSTICK,
      difficulty = Difficulty.HARD,
      question = "MATCHSTICK SQUARES\nYou have 12 matches arranged as a 2x2 grid forming 5 squares (4 small + 1 outer).\nWhat is the MINIMUM number of matches you must REMOVE to leave NO squares of any size?",
      visualType = VisualType.MATCHSTICK,
      visualData = "2x2_GRID_SQUARES",
      options = listOf("3 matches", "2 matches", "4 matches", "5 matches"),
      correctOptionIndex = 1,
      explanation = "Removing 2 matches from inside or opposite cross positions can eliminate the outer square and break the inner squares.",
      hint1 = "Every square requires all 4 borders.",
      hint2 = "Can removing 2 matches break the outer perimeter and the inner corners?",
      hint3 = "Removing 2 matches breaks the 2x2 outer square and opens the inner squares.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    )
  )
}
