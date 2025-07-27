package com.gsrathoreniks.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsrathoreniks.sample.ui.theme.ScratchifyTheme
import com.gsrathoreniks.scratchify.api.Scratchify
import com.gsrathoreniks.scratchify.api.ScratchifyController
import com.gsrathoreniks.scratchify.api.ScratchState
import com.gsrathoreniks.scratchify.api.config.BrushShape
import com.gsrathoreniks.scratchify.api.config.HapticFeedbackType
import com.gsrathoreniks.scratchify.api.config.RevealAnimationType
import com.gsrathoreniks.scratchify.api.config.ScratchifyAnimationConfig
import com.gsrathoreniks.scratchify.api.config.ScratchifyBrushConfig
import com.gsrathoreniks.scratchify.api.config.ScratchifyConfig
import com.gsrathoreniks.scratchify.api.config.ScratchifyHapticConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScratchifyTheme {
                ScratchifyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScratchifyApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Scratchify Demo",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6750A4)
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Content(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3E5F5),
                        Color(0xFFE1BEE7)
                    )
                )
            )
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Text(
            text = "Interactive Scratch Card Examples",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A148C),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Text(
            text = "Explore different configurations and features",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF7B1FA2),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // 1. Default Configuration Example
        ScratchCardExample(
            title = "✨ Default Configuration",
            description = "Basic scratch card with standard settings",
            config = ScratchifyConfig(),
            contentMessage = "🎉 You Won!",
            overlayMessage = "Scratch Here!"
        )

        // 2. Transparent Scratch (Traditional)
        ScratchCardExample(
            title = "🔍 Traditional Scratch",
            description = "Classic transparent scratching reveals content",
            config = ScratchifyConfig(
                brushConfig = ScratchifyBrushConfig(
                    brushSize = 25.dp,
                    brushColor = Color.Transparent,  // Traditional mode
                    opacity = 1f
                )
            ),
            contentMessage = "💎 Hidden Treasure!",
            overlayMessage = "Scratch Away!"
        )

        // 3. Colored Brush Painting Mode
        ScratchCardExample(
            title = "🎨 Paint Mode",
            description = "Red semi-transparent brush paints on surface",
            config = ScratchifyConfig(
                brushConfig = ScratchifyBrushConfig(
                    brushSize = 30.dp,
                    brushColor = Color.Red,
                    opacity = 0.7f
                )
            ),
            contentMessage = "🖼️ Masterpiece!",
            overlayMessage = "Paint Here!"
        )

        // 4. Large Brush Example
        ScratchCardExample(
            title = "🖌️ Large Brush Size",
            description = "Quick reveal with bigger brush strokes",
            config = ScratchifyConfig(
                brushConfig = ScratchifyBrushConfig(brushSize = 50.dp)
            ),
            contentMessage = "🏆 Jackpot!",
            overlayMessage = "Big Brush!"
        )

        // 5. Custom Reveal Threshold Example
        ScratchCardExample(
            title = "⚡ Quick Reveal",
            description = "Auto-reveals at 50% progress",
            config = ScratchifyConfig(revealFullAtPercent = 0.5f),
            contentMessage = "🚀 Super Prize!",
            overlayMessage = "Half Way!"
        )

        // 6. Bright Blue Paint Mode
        ScratchCardExample(
            title = "💙 Blue Paint Effect",
            description = "Bright blue brush with high opacity",
            config = ScratchifyConfig(
                brushConfig = ScratchifyBrushConfig(
                    brushSize = 40.dp,
                    brushColor = Color.Cyan,
                    opacity = 0.9f
                )
            ),
            contentMessage = "🌊 Ocean Prize!",
            overlayMessage = "Paint Blue!"
        )

        // 7. Instant Reveal Feature
        InstantRevealExample()

        // 8. Tap to Scratch Feature
        ScratchCardExample(
            title = "👆 Tap to Scratch",
            description = "Single taps create scratch marks",
            config = ScratchifyConfig(
                enableTapToScratch = true,
                brushConfig = ScratchifyBrushConfig(brushSize = 20.dp)
            ),
            contentMessage = "🎯 Tap Magic!",
            overlayMessage = "Tap Here!"
        )

        // 9. High Performance Mode
        ScratchCardExample(
            title = "⚡ Performance Mode",
            description = "Lower grid resolution for better performance",
            config = ScratchifyConfig(
                gridResolution = 75, // Lower resolution = better performance
                brushConfig = ScratchifyBrushConfig(brushSize = 35.dp)
            ),
            contentMessage = "🚀 Fast & Smooth!",
            overlayMessage = "Performance!"
        )

        // 10. Save/Restore State Example
        SaveRestoreExample()

        // 11. Haptic Feedback Example
        ScratchCardExample(
            title = "📳 Haptic Feedback",
            description = "Feel the vibrations while scratching",
            config = ScratchifyConfig(
                hapticConfig = ScratchifyHapticConfig(
                    isEnabled = true,
                    onScratchStarted = HapticFeedbackType.LIGHT,
                    onScratchProgress = HapticFeedbackType.LIGHT,
                    onScratchCompleted = HapticFeedbackType.SUCCESS,
                    progressHapticInterval = 0.2f
                )
            ),
            contentMessage = "📳 Feel the Power!",
            overlayMessage = "Haptic Scratch!"
        )

        // 12. Animated Reveals Example
        ScratchCardExample(
            title = "✨ Animated Reveals",
            description = "Beautiful reveal animations",
            config = ScratchifyConfig(
                animationConfig = ScratchifyAnimationConfig(
                    revealAnimationType = RevealAnimationType.BOUNCE,
                    animationDurationMs = 800
                )
            ),
            contentMessage = "🎭 Animated Magic!",
            overlayMessage = "Watch the Animation!"
        )

        // 13. Custom Brush Shapes Example
        CustomBrushShapesExample()

        // 14. Advanced Features Example
        ScratchCardExample(
            title = "🔧 Event Callbacks",
            description = "Demonstrates progress tracking",
            config = ScratchifyConfig(),
            contentMessage = "📊 Analytics!",
            overlayMessage = "Track Events!"
        )
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ScratchCardExample(
    title: String,
    description: String,
    config: ScratchifyConfig,
    contentMessage: String,
    overlayMessage: String
) {
    val controller = remember { ScratchifyController(config) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C)
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF7B1FA2)
            )
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Scratchify(
                    modifier = Modifier.fillMaxSize(),
                    config = config,
                    controller = controller,
                    contentToReveal = { 
                        BeautifulContentToReveal(contentMessage) 
                    },
                    overlayContent = { 
                        BeautifulOverlayContent(overlayMessage) 
                    }
                )
            }
            
            // Progress indicator
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF7B1FA2),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${(controller.scratchProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF7B1FA2),
                        fontWeight = FontWeight.Bold
                    )
                }
                LinearProgressIndicator(
                    progress = { controller.scratchProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFF9C27B0),
                    trackColor = Color(0xFFE1BEE7)
                )
            }
        }
    }
}


