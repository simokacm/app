package com.mindfullock.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val BgDark = Color(0xFF0A0A0F)
val CardDark = Color(0xFF15151F)
val PurplePrimary = Color(0xFF8264FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MindfulLockFrenchApp()
            }
        }
    }
}

@Composable
fun MindfulLockFrenchApp() {
    var selectedDays by remember { mutableStateOf(setOf(1, 2, 3, 4, 5)) }
    var limitMinutes by remember { mutableStateOf(30) }
    var remindBefore by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(20.dp)
    ) {
        Text("Limites quotidiennes", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            "Temps d'utilisation autorisé chaque jour pour une app",
            color = Color.Gray,
            fontSize = 13.sp
        )
        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CardDark)
                .padding(16.dp)
        ) {
            Text("Limite quotidienne : $limitMinutes min", color = Color.White, fontWeight = FontWeight.SemiBold)
            Slider(
                value = limitMinutes.toFloat(),
                onValueChange = { limitMinutes = it.toInt() },
                valueRange = 5f..180f
            )
            Spacer(Modifier.height(12.dp))
            Text("Répéter le (Jours d'application)", color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))

            val days = listOf(1 to "L", 2 to "M", 3 to "M", 4 to "J", 5 to "V", 6 to "S", 7 to "D")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                days.forEach { (id, label) ->
                    val active = selectedDays.contains(id)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (active) PurplePrimary else Color(0xFF222232))
                            .clickable {
                                selectedDays = if (active && selectedDays.size > 1) selectedDays - id else selectedDays + id
                            }
                    ) {
                        Text(label, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Rappel", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Text("Préviens-moi 5 minutes avant la limite", color = Color.Gray, fontSize = 12.sp)
                }
                Switch(checked = remindBefore, onCheckedChange = { remindBefore = it })
            }
        }
    }
}
