package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext

// 🎨 Colors
val ForestGreen = Color(0xFF2A5934)
val CreamBackground = Color(0xFFFAF8F5)
val Terracotta = Color(0xFFB56543)
val DarkGrey = Color(0xFF333333)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HalliSantheTheme {

                val context = LocalContext.current

                SplashScreen {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)

                    // 🔥 IMPORTANT: finish activity safely
                    (context as? ComponentActivity)?.finish()
                }
            }
        }
    }
}

@Composable
fun HalliSantheTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = ForestGreen,
            background = CreamBackground
        ),
        content = content
    )
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    // ⏳ Delay (3 seconds)
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CreamBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            // 🖼️ LOGO (Your Image)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

//            Spacer(modifier = Modifier.height(20.dp))

            // 🏷️ App Name
            Text(
                text = "Halli-Santhe",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🌟 Tagline
            Text(
                text = "\"Vocal for Local\"",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Terracotta
            )

            Spacer(modifier = Modifier.weight(1f))

            // ⏳ Loader
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(MaterialTheme.shapes.medium),
                    color = ForestGreen,
                    trackColor = Color(0xFFE0E0E0)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "[ Loading... ]",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}