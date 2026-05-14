package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ArtisanProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            HalliSantheTheme {

                ArtisanProfileScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtisanProfileScreen() {

    val context = LocalContext.current

    val currentUser =
        FirebaseManager.auth.currentUser

    var artisanName by remember {
        mutableStateOf("Loading...")
    }

    val artisanEmail =
        currentUser?.email ?: "No Email"

    val scrollState =
        rememberScrollState()

    // Fetch Username
    LaunchedEffect(Unit) {

        val userId =
            currentUser?.uid

        if (userId != null) {

            FirebaseManager.firestore
                .collection("users")
                .document(userId)
                .get()

                .addOnSuccessListener { document ->

                    artisanName =
                        document.getString("username")
                            ?: "Artisan"
                }
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Artisan Profile")
                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            (context as? android.app.Activity)
                                ?.finish()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription = null
                        )
                    }
                }
            )
        }

    ) { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(scrollState)

        ) {

            // PROFILE IMAGE
            Box(
                modifier = Modifier.fillMaxWidth(),

                contentAlignment =
                    Alignment.Center
            ) {

                Image(
                    painter =
                        painterResource(
                            id = R.drawable.logo
                        ),

                    contentDescription = null,

                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // NAME
            Text(
                text = artisanName,

                fontSize = 28.sp,

                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // EMAIL
            Text(
                text = artisanEmail,

                fontSize = 16.sp,

                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // PROFILE DETAILS
            ProfileInfoCard(
                icon = Icons.Default.Email,
                title = "Email",
                value = artisanEmail
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            ProfileInfoCard(
                icon = Icons.Default.Phone,
                title = "Phone",
                value = "+91 9876543210"
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            ProfileInfoCard(
                icon = Icons.Default.LocationOn,
                title = "Location",
                value = "Karnataka, India"
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // ACTION BUTTONS
            Button(

                onClick = {

                    // TODO EDIT PROFILE
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Edit,

                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Edit Profile")
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedButton(

                onClick = {

                    FirebaseManager.auth.signOut()

                    val intent =
                        Intent(
                            context,
                            LoginActivity::class.java
                        )

                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    context.startActivity(intent)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Logout,

                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Logout")
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

@Composable
fun ProfileInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ForestGreen
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column {

                Text(
                    text = title,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = value,
                    fontSize = 18.sp
                )
            }
        }
    }
}