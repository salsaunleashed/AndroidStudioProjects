package pt.isec.a21130067.quizecjetpack.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isec.a21130067.quizecjetpack.ui.theme.AzulClaro
import pt.isec.a21130067.quizecjetpack.ui.theme.Azulbebe
import pt.isec.a21130067.quizecjetpack.ui.theme.QuiZecJetpackTheme


class RegisterAndLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuiZecJetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "register_login_screen"
                ) {
                    composable("register_login_screen") {
                        RegisterAndLoginScreen(navController)
                    }
                    composable("initial_screen") {
                        Greeting(navController)
                    }
                    composable("main_screen"){
                        InitialAnimation(navController)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
    @Composable
    fun RegisterAndLoginScreenPreview() {
        QuiZecJetpackTheme {
            RegisterAndLoginScreen(rememberNavController())
        }
    }

    @Composable
    fun RegisterAndLoginScreen(navController: NavHostController) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var isRegisterMode by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Azulbebe)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isRegisterMode) "Register" else "Login",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (isRegisterMode) {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    placeholder = { Text("Re-enter your password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isRegisterMode) {
                        navController.navigate("initial_screen")
                    } else {
                        // Lógica de registo
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isRegisterMode) "Register" else "Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { isRegisterMode = !isRegisterMode }) {
                Text(
                    text = if (isRegisterMode)
                        "Already have an account? Login"
                    else
                        "Don't have an account? Register",
                    color = AzulClaro
                )
            }
        }
    }