@Composable
fun BeautifulContentToReveal(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFD700), // Gold
                        Color(0xFFFFA000), // Amber
                        Color(0xFFFF8F00)  // Orange
                    ),
                    radius = 300f
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎊 CONGRATULATIONS! 🎊",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BeautifulOverlayContent(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF8E24AA), // Purple
                        Color(0xFF7B1FA2), // Deep Purple
                        Color(0xFF6A1B9A)  // Darker Purple
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "✨ SCRATCH TO WIN ✨",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "👆 Drag your finger here",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ContentToReveal(message: String) {
    BeautifulContentToReveal(message)
}

@Composable
fun OverlayContent(message: String) {
    BeautifulOverlayContent(message)
}


@Composable
fun InstantRevealExample() {
    val controller = remember { ScratchifyController() }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "🚀 Instant Reveal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C)
            )
            
            Text(
                text = "Press the button to instantly reveal content",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF7B1FA2)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { controller.revealInstantly() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reveal Instantly")
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Button(
                    onClick = { controller.resetScratch() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset")
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Scratchify(
                    modifier = Modifier.fillMaxSize(),
                    config = ScratchifyConfig(),
                    controller = controller,
                    contentToReveal = { 
                        BeautifulContentToReveal("⚡ Instantly Revealed!") 
                    },
                    overlayContent = { 
                        BeautifulOverlayContent("Instant Magic!") 
                    }
                )
            }
            
            LinearProgressIndicator(
                progress = { controller.scratchProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF9C27B0),
                trackColor = Color(0xFFE1BEE7)
            )
        }
    }
}

