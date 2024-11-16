package pt.isec.a21130067.quizecjetpack

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuiZecJetpackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    InicialAnimation(painterResource(id = R.drawable.quizec_logo_no_background))
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun InicialAnimation(painter: Painter) {
    var visible by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (visible) 0f else 1f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                visible = true
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(alpha = alpha)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InicialAnimationPreview() {
    InicialAnimation(painterResource(id = R.drawable.quizec_logo_no_background))
}
