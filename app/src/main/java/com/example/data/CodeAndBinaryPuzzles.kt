package com.example.data

import com.example.model.Difficulty
import com.example.model.Puzzle
import com.example.model.PuzzleCategory
import com.example.model.VisualType

object CodeAndBinaryPuzzles {
  val list: List<Puzzle> = listOf(
    // CODE BREAKING
    Puzzle(
      id = "code_01",
      category = PuzzleCategory.CODE_BREAKING,
      difficulty = Difficulty.BEGINNER,
      question = "CRACK THE 3-DIGIT LOCK\n\n" +
        "• 6 8 2 : One number correct and well placed\n" +
        "• 6 1 4 : One number correct, wrong placed\n" +
        "• 2 0 6 : Two numbers correct, both wrong placed\n" +
        "• 7 3 8 : Nothing is correct\n" +
        "• 7 8 0 : One number correct, wrong placed\n\n" +
        "What is the secret 3-digit code?",
      visualType = VisualType.CODE_LOCK,
      visualData = "3_DIGIT_LOCK",
      options = listOf("042", "062", "402", "142"),
      correctOptionIndex = 0,
      explanation = "7, 3, 8 are eliminated. 6 is eliminated because it cannot be both well placed and wrong placed in position 1. 2 is at the end (_ _ 2). 0 is at the start (0 _ 2). From 6 1 4, 4 is the middle digit. Result: 042.",
      hint1 = "Start by crossing out 7, 3, and 8 from all clues.",
      hint2 = "If 6 were correct, it conflicts between clues 1 and 2. So 2 is correct and in position 3.",
      hint3 = "0 must be in the first spot, and 4 in the second. Code is 042.",
      timeLimitSeconds = 60,
      coinReward = 15,
      pointValue = 120
    ),
    Puzzle(
      id = "code_02",
      category = PuzzleCategory.CODE_BREAKING,
      difficulty = Difficulty.EASY,
      question = "CRACK THE 3-DIGIT VAULT\n\n" +
        "• 1 4 7 : One digit correct and in right place\n" +
        "• 1 8 9 : One digit correct but in wrong place\n" +
        "• 9 6 4 : Two digits correct but both wrong place\n" +
        "• 5 2 3 : All digits are completely wrong\n" +
        "• 2 8 6 : One digit correct but wrong place\n\n" +
        "What is the combination?",
      visualType = VisualType.CODE_LOCK,
      visualData = "3_DIGIT_VAULT",
      options = listOf("679", "479", "674", "974"),
      correctOptionIndex = 0,
      explanation = "5, 2, 3 are false. From 2 8 6, 8 or 6 is correct. If 1 was in right place, 189 would have 1 right placed (conflict), so 1 is false. From 147, 4 or 7 is right placed. By testing candidates, 6 is in position 1, 7 is in position 2, and 9 is in position 3 = 679.",
      hint1 = "5, 2, 3 are all wrong, which helps eliminate 2 in the last clue.",
      hint2 = "Check if 1 can be correct in the first position.",
      hint3 = "The combination is 6 7 9.",
      timeLimitSeconds = 50,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "code_03",
      category = PuzzleCategory.CODE_BREAKING,
      difficulty = Difficulty.MEDIUM,
      question = "CRACK THE 4-DIGIT PIN\n\n" +
        "• The sum of all 4 digits is 14.\n" +
        "• The first digit is 3 times the fourth digit.\n" +
        "• The second and third digits are identical.\n" +
        "• The fourth digit is greater than 1.\n\n" +
        "What is the PIN?",
      visualType = VisualType.CODE_LOCK,
      visualData = "4_DIGIT_PIN",
      options = listOf("6442", "6332", "9221", "3441"),
      correctOptionIndex = 1,
      explanation = "Fourth digit d > 1. If d = 2, first digit a = 3 * 2 = 6. Sum so far = 6 + 2 = 8. Remaining sum for b + c = 14 - 8 = 6. Since b = c, b = 3 and c = 3. Check: 6 + 3 + 3 + 2 = 14. Code is 6332.",
      hint1 = "Since fourth digit > 1, test fourth digit = 2.",
      hint2 = "If fourth digit = 2, first digit is 3 * 2 = 6.",
      hint3 = "14 - (6 + 2) = 6. Divide by 2 to get 3. PIN is 6332.",
      timeLimitSeconds = 40,
      coinReward = 18,
      pointValue = 150
    ),
    Puzzle(
      id = "code_04",
      category = PuzzleCategory.CODE_BREAKING,
      difficulty = Difficulty.HARD,
      question = "MASTERMIND DEDUCTION\nA 4-digit code using digits 1–6 (no duplicates):\n\n" +
        "• Guess 1 2 3 4 : 2 digits correct, 0 in correct position\n" +
        "• Guess 3 4 5 6 : 2 digits correct, 1 in correct position\n" +
        "• Guess 1 5 2 6 : 3 digits correct, 2 in correct position\n" +
        "• Guess 5 1 6 2 : 3 digits correct, 1 in correct position\n\n" +
        "Which of the following is the exact code?",
      visualType = VisualType.CODE_LOCK,
      visualData = "4_DIGIT_MASTERMIND",
      options = listOf("5123", "1546", "3526", "2516"),
      correctOptionIndex = 1,
      explanation = "Comparing clues confirms digits {1, 5, 4, 6}. Position 1 is 1, position 2 is 5, position 3 is 4, position 4 is 6 = 1546.",
      hint1 = "Look closely at which digits are shared between 1 5 2 6 and 3 4 5 6.",
      hint2 = "Digits 1, 5, 4, 6 form the winning set.",
      hint3 = "1546 satisfies all clue constraints.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 170
    ),
    Puzzle(
      id = "code_05",
      category = PuzzleCategory.CODE_BREAKING,
      difficulty = Difficulty.EXPERT,
      question = "SAFE COMBINATION\n\n" +
        "• 3-digit number where all digits are distinct primes.\n" +
        "• The number is divisible by 3.\n" +
        "• The product of the three digits is 105.\n\n" +
        "If the digits are arranged in descending order, what is the code?",
      options = listOf("753", "573", "735", "357"),
      correctOptionIndex = 0,
      explanation = "Single-digit primes are 2, 3, 5, 7. Prime factorization of 105 = 3 * 5 * 7. Sum = 3 + 5 + 7 = 15 (divisible by 3). In descending order: 753.",
      hint1 = "Factor 105 into prime numbers.",
      hint2 = "105 = 3 * 5 * 7.",
      hint3 = "Arrange 7, 5, 3 in descending order: 753.",
      timeLimitSeconds = 25,
      coinReward = 25,
      pointValue = 200
    ),
    Puzzle(
      id = "code_06",
      category = PuzzleCategory.CODE_BREAKING,
      difficulty = Difficulty.GENIUS,
      question = "DIGITAL LOCK CIPHER\n" +
        "A 4-digit code ABCD has:\n" +
        "• A = Number of 0s in ABCD\n" +
        "• B = Number of 1s in ABCD\n" +
        "• C = Number of 2s in ABCD\n" +
        "• D = Number of 3s in ABCD\n\n" +
        "What is this unique self-describing 4-digit number?",
      options = listOf("1210", "2020", "2110", "3100"),
      correctOptionIndex = 0,
      explanation = "For 1210: count of 0s is 1 (A=1), count of 1s is 2 (B=2), count of 2s is 1 (C=1), count of 3s is 0 (D=0). It accurately describes itself!",
      hint1 = "This is an autobiographical (self-describing) number.",
      hint2 = "Check the options by counting how many 0s, 1s, 2s, and 3s appear.",
      hint3 = "In 1210: 1 zero, 2 ones, 1 two, 0 threes. Matches 1-2-1-0.",
      timeLimitSeconds = 20,
      coinReward = 30,
      pointValue = 250
    ),

    // BINARY AND SECRET MESSAGES
    Puzzle(
      id = "bin_01",
      category = PuzzleCategory.BINARY_CIPHER,
      difficulty = Difficulty.BEGINNER,
      question = "Convert this 4-bit binary number to decimal:\n1 1 0 1",
      visualType = VisualType.SEQUENCE_BOXES,
      visualData = "8 + 4 + 0 + 1",
      options = listOf("11", "13", "15", "9"),
      correctOptionIndex = 1,
      explanation = "1*(2³) + 1*(2²) + 0*(2¹) + 1*(2⁰) = 8 + 4 + 0 + 1 = 13.",
      hint1 = "The place values from left to right are 8, 4, 2, 1.",
      hint2 = "Add 8 + 4 + 1.",
      hint3 = "8 + 4 + 1 = 13.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "bin_02",
      category = PuzzleCategory.BINARY_CIPHER,
      difficulty = Difficulty.BEGINNER,
      question = "Using A=1, B=2 ... Z=26, decode this word:\n2 - 18 - 1 - 9 - 14",
      options = listOf("BRAIN", "BLITZ", "BREAD", "BRICK"),
      correctOptionIndex = 0,
      explanation = "2=B, 18=R, 1=A, 9=I, 14=N. Spells BRAIN.",
      hint1 = "Letter 2 is B, Letter 18 is R.",
      hint2 = "Letter 1 is A, Letter 9 is I.",
      hint3 = "Combine: B-R-A-I-N = BRAIN.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "bin_03",
      category = PuzzleCategory.BINARY_CIPHER,
      difficulty = Difficulty.EASY,
      question = "CAESAR CIPHER (+3 shift):\nIf 'D' becomes 'G' and 'A' becomes 'D', what word was encoded as 'FRGH'?",
      options = listOf("CODE", "COOL", "COLD", "CLUE"),
      correctOptionIndex = 0,
      explanation = "Shift each letter back by 3: F-3 = C, R-3 = O, G-3 = D, H-3 = E. The word is CODE.",
      hint1 = "Shift backwards by 3 positions in the alphabet.",
      hint2 = "F minus 3 letters is C.",
      hint3 = "R-3=O, G-3=D, H-3=E -> CODE.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "bin_04",
      category = PuzzleCategory.BINARY_CIPHER,
      difficulty = Difficulty.MEDIUM,
      question = "What decimal number is represented by the 6-bit binary:\n1 0 1 0 1 0",
      options = listOf("42", "40", "38", "44"),
      correctOptionIndex = 0,
      explanation = "Place values: 32 + 8 + 2 = 42.",
      hint1 = "Powers of 2: 32, 16, 8, 4, 2, 1.",
      hint2 = "Take 32 (pos 5), 8 (pos 3), and 2 (pos 1).",
      hint3 = "32 + 8 + 2 = 42.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "bin_05",
      category = PuzzleCategory.BINARY_CIPHER,
      difficulty = Difficulty.HARD,
      question = "ATBASH CIPHER (Reverse alphabet: A<->Z, B<->Y, C<->X ...):\nDecode the word: 'GZHP'",
      options = listOf("TASK", "TEAM", "TIME", "TRIP"),
      correctOptionIndex = 0,
      explanation = "G(7) maps to T(20), Z(26) maps to A(1), H(8) maps to S(19), P(16) maps to K(11). Word: TASK.",
      hint1 = "A is Z, B is Y, C is X.",
      hint2 = "G is 7th from start; 7th from end is T.",
      hint3 = "GZHP decodes to TASK.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    ),
    Puzzle(
      id = "bin_06",
      category = PuzzleCategory.BINARY_CIPHER,
      difficulty = Difficulty.EXPERT,
      question = "ROT13 CIPHER:\nA 13-letter shift turns 'PYVMM' into what word?",
      options = listOf("CLIFF", "CLOUD", "CLOCK", "CLEAR"),
      correctOptionIndex = 0,
      explanation = "P+13 = C, Y+13 = L, V+13 = I, M+13 = F, M+13 = F. Result: CLIFF.",
      hint1 = "Shift each letter forward or backward by 13.",
      hint2 = "P is letter 16; 16 - 13 = 3 (C).",
      hint3 = "PYVMM becomes CLIFF.",
      timeLimitSeconds = 25,
      coinReward = 25,
      pointValue = 200
    )
  )
}
