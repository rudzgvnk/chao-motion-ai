package com.chaomotion.ai

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

private val Background = Color(0xFF0B0D12)
private val Panel = Color(0xFF171A23)
private val Accent = Color(0xFF8B5CF6)
private val TextWhite = Color(0xFFF4F4F5)
private val TextMuted = Color(0xFF9CA3AF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = android.graphics.Color.rgb(11, 13, 18)
        window.navigationBarColor = android.graphics.Color.rgb(11, 13, 18)

        setContent {
            MaterialTheme {
                ChaoMotionEditor()
            }
        }
    }
}

@Composable
fun ChaoMotionEditor() {
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        videoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "CHAO MOTION",
                    color = TextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "AI VIDEO STUDIO",
                    color = Accent,
                    fontSize = 11.sp,
                    letterSpacing = 2.sp
                )
            }

            Surface(
                color = Panel,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "V1.0",
                    color = TextWhite,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "VIDEO PREVIEW",
            color = TextMuted,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Black,
            shape = RoundedCornerShape(18.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val context = androidx.compose.ui.platform.LocalContext.current

                if (videoUri != null) {
                    val player = remember(videoUri) {
                        ExoPlayer.Builder(context).build().apply {
                            setMediaItem(MediaItem.fromUri(videoUri!!))
                            prepare()
                        }
                    }

                    DisposableEffect(player) {
                        onDispose { player.release() }
                    }

                    AndroidView(
                        factory = { ctx ->
                            PlayerView(ctx).apply {
                                this.player = player
                                useController = true
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("▶", color = Accent, fontSize = 54.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Belum ada video",
                            color = TextWhite,
                            font
