package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Difficulty
import com.example.model.Puzzle
import com.example.model.VisualType
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.DeepNavySurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanGlow
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PuzzleCardView(
  puzzle: Puzzle,
  selectedIndex: Int?,
  isSubmitted: Boolean,
  onOptionSelected: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    // Header Row: Category Badge + Difficulty
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(
            Brush.horizontalGradient(
              listOf(Color(puzzle.category.primaryColor), Color(puzzle.category.accentColor))
            )
          )
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text(text = puzzle.category.emoji, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = puzzle.category.displayName,
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }

      val diffColor = when (puzzle.difficulty) {
        Difficulty.BEGINNER -> NeonEmerald
        Difficulty.EASY -> ElectricCyan
        Difficulty.MEDIUM -> SolarGold
        Difficulty.HARD -> Color(0xFFFF6D00)
        Difficulty.EXPERT -> Color(0xFFD500F9)
        Difficulty.GENIUS -> NeonCoral
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(DeepNavyCard)
          .border(1.dp, diffColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Text(
          text = puzzle.difficulty.displayName.uppercase(),
          color = diffColor,
          fontSize = 11.sp,
          fontWeight = FontWeight.ExtraBold,
          letterSpacing = 1.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Main Question Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
      border = BorderStroke(1.dp, DeepNavyCardBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text(
          text = puzzle.question,
          color = TextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Medium,
          lineHeight = 24.sp
        )

        // Visual Presentation Container if applicable
        if (puzzle.visualType != VisualType.NONE && puzzle.visualData.isNotBlank()) {
          Spacer(modifier = Modifier.height(14.dp))
          VisualPresentationBox(puzzle.visualType, puzzle.visualData)
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Options List
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      puzzle.options.forEachIndexed { index, optionText ->
        val isSelected = selectedIndex == index
        val isCorrect = index == puzzle.correctOptionIndex

        val optionBg by animateColorAsState(
          targetValue = when {
            isSubmitted && isCorrect -> NeonEmerald.copy(alpha = 0.25f)
            isSubmitted && isSelected && !isCorrect -> NeonCoral.copy(alpha = 0.25f)
            isSelected -> ElectricCyan.copy(alpha = 0.15f)
            else -> DeepNavyCard
          },
          label = "option_bg"
        )

        val optionBorder by animateColorAsState(
          targetValue = when {
            isSubmitted && isCorrect -> NeonEmerald
            isSubmitted && isSelected && !isCorrect -> NeonCoral
            isSelected -> ElectricCyan
            else -> DeepNavyCardBorder
          },
          label = "option_border"
        )

        val optionLetter = ('A'.code + index).toChar()

        Box(
          modifier = Modifier
            .testTag("puzzle_option_$index")
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(optionBg)
            .border(1.5.dp, optionBorder, RoundedCornerShape(14.dp))
            .clickable(enabled = !isSubmitted) { onOptionSelected(index) }
            .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Letter Badge
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                  when {
                    isSubmitted && isCorrect -> NeonEmerald
                    isSubmitted && isSelected && !isCorrect -> NeonCoral
                    isSelected -> ElectricCyan
                    else -> DeepNavySurfaceVariant
                  }
                )
            ) {
              Text(
                text = optionLetter.toString(),
                color = if (isSelected || (isSubmitted && isCorrect)) DeepNavyDark else TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
              text = optionText,
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
              modifier = Modifier.weight(1f)
            )

            if (isSubmitted) {
              if (isCorrect) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Correct Answer",
                  tint = NeonEmerald,
                  modifier = Modifier.size(22.dp)
                )
              } else if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Cancel,
                  contentDescription = "Wrong Answer",
                  tint = NeonCoral,
                  modifier = Modifier.size(22.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun VisualPresentationBox(type: VisualType, data: String) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(DeepNavyDark)
      .border(1.dp, DeepNavyCardBorder, RoundedCornerShape(12.dp))
      .padding(14.dp)
  ) {
    when (type) {
      VisualType.CODE_LOCK -> {
        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "🔒", fontSize = 24.sp)
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "CIPHER CLUES",
            color = SolarGold,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        }
      }
      VisualType.SEQUENCE_BOXES -> {
        Text(
          text = data,
          color = ElectricCyan,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          textAlign = TextAlign.Center
        )
      }
      VisualType.WORD_ANAGRAM -> {
        Text(
          text = data,
          color = NeonEmerald,
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          letterSpacing = 3.sp,
          textAlign = TextAlign.Center
        )
      }
      VisualType.MATCHSTICK -> {
        Text(
          text = data,
          color = Color(0xFFFF6D00),
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          textAlign = TextAlign.Center
        )
      }
      else -> {
        Text(
          text = data,
          color = ElectricCyanGlow,
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
