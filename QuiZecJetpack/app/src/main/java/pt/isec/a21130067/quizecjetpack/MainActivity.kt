package pt.isec.a21130067.quizecjetpack

import JoinScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.isec.a21130067.quizecjetpack.ui.screens.CreateQuestionScreen
import pt.isec.a21130067.quizecjetpack.ui.screens.CreateQuizScreen
import pt.isec.a21130067.quizecjetpack.ui.screens.Greeting
import pt.isec.a21130067.quizecjetpack.ui.screens.InitialAnimation
import pt.isec.a21130067.quizecjetpack.ui.screens.LobbyScreen
import pt.isec.a21130067.quizecjetpack.ui.screens.RegisterAndLoginScreen
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme

class MainActivity : ComponentActivity() {
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
                    composable("initial_screen") { Greeting(navController) }
                    composable("join_screen") {JoinScreen(navController)}
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
                    composable("create_quiz_screen") { CreateQuizScreen(navController) }
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
