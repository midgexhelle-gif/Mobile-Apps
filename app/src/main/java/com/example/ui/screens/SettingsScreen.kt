package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.PlayerProfile
import com.example.ui.components.AdBannerComposable
import com.example.ui.components.GameTopBar
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.DeepNavyDark
import com.example.ui.theme.DeepNavySurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.NeonCoral
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun SettingsScreen(
  profile: PlayerProfile,
  onToggleSound: (Boolean) -> Unit,
  onToggleMusic: (Boolean) -> Unit,
  onToggleHaptics: (Boolean) -> Unit,
  onResetProgress: () -> Unit,
  onBackClick: () -> Unit,
  onCoinClick: () -> Unit
) {
  var showResetConfirmDialog by remember { mutableStateOf(false) }
  var showPrivacyDialog by remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavyDark)
  ) {
    GameTopBar(
      title = "SETTINGS",
      coins = profile.coins,
      streakDays = profile.streakDays,
      soundEnabled = profile.soundEnabled,
      onBackClick = onBackClick,
      onCoinClick = onCoinClick,
      onSoundToggle = null
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Text(
        text = "AUDIO & FEEDBACK",
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.dp, DeepNavyCardBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          SettingToggleRow(
            title = "Sound Effects",
            subtitle = "Correct, buzzer, ticking, and fanfare audio",
            isChecked = profile.soundEnabled,
            onCheckedChange = onToggleSound
          )

          Spacer(modifier = Modifier.height(14.dp))

          SettingToggleRow(
            title = "Background Music",
            subtitle = "Ambient puzzle solving music",
            isChecked = profile.musicEnabled,
            onCheckedChange = onToggleMusic
          )

          Spacer(modifier = Modifier.height(14.dp))

          SettingToggleRow(
            title = "Vibration & Haptics",
            subtitle = "Tactile tap and answer feedback",
            isChecked = profile.hapticsEnabled,
            onCheckedChange = onToggleHaptics
          )
        }
      }

      Text(
        text = "LEGAL & COMPLIANCE",
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.dp, DeepNavyCardBorder),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { showPrivacyDialog = true }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.PrivacyTip, contentDescription = "Privacy", tint = ElectricCyan)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(text = "Privacy Policy & AdMob Consent", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
              Text(text = "GDPR, Google Play policy disclosure", color = TextSecondary, fontSize = 11.sp)
            }
          }
          Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Open", tint = TextMuted)
        }
      }

      Text(
        text = "DATA MANAGEMENT",
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavySurface),
        border = BorderStroke(1.dp, DeepNavyCardBorder),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { showResetConfirmDialog = true }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.RestartAlt, contentDescription = "Reset", tint = NeonCoral)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(text = "Reset All Game Progress", color = NeonCoral, fontSize = 14.sp, fontWeight = FontWeight.Bold)
              Text(text = "Clear coins, levels, stars, and achievements", color = TextSecondary, fontSize = 11.sp)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "Brain Blitz v1.0 • Built with Jetpack Compose\nOfficial AdMob Test Integration",
        color = TextMuted,
        fontSize = 11.sp,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
    }

    AdBannerComposable()
  }

  // Reset Confirmation Dialog
  if (showResetConfirmDialog) {
    Dialog(onDismissRequest = { showResetConfirmDialog = false }) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(DeepNavySurface)
          .border(1.5.dp, NeonCoral.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
          .padding(20.dp)
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "⚠️ RESET GAME DATA?", color = NeonCoral, fontSize = 18.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Are you sure you want to reset all your progress? Your coins, unlocked levels, and achievements will be reset to factory defaults.",
            color = TextSecondary,
            fontSize = 13.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )
          Spacer(modifier = Modifier.height(20.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { showResetConfirmDialog = false },
              colors = ButtonDefaults.buttonColors(containerColor = DeepNavyCard),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text(text = "CANCEL", color = TextPrimary)
            }
            Button(
              onClick = {
                showResetConfirmDialog = false
                onResetProgress()
              },
              colors = ButtonDefaults.buttonColors(containerColor = NeonCoral),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text(text = "RESET ALL", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }

  // Privacy Policy Dialog
  if (showPrivacyDialog) {
    Dialog(onDismissRequest = { showPrivacyDialog = false }) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(DeepNavySurface)
          .border(1.5.dp, DeepNavyCardBorder, RoundedCornerShape(20.dp))
          .padding(20.dp)
      ) {
        Column {
          Text(text = "Privacy Policy & Terms", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Brain Blitz - Puzzle Challenge respects your privacy.\n\n" +
              "• Game Progress: Stored locally on your device using secure preferences.\n" +
              "• Google AdMob: Official Google Mobile Ads SDK is utilized for non-intrusive banner, interstitial, and voluntary rewarded video ads. Official Google Test Unit IDs are active.\n" +
              "• No Personal Data Selling: We do not sell, rent, or distribute personal information to third parties.\n" +
              "• Contact: support@brainblitzgame.example.com",
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(18.dp))
          Button(
            onClick = { showPrivacyDialog = false },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(text = "I AGREE", color = DeepNavyDark, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
private fun SettingToggleRow(
  title: String,
  subtitle: String,
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(text = title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
      Text(text = subtitle, color = TextSecondary, fontSize = 11.sp)
    }

    Switch(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = ElectricCyan,
        checkedTrackColor = DeepNavyCard,
        uncheckedThumbColor = TextMuted,
        uncheckedTrackColor = DeepNavyDark
      )
    )
  }
}
