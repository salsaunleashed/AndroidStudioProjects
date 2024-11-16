package pt.isec.a21130067.quizecjetpack

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuiZecJetpackTheme {
                LoginScreenInsert()
            }
        }
    }
}

@Composable
fun LoginScreenInsert() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFADD8E6))
                .padding(30.dp)
        ) {
            Text(text = "Hi there!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenInsertPreview() {
    QuiZecJetpackTheme {
        LoginScreen()
    }
}