@Composable
fun SaveRestoreExample() {
    val controller = remember { ScratchifyController() }
    var savedState by remember { mutableStateOf<ScratchState?>(null) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "💾 Save/Restore State",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C)
            )
            
            Text(
                text = "Scratch, save state, reset, then restore",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF7B1FA2)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { savedState = controller.saveState() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = { 
                        savedState?.let { controller.restoreState(it) }
                    },
                    enabled = savedState != null,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Restore")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = { controller.resetScratch() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset")
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Scratchify(
                    modifier = Modifier.fillMaxSize(),
                    config = ScratchifyConfig(),
                    controller = controller,
                    contentToReveal = { 
                        BeautifulContentToReveal("💾 State Saved!") 
                    },
                    overlayContent = { 
                        BeautifulOverlayContent("Save & Restore!") 
                    }
                )
            }
            
            Text(
                text = if (savedState != null) "✅ State saved (${(savedState!!.scratchProgress * 100).toInt()}%)" else "❌ No saved state",
                style = MaterialTheme.typography.bodySmall,
                color = if (savedState != null) Color(0xFF4CAF50) else Color(0xFF9E9E9E),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomBrushShapesExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "🎨 Custom Brush Shapes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C)
            )
            
            Text(
                text = "Try different brush shapes for unique effects",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF7B1FA2)
            )
            
            // Row of different brush shape examples
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Star brush
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Scratchify(
                        modifier = Modifier.fillMaxSize(),
                        config = ScratchifyConfig(
                            brushConfig = ScratchifyBrushConfig(
                                brushSize = 25.dp,
                                brushShape = BrushShape.Star(5),
                                brushColor = Color.Transparent
                            )
                        ),
                        controller = remember { ScratchifyController() },
                        contentToReveal = { 
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color(0xFFFFD700)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("⭐", fontSize = 24.sp)
                            }
                        },
                        overlayContent = { 
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color(0xFF8E24AA)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Star", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    )
                }
                
                // Heart brush
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Scratchify(
                        modifier = Modifier.fillMaxSize(),
                        config = ScratchifyConfig(
                            brushConfig = ScratchifyBrushConfig(
                                brushSize = 20.dp,
                                brushShape = BrushShape.Heart,
                                brushColor = Color.Transparent
                            )
                        ),
                        controller = remember { ScratchifyController() },
                        contentToReveal = { 
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color(0xFFE91E63)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("💖", fontSize = 24.sp)
                            }
                        },
                        overlayContent = { 
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color(0xFF8E24AA)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Heart", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    )
                }
                
                // Square brush
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Scratchify(
                        modifier = Modifier.fillMaxSize(),
                        config = ScratchifyConfig(
                            brushConfig = ScratchifyBrushConfig(
                                brushSize = 18.dp,
                                brushShape = BrushShape.Square,
                                brushColor = Color.Transparent
                            )
                        ),
                        controller = remember { ScratchifyController() },
                        contentToReveal = { 
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color(0xFF4CAF50)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("⬛", fontSize = 24.sp)
                            }
                        },
                        overlayContent = { 
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color(0xFF8E24AA)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Square", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScratchifyAppPreview() {
    ScratchifyTheme {
        ScratchifyApp()
    }
}