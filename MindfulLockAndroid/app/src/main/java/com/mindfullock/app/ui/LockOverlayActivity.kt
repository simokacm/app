package com.mindfullock.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LockOverlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appName = intent.getStringExtra("APP_NAME") ?: "Instagram"
        val limitMinutes = intent.getIntExtra("LIMIT_MINUTES", 30)

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF09090F))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "• LIMITE QUOTIDIENNE ATTEINTE",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF1B1B29))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Limite de $appName atteinte", color = Color.White, fontSize = 13.sp)
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8264FF))
                ) {
                    Text("🔒", fontSize = 42.sp)
                }

                Text(
                    text = "L'envie de vérifier s'estompe en général en une minute. C'est cette minute.",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Ta limite quotidienne de $limitMinutes min · réinitialisée à minuit",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val home = Intent(Intent.ACTION_MAIN).apply {
                                addCategory(Intent.CATEGORY_HOME)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(home)
                            finish()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8264FF))
                    ) {
                        Text("Inspire · Fermer l'application")
                    }
                }
            }
        }
    }
}
