package com.gsrathoreniks.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsrathoreniks.sample.ui.theme.ScratchifyTheme
import com.gsrathoreniks.scratchify.api.Scratchify
import com.gsrathoreniks.scratchify.api.ScratchifyController
import com.gsrathoreniks.scratchify.api.config.ScratchifyBrushConfig
import com.gsrathoreniks.scratchify.api.config.ScratchifyConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScratchifyTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(Color.Blue)) { innerPadding ->
                    Content(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val scratchController = remember { ScratchifyController() }
    Column(modifier = modifier.fillMaxSize()) {
        // 1. Default ScratchifyView Example
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Scratchify(
                modifier = Modifier.fillMaxSize(),
                config = ScratchifyConfig(),
                controller = scratchController,
                contentToReveal = { ContentToReveal("Default Scratch Config") },
                overlayContent = { OverlayContent("Scratch Me!") }
            )
        }
        Text(
            text = "Scratch Progress: ${scratchController.scratchProgress}",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // 2. Custom Brush Size Example
        val brushSizeConfig = ScratchifyConfig(brushConfig = ScratchifyBrushConfig(brushSize = 50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Scratchify(
                modifier = Modifier.fillMaxSize(),
                config = brushSizeConfig,
                controller = remember { ScratchifyController() },
                contentToReveal = { ContentToReveal("Large Brush Size!") },
                overlayContent = { OverlayContent("Scratch to Reveal!") }
            )
        }

        // 3. Custom Reveal Threshold Example
        val customThresholdController = remember { ScratchifyController() }
        val thresholdConfig = ScratchifyConfig(revealFullAtPercent = 0.5f)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Scratchify(
                modifier = Modifier.fillMaxSize(),
                config = thresholdConfig,
                controller = customThresholdController,
                contentToReveal = { ContentToReveal("50% Scratch to Reveal!") },
                overlayContent = { OverlayContent("Keep Scratching!") }
            )
        }
        Text(
            text = "Custom Threshold Progress: ${customThresholdController.scratchProgress}",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // 4. Scratch Callbacks Example
        val callbackController = remember { ScratchifyController() }
        callbackController.onScratchStarted = {
            println("Scratch Started!")
        }
        callbackController.onScratchProgress = { progress ->
            println("Scratch Progress: $progress")
        }
        callbackController.onScratchCompleted = {
            println("Scratch Completed!")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Scratchify(
                modifier = Modifier.fillMaxSize(),
                config = ScratchifyConfig(),
                controller = callbackController,
                contentToReveal = { ContentToReveal("Scratch Callbacks!") },
                overlayContent = { OverlayContent("Callbacks in Action!") }
            )
        }
    }
}


@Composable
fun ContentToReveal(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Green)
    ) {
        Text(
            text = message,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun OverlayContent(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Red)
    ) {
        Text(
            text = message,
            fontSize = 24.sp,
            color = Color.White
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ScratchifyTheme {
        Content()
    }
}