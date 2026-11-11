package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.data.GameStateRepository
import com.example.ui.BrainBlitzApp
import com.example.ui.theme.BrainBlitzTheme
import com.example.ui.theme.DeepNavyDark

class MainActivity : ComponentActivity() {

  private lateinit var repository: GameStateRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    repository = GameStateRepository(applicationContext)

    setContent {
      BrainBlitzTheme {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(DeepNavyDark)
            .safeDrawingPadding()
        ) {
          BrainBlitzApp(repository = repository)
        }
      }
    }
  }
}
