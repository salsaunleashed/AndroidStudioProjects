package pt.isec.a21130067.quizecjetpack.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isec.a21130067.quizecjetpack.R
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme

class InitialLogoType : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main_screen"
                ) {
                    composable("main_screen") { InitialAnimation(navController) }
                    composable("register_login_screen") { RegisterAndLoginScreen(navController) }
                }
            }
        }
    }
}

@Composable
fun InitialAnimation(navController: NavHostController) {
    var startFadeOut by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (startFadeOut) 0f else 1f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                startFadeOut = true
            }


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFADD8E6))
                .padding(30.dp)
        ) {
            Text(
                text = "Welcome to QuiZec",
                modifier = Modifier
                    .graphicsLayer(alpha = alpha)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge,
//                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.quizec_logo_no_background),
                contentDescription = "Logo",
                modifier = Modifier
                    .graphicsLayer(alpha = alpha)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "your quiZ partner!",
                modifier = Modifier
                    .graphicsLayer(alpha = alpha)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge,
//                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Text(
                text = "Click on screen continue",
                modifier = Modifier
                    .graphicsLayer(alpha = alpha)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
//                fontSize = 34.sp,
                color = Color.Black
            )

            if (startFadeOut && alpha == 0f) {
                LaunchedEffect(Unit) {
                    navController.navigate("register_login_screen")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InitialLogoPreview() {
    QuiZecJetpackTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "main_screen"
        ) {
            composable("main_screen") { InitialAnimation(navController) }
            composable("register_login_screen") { RegisterAndLoginScreen(navController) }
            composable("initial_screen") { Greeting(navController) }
        }
    }
}
