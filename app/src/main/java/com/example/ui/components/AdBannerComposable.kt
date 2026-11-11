package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdManager
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.DeepNavyCardBorder
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.SolarGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AdBannerComposable(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .testTag("admob_banner_container")
      .fillMaxWidth()
      .height(52.dp)
      .background(DeepNavyCard)
      .border(1.dp, DeepNavyCardBorder)
      .padding(horizontal = 12.dp, vertical = 4.dp),
    contentAlignment = Alignment.Center
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SolarGold)
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "Ad",
            color = Color.Black,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = "Google AdMob Test Unit",
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = AdManager.TEST_BANNER_AD_UNIT_ID,
            color = TextMuted,
            fontSize = 10.sp,
            maxLines = 1
          )
        }
      }

      Text(
        text = "SPONSORED",
        color = ElectricCyan,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
      )
    }
  }
}
