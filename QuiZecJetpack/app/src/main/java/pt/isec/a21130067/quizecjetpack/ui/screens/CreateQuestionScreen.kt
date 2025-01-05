package pt.isec.a21130067.quizecjetpack.ui.screens
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.isec.a21130067.quizecjetpack.data.model.Question
import pt.isec.a21130067.quizecjetpack.ui.theme.Azulbebe
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme
import pt.isec.a21130067.quizecjetpack.utils.FirestoreManager.saveQuestion

class CreateQuestionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "create_question_screen"
                ) {
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
                    composable(
                        route = "lobby_screen/{quizId}/{isHost}",
                        arguments = listOf(
                            navArgument("quizId") { type = NavType.StringType },
                            navArgument("isHost") { type = NavType.BoolType }
                        )
                    ) { backStackEntry ->
                        val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
                        LobbyScreen(navController, quizId, true)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateQuestionScreen(navController: NavHostController, quizId: String) {
    var questionText by remember { mutableStateOf("") }
    var questionType by remember { mutableStateOf("P01") }
    var multipleChoiceOptions by remember { mutableStateOf(mutableListOf<String>()) }
    var optionText by remember { mutableStateOf("") }
    var selectedCorrectAnswer by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6))
            .padding(50.dp)
    ) {
        Text(
            text = "Create Question",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF1565C0),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Type: $questionType")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        questionType = "P01"
                        selectedCorrectAnswer = null
                        expanded = false
                    },
                    text = { Text("P01 (Yes/No)") }
                )
                DropdownMenuItem(
                    onClick = {
                        questionType = "P02"
                        selectedCorrectAnswer = null
                        expanded = false
                    },
                    text = { Text("P02 (Multiple Choice)") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Question Text") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (questionType == "P02") {
            TextField(
                value = optionText,
                onValueChange = { optionText = it },
                label = { Text("Option Text") },
                placeholder = { Text("Add an option") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (optionText.isNotBlank()) {
                        multipleChoiceOptions = (multipleChoiceOptions + optionText).toMutableList()
                        optionText = ""
                    } else {
                        errorMessage = "Option text cannot be empty."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Option")
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(multipleChoiceOptions) { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedCorrectAnswer == option,
                            onCheckedChange = { selectedCorrectAnswer = option }
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        } else if (questionType == "P01") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedCorrectAnswer == "Yes",
                    onCheckedChange = { selectedCorrectAnswer = "Yes" }
                )
                Text("Yes", modifier = Modifier.padding(start = 8.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedCorrectAnswer == "No",
                    onCheckedChange = { selectedCorrectAnswer = "No" }
                )
                Text("No", modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (questionText.isNotBlank() &&
                    (questionType != "P02" || multipleChoiceOptions.isNotEmpty()) &&
                    selectedCorrectAnswer != null
                ) {
                    val question = mapOf(
                        "text" to questionText,
                        "type" to questionType,
                        "options" to if (questionType == "P02") multipleChoiceOptions else listOf("Yes", "No"),
                        "correctAnswer" to selectedCorrectAnswer
                    ).filterValues { it != null } as Map<String, Any>

                    saveQuestion(quizId, question) { success, error ->
                        if (success) {
                            questionText = ""
                            multipleChoiceOptions = mutableListOf()
                            selectedCorrectAnswer = null
                        } else {
                            errorMessage = error
                        }
                    }
                } else {
                    errorMessage = "All fields must be filled, and options are required for multiple choice."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Question")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val isHost = true;
                navController.navigate("lobby_screen/$quizId/$isHost")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Lobby")
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
