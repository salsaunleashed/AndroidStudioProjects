package pt.isec.a21130067.quizec

import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isec.a21130067.quizec.ui.theme.QuiZecTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuiZecTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Greeting()
                }
            }

        }
    }
}

@Composable
fun Greeting() {

    Box ( modifier = Modifier.fillMaxSize()){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            ){

        Text(
            text = "QuiZec",
            style = TextStyle(
                color = Color.Blue,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        )

        Image(
            painter = painterResource(id = R.drawable.quizec_logo),
            contentDescription = "QuiZec logo!",
            modifier = Modifier.size(250.dp)
        )

        Button(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Creator")
        }

        Button(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "User")
        }
    }
        }
}

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Greeting()
    }


