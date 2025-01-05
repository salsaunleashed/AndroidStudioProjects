package pt.isec.a21130067.quizecjetpack.ui.screens
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.firestore.FirebaseFirestore
import pt.isec.a21130067.quizecjetpack.ui.theme.Azulbebe
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme

class Lobby : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "lobby_screen"
                ) {
                    composable(
                        route = "lobby_screen/{quizId}/{isHost}",
                        arguments = listOf(
                            navArgument("quizId") { type = NavType.StringType },
                            navArgument("isHost") { type = NavType.BoolType }
                        )
                    ) { backStackEntry ->
                        val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
                        val isHost = backStackEntry.arguments?.getBoolean("isHost") ?: false
                        LobbyScreen(navController, quizId, isHost)
                    }

                }
            }
        }
    }
}

@Composable
fun LobbyScreen(navController: NavHostController, quizCode: String, isHost: Boolean) {
    val players = remember { mutableStateListOf<String>() }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Listener para atualizar jogadores em tempo real
    LaunchedEffect(quizCode) {
        val db = FirebaseFirestore.getInstance()
        db.collection("quizzes")
            .document(quizCode)
            .collection("players")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    errorMessage.value = "Error loading players: ${e.message}"
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    players.clear()
                    players.addAll(snapshot.documents.mapNotNull { it.getString("name") })
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "QUIZ LOBBY",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Quiz Code: $quizCode",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(players) { player ->
                        Text(
                            text = player,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isHost) {
                Button(
                    onClick = {
                        navController.navigate("quiz_screen/$quizCode")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Start Quiz")
                }
            } else {
                Text(
                    text = "Waiting for the host to start the quiz...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        errorMessage.value?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


