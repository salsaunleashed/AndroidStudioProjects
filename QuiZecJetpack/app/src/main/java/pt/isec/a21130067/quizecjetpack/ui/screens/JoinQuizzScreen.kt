import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import pt.isec.a21130067.quizecjetpack.ui.screens.LobbyScreen
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme
import pt.isec.a21130067.quizecjetpack.utils.AuthManager.getCurrentUserEmail
import pt.isec.a21130067.quizecjetpack.utils.FirestoreManager
import pt.isec.a21130067.quizecjetpack.utils.FirestoreManager.addPlayerToLobby

class JoinQuizzScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "join_screen"
                ) {
                    composable("lobby_screen/{quizCode}"){ backStackEntry ->
                        val quizCode = backStackEntry.arguments?.getString("quizCode") ?: ""
                        LobbyScreen(navController, quizCode, false)
                    }
                    composable("join_screen") {
                        JoinScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun JoinScreen(navController: NavHostController) {
    var quizCode by remember { mutableStateOf("")}
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Quiz Code",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = quizCode,
            onValueChange = { quizCode = it },
            label = { Text("Quiz Code") },
            placeholder = { Text("Enter 6-character code") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (quizCode.length == 6) {
                    val nome = FirebaseAuth.getInstance().currentUser?.email ?: "Player"
                    addPlayerToLobby(quizCode, nome) { success, error ->
                        if (success) {
                            val isHost = false;
                            navController.navigate("lobby_screen/$quizCode/$isHost")
                        } else {
                            errorMessage = error
                        }
                    }

                }
                else{
                    errorMessage = "That quiz doesnt exist"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Join Quiz")
        }

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuiZecJetpackTheme {
        val navController = rememberNavController()
        JoinScreen(navController)
    }
}