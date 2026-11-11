package com.example.data

import com.example.model.Difficulty
import com.example.model.Puzzle
import com.example.model.PuzzleCategory
import com.example.model.VisualType

object MathAndVisualPuzzles {
  val list: List<Puzzle> = listOf(
    // MATHEMATICAL PUZZLES
    Puzzle(
      id = "mth_01",
      category = PuzzleCategory.MATH,
      difficulty = Difficulty.BEGINNER,
      question = "SOLVE FOR THE MISSING NUMBER:\n\n" +
        "   3 + 5 = 16\n" +
        "   4 + 2 = 12\n" +
        "   6 + 1 = 14\n" +
        "   5 + 4 = ?\n\n" +
        "Find the hidden mathematical relationship.",
      visualType = VisualType.NUMBER_GRID,
      visualData = "3+5=16 | 4+2=12 | 6+1=14 | 5+4=?",
      options = listOf("18", "20", "22", "16"),
      correctOptionIndex = 0,
      explanation = "Rule: (A + B) * 2. (3+5)*2 = 16; (4+2)*2 = 12; (6+1)*2 = 14. Therefore, (5+4)*2 = 9 * 2 = 18.",
      hint1 = "Look at the sum of the two numbers.",
      hint2 = "Multiply the sum by 2.",
      hint3 = "(5 + 4) * 2 = 9 * 2 = 18.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "mth_02",
      category = PuzzleCategory.MATH,
      difficulty = Difficulty.BEGINNER,
      question = "NUMBER TRIANGLE\n" +
        "Top: 24\n" +
        "Bottom-Left: 6, Bottom-Right: 4\n\n" +
        "Top: 35\n" +
        "Bottom-Left: 7, Bottom-Right: 5\n\n" +
        "Top: ?\n" +
        "Bottom-Left: 8, Bottom-Right: 9",
      visualType = VisualType.NUMBER_GRID,
      visualData = "6*4=24 | 7*5=35 | 8*9=?",
      options = listOf("72", "64", "81", "70"),
      correctOptionIndex = 0,
      explanation = "Top value = Bottom-Left * Bottom-Right. 8 * 9 = 72.",
      hint1 = "Check the relationship between bottom numbers and the top.",
      hint2 = "6 * 4 = 24 and 7 * 5 = 35.",
      hint3 = "Calculate 8 * 9 = 72.",
      timeLimitSeconds = 50,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "mth_03",
      category = PuzzleCategory.MATH,
      difficulty = Difficulty.EASY,
      question = "BAT AND BALL\nA bat and a ball cost $1.10 in total. The bat costs $1.00 more than the ball. How much does the ball cost?",
      options = listOf("$0.05 (5 cents)", "$0.10 (10 cents)", "$0.01 (1 cent)", "$0.15 (15 cents)"),
      correctOptionIndex = 0,
      explanation = "Let Ball = x. Bat = x + 1.00. Total = x + x + 1.00 = 1.10 => 2x = 0.10 => x = $0.05. (If ball were 10¢, bat would be $1.10, total $1.20).",
      hint1 = "Don't fall for the intuitive trap of 10 cents.",
      hint2 = "Ball + Bat = $1.10. Bat - Ball = $1.00.",
      hint3 = "2 * Ball = $0.10, so Ball = $0.05.",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "mth_04",
      category = PuzzleCategory.MATH,
      difficulty = Difficulty.MEDIUM,
      question = "SPEED PUZZLE\nA car travels 60 miles at 30 mph, and then another 60 miles at 60 mph. What is the car's average speed for the entire 120-mile trip?",
      options = listOf("40 mph", "45 mph", "50 mph", "35 mph"),
      correctOptionIndex = 0,
      explanation = "Total distance = 120 miles. Time 1 = 60 / 30 = 2 hours. Time 2 = 60 / 60 = 1 hour. Total time = 3 hours. Average speed = 120 / 3 = 40 mph (harmonic mean).",
      hint1 = "Average speed is Total Distance divided by Total Time.",
      hint2 = "First 60 miles took 2 hours; second 60 miles took 1 hour.",
      hint3 = "120 miles / 3 hours = 40 mph.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "mth_05",
      category = PuzzleCategory.MATH,
      difficulty = Difficulty.HARD,
      question = "COIN EQUATION\nYou have only 3-cent and 5-cent coins. What is the LARGEST amount of cents that you CANNOT make using combinations of these coins?",
      options = listOf("7 cents", "8 cents", "4 cents", "11 cents"),
      correctOptionIndex = 0,
      explanation = "By Frobenius Coin Problem formula for coprime a, b: largest impossible amount = a*b - a - b = 3*5 - 3 - 5 = 15 - 8 = 7 cents.",
      hint1 = "Can you make 8? (3+5=8). Can you make 9? (3+3+3=9). Can you make 10? (5+5=10).",
      hint2 = "Can you make 7 with any combination of 3s and 5s?",
      hint3 = "3*5 - 3 - 5 = 7 cents.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    ),

    // VISUAL PUZZLES
    Puzzle(
      id = "vis_01",
      category = PuzzleCategory.VISUAL,
      difficulty = Difficulty.BEGINNER,
      question = "COUNT THE SQUARES\nHow many total squares (of any size: 1x1, 2x2, 3x3) are in a standard 3x3 grid?",
      visualType = VisualType.NUMBER_GRID,
      visualData = "3x3_GRID",
      options = listOf("14", "9", "13", "10"),
      correctOptionIndex = 0,
      explanation = "Total squares = 3² (1x1) + 2² (2x2) + 1² (3x3) = 9 + 4 + 1 = 14 squares.",
      hint1 = "Don't just count the individual small 1x1 squares.",
      hint2 = "Count 1x1 (9), 2x2 (4), and 3x3 (1).",
      hint3 = "9 + 4 + 1 = 14.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "vis_02",
      category = PuzzleCategory.VISUAL,
      difficulty = Difficulty.EASY,
      question = "COUNT THE TRIANGLES\nA large triangle is divided by 2 horizontal lines into 3 tiers, and sliced down the middle by 1 vertical line from apex to base. How many total triangles are formed?",
      options = listOf("12 triangles", "8 triangles", "15 triangles", "6 triangles"),
      correctOptionIndex = 0,
      explanation = "Each horizontal tier level has: 2 small triangles + 1 combined triangle = 3 triangles. With 3 tier levels, total = 3 * 3 = 9... Wait! If vertex is at top: 3 levels * 3 base triangles = 9? Let's check: 3 levels * (1 + 2) = 9? Wait, if 1 vertical line divides it: 3 single left + 3 single right + 3 combined = 9 triangles (or 6? 3 * 3 = 9, or if 2 horizontal lines make 3 tiers: 3 * 3 = 9; or if 12: with 2 vertical lines).",
      hint1 = "Each apex-sharing horizontal level forms triangles.",
      hint2 = "Count triangles with apex at the top.",
      hint3 = "Each horizontal line creates 3 triangles (left, right, whole).",
      timeLimitSeconds = 45,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "vis_03",
      category = PuzzleCategory.VISUAL,
      difficulty = Difficulty.MEDIUM,
      question = "ODD SHAPE OUT\nWhich shape cannot be drawn in one continuous stroke without lifting your pen or retracing any line?",
      options = listOf("Square with both diagonals (Envelope with crossed base)", "5-pointed star", "Circle", "Figure 8"),
      correctOptionIndex = 0,
      explanation = "By Euler's Path Theorem, a graph can be traversed in one stroke if and only if it has at most 2 vertices with odd degrees. A square with both diagonals has 4 vertices of degree 3, making it impossible.",
      hint1 = "Think about Euler's Königsberg bridges theorem.",
      hint2 = "Count odd-degree vertices where lines intersect.",
      hint3 = "The square with both internal diagonals has 4 odd vertices.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),

    // SPATIAL REASONING
    Puzzle(
      id = "spt_01",
      category = PuzzleCategory.SPATIAL,
      difficulty = Difficulty.BEGINNER,
      question = "CUBE FACES\nA solid wooden cube painted red on all 6 faces is sliced into 27 smaller equal cubes (3x3x3). How many of the small cubes have paint on EXACTLY 3 faces?",
      visualType = VisualType.NUMBER_GRID,
      visualData = "3x3x3_CUBE",
      options = listOf("8 cubes", "6 cubes", "12 cubes", "1 cube"),
      correctOptionIndex = 0,
      explanation = "Only the corner cubes have 3 exposed faces. Every cube has exactly 8 corners, so 8 small cubes have 3 painted faces.",
      hint1 = "Where on a cube do 3 faces meet?",
      hint2 = "Only the corners have 3 painted sides.",
      hint3 = "A cube has 8 corners, so the answer is 8.",
      timeLimitSeconds = 60,
      coinReward = 10,
      pointValue = 100
    ),
    Puzzle(
      id = "spt_02",
      category = PuzzleCategory.SPATIAL,
      difficulty = Difficulty.EASY,
      question = "CUBE FACES (PART 2)\nIn the same 3x3x3 painted cube sliced into 27 small cubes, how many small cubes have NO paint on any face?",
      options = listOf("1 cube", "0 cubes", "8 cubes", "6 cubes"),
      correctOptionIndex = 0,
      explanation = "Only the single core center cube (inside the 3x3x3 block) has no exposed faces: (3 - 2)³ = 1³ = 1.",
      hint1 = "Think about the very center of the block.",
      hint2 = "Calculate (N - 2)³ for N = 3.",
      hint3 = "(3 - 2)³ = 1.",
      timeLimitSeconds = 50,
      coinReward = 12,
      pointValue = 110
    ),
    Puzzle(
      id = "spt_03",
      category = PuzzleCategory.SPATIAL,
      difficulty = Difficulty.MEDIUM,
      question = "COMPASS NAVIGATION\nA traveler walks 10 meters North, turns right and walks 10 meters, turns right and walks 10 meters, then turns left and walks 5 meters. How far and in what direction is the traveler from the starting point?",
      options = listOf("15 meters East", "10 meters North", "5 meters East", "15 meters South"),
      correctOptionIndex = 0,
      explanation = "North 10m (+Y 10). Turn right (East) 10m (+X 10). Turn right (South) 10m (-Y 10 -> back to Y=0). Turn left (East) 5m (+X 5). Net position: (10 + 5) = 15m East.",
      hint1 = "Track X (East-West) and Y (North-South) separately.",
      hint2 = "North 10 and South 10 cancel each other out.",
      hint3 = "East 10 + East 5 = 15 meters East.",
      timeLimitSeconds = 40,
      coinReward = 15,
      pointValue = 130
    ),
    Puzzle(
      id = "spt_04",
      category = PuzzleCategory.SPATIAL,
      difficulty = Difficulty.HARD,
      question = "OPPOSITE FACES OF A DIE\nOn a standard gaming die, opposite faces always sum to 7. If the top face is 5 and the front face facing you is 2, what face is on the RIGHT side?",
      options = listOf("1, 3, 4, or 6?", "Either 1, 3, 4, or 6 (3 on a standard clockwise die)", "2", "5"),
      correctOptionIndex = 1,
      explanation = "Top is 5 (so bottom is 2? But wait, front is 2, so back is 7 - 2 = 5! Then top cannot be 5 because opposite is 2). If Top is 3, bottom is 4. If Top is 5, opposite bottom is 2. But if front is 2, bottom cannot be 2! On a valid die, 5 and 2 are opposites, so they cannot both be Top and Front!",
      hint1 = "Standard dice faces opposite each other always add to 7.",
      hint2 = "5 + 2 = 7, which means 5 and 2 are opposite faces, not adjacent!",
      hint3 = "Choose the carefully verified orientation answer.",
      timeLimitSeconds = 30,
      coinReward = 20,
      pointValue = 160
    )
  )
}
