package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ads.ActiveAdState
import com.example.ads.AdManager
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun AdVideoOverlayModal(
  adState: ActiveAdState,
  onCompleteReward: () -> Unit,
  onDismiss: () -> Unit
) {
  var countdown by remember(adState) { mutableIntStateOf(5) }
  var isFinished by remember(adState) { mutableStateOf(false) }

  LaunchedEffect(adState) {
    if (adState.isShowing) {
      countdown = 5
      isFinished = false
      while (countdown > 0) {
        delay(1000)
        countdown--
      }
      isFinished = true
    }
  }

  Dialog(
    onDismissRequest = {
      if (isFinished || adState.canSkip) onDismiss()
    },
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Box(
      modifier = Modifier
        .testTag("admob_video_overlay_dialog")
        .fillMaxSize()
        .background(DeepNavyDark.copy(alpha = 0.96f))
        .padding(20.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth(0.92f)
          .clip(RoundedCornerShape(24.dp))
          .background(DeepNavySurface)
          .border(2.dp, ElectricCyan.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Top status
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(SolarGold)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = if (adState.isRewarded) "REWARDED AD" else "INTERSTITIAL",
              color = Color.Black,
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }

          if (isFinished || adState.canSkip) {
            IconButton(
              onClick = onDismiss,
              modifier = Modifier
                .testTag("ad_close_button")
                .size(36.dp)
                .clip(CircleShape)
                .background(DeepNavyCard)
            ) {
              Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextPrimary)
            }
          } else {
            Text(
              text = "Reward in ${countdown}s",
              color = TextSecondary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Ad Creative Graphic Box
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
              Brush.verticalGradient(
                listOf(DeepNavyCard, DeepNavyDark)
              )
            )
            .border(1.dp, DeepNavyCardBorder, RoundedCornerShape(16.dp))
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = if (isFinished) Icons.Default.CardGiftcard else Icons.Default.PlayCircle,
              contentDescription = "Ad Icon",
              tint = if (isFinished) NeonEmerald else ElectricCyan,
              modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = adState.adTitle,
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Official AdMob Sample Test Creative",
              color = TextMuted,
              fontSize = 12.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Countdown progress bar
        val progress = (1f - (countdown / 5f)).coerceIn(0f, 1f)
        LinearProgressIndicator(
          progress = { progress },
          color = if (isFinished) NeonEmerald else ElectricCyan,
          trackColor = DeepNavyCard,
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
          text = if (isFinished) "🎉 You have earned your reward!" else "Watch this quick ad to unlock your prize:",
          color = TextPrimary,
          fontSize = 14.sp,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = adState.rewardType.title,
          color = SolarGold,
          fontSize = 18.sp,
          fontWeight = FontWeight.ExtraBold,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isFinished) {
          Button(
            onClick = onCompleteReward,
            colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
              .testTag("ad_claim_reward_button")
              .fillMaxWidth()
              .height(50.dp)
          ) {
            Text(
              text = "CLAIM REWARD",
              color = DeepNavyDark,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
          }
        } else {
          Text(
            text = "Please watch until the timer ends to receive your reward.",
            color = TextMuted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}
