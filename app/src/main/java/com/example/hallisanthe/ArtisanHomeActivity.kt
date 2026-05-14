package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ArtisanHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HalliSantheTheme {
                ArtisanHomeScreen()
            }
        }
    }
}

@Composable
fun ArtisanHomeScreen() {

    val context = LocalContext.current

    val currentUser = FirebaseManager.auth.currentUser

    val artisanEmail =
        currentUser?.email ?: "artisan@gmail.com"

    Scaffold(

        bottomBar = {

            NavigationBar {

                // HOME
                NavigationBarItem(

                    selected = true,

                    onClick = { },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Home,

                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Home")
                    }
                )

                // ADD PRODUCT
                NavigationBarItem(

                    selected = false,

                    onClick = {

                        context.startActivity(
                            Intent(
                                context,
                                AddProductActivity::class.java
                            )
                        )
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.AddBox,

                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Add")
                    }
                )

                // MY PRODUCTS
                NavigationBarItem(

                    selected = false,

                    onClick = {

                        context.startActivity(
                            Intent(
                                context,
                                MyProductsActivity::class.java
                            )
                        )
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Inventory,

                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Products")
                    }
                )

                // PROFILE
                NavigationBarItem(

                    selected = false,

                    onClick = {

                        context.startActivity(
                            Intent(
                                context,
                                UserActivity::class.java
                            )
                        )
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.AccountCircle,

                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {

            item {

                // HEADER
                Text(
                    text = "Welcome Artisan 👋",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestGreen
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = artisanEmail,
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                // DASHBOARD TITLE
                Text(
                    text = "Dashboard",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // STATS CARDS
                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    DashboardStatCard(
                        title = "Products",
                        value = "12"
                    )

                    DashboardStatCard(
                        title = "Orders",
                        value = "5"
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    DashboardStatCard(
                        title = "Revenue",
                        value = "₹4.5K"
                    )

                    DashboardStatCard(
                        title = "Views",
                        value = "320"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // QUICK ACTIONS
                Text(
                    text = "Quick Actions",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ADD PRODUCT CARD
                ActionCard(
                    title = "Add Product",
                    subtitle = "Upload handmade products"
                ) {

                    context.startActivity(
                        Intent(
                            context,
                            AddProductActivity::class.java
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // MY PRODUCTS CARD
                ActionCard(
                    title = "My Products",
                    subtitle = "Manage uploaded products"
                ) {

                    context.startActivity(
                        Intent(
                            context,
                            MyProductsActivity::class.java
                        )
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // RECENT PRODUCTS
                Text(
                    text = "Recent Products",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                repeat(3) {

                    ProductCard(
                        productName = "Handmade Pottery",
                        price = "₹499"
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(110.dp),

        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            verticalArrangement =
                Arrangement.Center
        ) {

            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ForestGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        onClick = onClick
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProductCard(
    productName: String,
    price: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = productName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = price,
                    color = ForestGreen,
                    fontSize = 16.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        ForestGreen.copy(alpha = 0.1f),
                        RoundedCornerShape(14.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Inventory,
                    contentDescription = null,
                    tint = ForestGreen
                )
            }
        }
    }
}