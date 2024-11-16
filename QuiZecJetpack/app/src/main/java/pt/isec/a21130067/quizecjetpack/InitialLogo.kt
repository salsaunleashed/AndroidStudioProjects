package pt.isec.a21130067.quizecjetpack

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme

class InitialLogoType(navController: NavHostController) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicial_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

@Composable
fun InitialLogo() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { InitialAnimation(navController) }
        composable("initial_screen") { Greeting() }
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

        Image(
            painter = painterResource(id = R.drawable.quizec_logo_no_background),
            contentDescription = "Logo",
            modifier = Modifier.graphicsLayer(alpha = alpha)
                .align(Alignment.Center)
        )

        if (startFadeOut && alpha == 0f) {
            LaunchedEffect(Unit) {
                navController.navigate("initial_screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialLogoPreview() {
    QuiZecJetpackTheme {
        InitialLogo()
    }
}
