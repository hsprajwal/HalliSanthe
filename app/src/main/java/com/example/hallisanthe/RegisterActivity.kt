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

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HalliSantheTheme {
                RegisterScreen()
            }
        }
    }
}

@Composable
fun RegisterScreen() {

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Default Role
    var role by remember { mutableStateOf("buyer") }

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
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Create Account",
                fontSize = 24.sp,
                color = DarkGrey
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Username
            OutlinedTextField(
                value = username,

                onValueChange = {
                    username = it
                },

                label = {
                    Text("Username")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(15.dp))

            // Role Selection
            Text(
                text = "Select Role",
                color = DarkGrey
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceEvenly
            ) {

                // Buyer Button
                Button(
                    onClick = {
                        role = "buyer"
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (role == "buyer")
                                ForestGreen
                            else
                                Color.LightGray
                    )
                ) {

                    Text(
                        text = "Buyer",
                        color = Color.White
                    )
                }

                // Artisan Button
                Button(
                    onClick = {
                        role = "artisan"
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (role == "artisan")
                                ForestGreen
                            else
                                Color.LightGray
                    )
                ) {

                    Text(
                        text = "Artisan",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Register Button
            Button(

                onClick = {

                    // Validation
                    if (
                        username.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty()
                    ) {

                        // Firebase Register
                        FirebaseManager.auth
                            .createUserWithEmailAndPassword(
                                email,
                                password
                            )

                            .addOnCompleteListener { task ->

                                if (task.isSuccessful) {

                                    // User ID
                                    val userId =
                                        FirebaseManager
                                            .auth
                                            .currentUser
                                            ?.uid

                                    // User Data
                                    val userData = hashMapOf(
                                        "username" to username,
                                        "email" to email,
                                        "role" to role
                                    )

                                    // Save in Firestore
                                    if (userId != null) {

                                        FirebaseManager
                                            .firestore
                                            .collection("users")
                                            .document(userId)
                                            .set(userData)

                                            .addOnSuccessListener {

                                                Toast.makeText(
                                                    context,
                                                    "Registration Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Go To Login
                                                context.startActivity(
                                                    Intent(
                                                        context,
                                                        LoginActivity::class.java
                                                    )
                                                )

                                                // Close Register Screen
                                                (context as? ComponentActivity)
                                                    ?.finish()
                                            }

                                            .addOnFailureListener {

                                                Toast.makeText(
                                                    context,
                                                    "Firestore Error",
                                                    Toast.LENGTH_LONG
                                                ).show()
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
                    text = "Register",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Login Navigation
            Text(
                text = "Already have an account? Login",

                color = ForestGreen,

                modifier = Modifier.clickable {

                    context.startActivity(
                        Intent(
                            context,
                            LoginActivity::class.java
                        )
                    )
                }
            )
        }
    }
}