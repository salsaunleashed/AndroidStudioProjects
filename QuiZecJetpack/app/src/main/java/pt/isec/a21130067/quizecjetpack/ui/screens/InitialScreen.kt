package pt.isec.a21130067.quizecjetpack.ui.screens

import JoinScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isec.a21130067.quizecjetpack.R
import pt.isec.a21130067.quizecjetpack.ui.theme.AzulClaro
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme
import pt.isec.a21130067.quizecjetpack.ui.theme.Vermelho
import pt.isec.a21130067.quizecjetpack.utils.AuthManager

class InitialScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "initial_screen"
                ) {
                    composable("initial_screen") { Greeting(navController) }
                    composable("login_register_screen") { RegisterAndLoginScreen(navController) }
                    composable("join_screen") { JoinScreen(navController) }
                    composable("create_quiz_screen") { CreateQuizScreen(navController)}
                }

            }
        }
    }
}


@Composable
fun Greeting(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFADD8E6))
                .padding(50.dp)
        ) {
            Text(
                text = "QuiZec",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.quizec_logo_no_background),
                contentDescription = "Logo Quizec",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                navController.navigate("create_quiz_screen")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Create Quizz")
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                navController.navigate("join_screen")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Join Quizz")
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                navController.navigate("join_screen")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Recent Quizzes")
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                AuthManager.logoutUser()
                navController.navigate("register_login_screen")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Vermelho,
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Logout")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuiZecJetpackTheme {
        val navController = rememberNavController()
        Greeting(navController)
    }
}