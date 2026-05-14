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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HalliSantheTheme {
                UserScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {

    val context = LocalContext.current

    val currentUser = FirebaseManager.auth.currentUser

    var userName by remember {
        mutableStateOf("Loading...")
    }

    val userEmail =
        currentUser?.email ?: "No Email"

    // Scroll State
    val scrollState = rememberScrollState()

    // Expand States
    var termsExpanded by remember {
        mutableStateOf(false)
    }

    var policyExpanded by remember {
        mutableStateOf(false)
    }

    var faqExpanded by remember {
        mutableStateOf(false)
    }

    var aboutExpanded by remember {
        mutableStateOf(false)
    }

    // Fetch Username
    LaunchedEffect(Unit) {

        val userId = currentUser?.uid

        if (userId != null) {

            FirebaseManager.firestore
                .collection("users")
                .document(userId)
                .get()

                .addOnSuccessListener { document ->

                    userName =
                        document.getString("username")
                            ?: "Halli-Santhe User"
                }
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("My Profile")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            (context as? android.app.Activity)?.finish()
                        }
                    ) {

                        Icon(
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
                .padding(16.dp)
                .verticalScroll(scrollState)
                .padding(bottom = 120.dp)
        ) {

            // PROFILE
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,

                    modifier = Modifier
                        .size(85.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(
                        text = userName,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = userEmail,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ACCOUNT SETTINGS
            SectionTitle("Account Settings")

            ProfileItem("Edit Profile") { }

            ProfileItem("Saved Address") {

                val intent =
                    Intent(context, AddressActivity::class.java)

                context.startActivity(intent)
            }

            ProfileItem("Notifications") { }

            ProfileItem("Privacy Center") { }

            Spacer(modifier = Modifier.height(24.dp))

            // FEEDBACK & INFORMATION
            SectionTitle("Feedback & Information")

            // TERMS
            ExpandableInfoCard(
                title = "Terms & Conditions",

                expanded = termsExpanded,

                onExpandChange = {
                    termsExpanded = !termsExpanded
                },

                content =
                    "Halli-Santhe supports local artisans through a secure and transparent marketplace. Buyers are encouraged to support handmade local products responsibly."
            )

            // POLICIES
            ExpandableInfoCard(
                title = "Policies & Licenses",

                expanded = policyExpanded,

                onExpandChange = {
                    policyExpanded = !policyExpanded
                },

                content =
                    "All products listed on Halli-Santhe are uploaded by independent artisans. The platform promotes authentic handcrafted and rural products."
            )

            // FAQ
            ExpandableInfoCard(
                title = "Browse FAQ",

                expanded = faqExpanded,

                onExpandChange = {
                    faqExpanded = !faqExpanded
                },

                content =
                    "Q: How do I place an order?\n\nBrowse products and add items to cart.\n\nQ: Can artisans upload products?\n\nYes. Artisans can manage products from their dashboard."
            )

            // ABOUT
            ExpandableInfoCard(
                title = "About Halli-Santhe",

                expanded = aboutExpanded,

                onExpandChange = {
                    aboutExpanded = !aboutExpanded
                },

                content =
                    "Halli-Santhe is a hyper-local marketplace built to empower rural artisans and promote the 'Vocal for Local' initiative using digital technology."
            )

            Spacer(modifier = Modifier.height(24.dp))

            // APP INFORMATION
            SectionTitle("App Information")

            InfoCard(
                title = "App Version",
                value = "1.0.0"
            )

            InfoCard(
                title = "Developer",
                value = "Code with Manoj"
            )

            InfoCard(
                title = "Platform",
                value = "Halli-Santhe Marketplace"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // LOGOUT
            LogoutItem {

                FirebaseManager.auth.signOut()

                val intent =
                    Intent(context, LoginActivity::class.java)

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {

    Text(
        text = title,

        style = MaterialTheme.typography.titleMedium,

        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun ProfileItem(
    title: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        onClick = onClick
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(text = title)

            Icon(
                imageVector =
                    Icons.Default.KeyboardArrowRight,

                contentDescription = null
            )
        }
    }
}

@Composable
fun ExpandableInfoCard(
    title: String,
    content: String,
    expanded: Boolean,
    onExpandChange: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        onClick = onExpandChange
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    fontSize = 16.sp
                )

                Icon(
                    imageVector =
                        if (expanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,

                    contentDescription = null
                )
            }

            if (expanded) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = content,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(text = title)

            Text(text = value)
        }
    }
}

@Composable
fun LogoutItem(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.errorContainer
        ),

        onClick = onClick
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Logout",

                color =
                    MaterialTheme.colorScheme.error
            )
        }
    }
}