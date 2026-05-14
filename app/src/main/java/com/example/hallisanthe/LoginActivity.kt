package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HalliSantheTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CreamBackground
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",

                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome Back",
                fontSize = 24.sp,
                color = DarkGrey
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            OutlinedTextField(
                value = email,

                onValueChange = {
                    email = it
                },

                label = {
                    Text("Email")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = password,

                onValueChange = {
                    password = it
                },

                label = {
                    Text("Password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Login Button
            Button(

                onClick = {

                    // Validation
                    if (
                        email.isNotEmpty() &&
                        password.isNotEmpty()
                    ) {

                        // Firebase Login
                        FirebaseManager.auth
                            .signInWithEmailAndPassword(
                                email,
                                password
                            )

                            .addOnCompleteListener { task ->

                                if (task.isSuccessful) {

                                    // Current User ID
                                    val userId =
                                        FirebaseManager
                                            .auth
                                            .currentUser
                                            ?.uid

                                    if (userId != null) {

                                        // Get Role
                                        FirebaseManager
                                            .firestore
                                            .collection("users")
                                            .document(userId)
                                            .get()

                                            .addOnSuccessListener { document ->

                                                val role =
                                                    document.getString("role")

                                                Toast.makeText(
                                                    context,
                                                    "Login Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Navigate
                                                if (role == "buyer") {

                                                    context.startActivity(
                                                        Intent(
                                                            context,
                                                            BuyerHomeActivity::class.java
                                                        )
                                                    )

                                                } else {

                                                    context.startActivity(
                                                        Intent(
                                                            context,
                                                            ArtisanHomeActivity::class.java
                                                        )
                                                    )
                                                }

                                                // Close Screen
                                                (context as? ComponentActivity)
                                                    ?.finish()
                                            }
                                    }

                                } else {

                                    Toast.makeText(
                                        context,
                                        task.exception?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                    } else {

                        Toast.makeText(
                            context,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen
                )
            ) {

                Text(
                    text = "Login",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Register Navigation
            Text(
                text = "Don't have an account? Register",

                color = ForestGreen,

                modifier = Modifier.clickable {

                    context.startActivity(
                        Intent(
                            context,
                            RegisterActivity::class.java
                        )
                    )
                }
            )
        }
    }
}