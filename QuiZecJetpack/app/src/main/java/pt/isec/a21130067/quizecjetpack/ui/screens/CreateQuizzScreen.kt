package pt.isec.a21130067.quizecjetpack.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.isec.a21130067.quizecjetpack.data.model.Question
import pt.isec.a21130067.quizecjetpack.data.model.Quiz
import pt.isec.a21130067.quizecjetpack.ui.theme.Azulbebe
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme
import pt.isec.a21130067.quizecjetpack.utils.FirestoreManager.createQuiz
import pt.isec.a21130067.quizecjetpack.utils.FirestoreManager.getQuizById

class CreateScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "create_quiz_screen"
                ) {
                    composable(
                        route = "lobby_screen",
                        arguments = listOf(
                            navArgument("quizId") { type = NavType.StringType },
                            navArgument("isHost") { type = NavType.BoolType }
                        )
                        ) {backStackEntry ->
                        val quizCode = backStackEntry.arguments?.getString("quizCode") ?: ""
                        LobbyScreen(navController, quizCode, true) }
                    composable("create_quiz_screen") {
                        CreateQuizScreen(navController)
                    }

                    composable(
                        route = "create_question_screen/{quizId}",
                        arguments = listOf(navArgument("quizId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
                        CreateQuestionScreen(navController, quizId)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateQuizScreen(navController: NavHostController) {
    var quizTitle by remember { mutableStateOf("") }
    var quizDescription by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFADD8E6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Create Quiz",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        TextField(
            value = quizTitle,
            onValueChange = { quizTitle = it },
            label = { Text("Quiz Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = quizDescription,
            onValueChange = { quizDescription = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (quizTitle.isNotBlank() && quizDescription.isNotBlank()) {
                    createQuiz(quizTitle, quizDescription) { success, quizId, error ->
                        if (success && quizId != null) {
                            navController.navigate("create_question_screen/$quizId")
                        } else {
                            errorMessage = error
                        }
                    }
                } else {
                    errorMessage = "Title and description are required"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Quiz")
        }

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    QuiZecJetpackTheme {
        val navController = rememberNavController()
        CreateQuizScreen(navController)
    }
}