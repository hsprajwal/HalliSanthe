package com.example.hallisanthe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ProductDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HalliSantheTheme {
                ProductDetailScreen()
            }
        }
    }
}

@Composable
fun ProductDetailScreen() {

    var quantity by remember {
        mutableStateOf(1)
    }

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Home, null)
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                    label = {
                        Text("Add")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Inventory2, null)
                    },
                    label = {
                        Text("Products")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Person, null)
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { }) {
                    Icon(Icons.Default.ArrowBack, null)
                }

                BadgedBox(
                    badge = {
                        Badge {
                            Text("2")
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = ForestGreen,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // PRODUCT IMAGE
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TITLE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Handcrafted Coaster",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGrey
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Handcraft Items",
                        fontSize = 20.sp,
                        color = ForestGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = DarkGrey,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DESCRIPTION
            Text(
                text = "Beautifully handcrafted coaster made with natural jute and cotton. Perfect for your home or as a thoughtful gift.",
                fontSize = 18.sp,
                color = Color.Gray,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // PRICE SECTION
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "₹249",
                        fontSize = 36.sp,
                        color = ForestGreen,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "₹349",
                        fontSize = 24.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFFE8F5E9),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    Text(
                        text = "In Stock",
                        color = ForestGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            // FEATURES TITLE
            Text(
                text = "Key Features",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // FEATURES CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF6F1FA)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    FeatureItem(
                        icon = Icons.Default.Handshake,
                        title = "Handmade",
                        subtitle = "with love"
                    )

                    FeatureItem(
                        icon = Icons.Default.Eco,
                        title = "Natural",
                        subtitle = "Materials"
                    )

                    FeatureItem(
                        icon = Icons.Default.StarBorder,
                        title = "Unique",
                        subtitle = "Design"
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // QUANTITY
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Quantity",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF4F0F6))
                ) {

                    IconButton(
                        onClick = {
                            if (quantity > 1) {
                                quantity--
                            }
                        }
                    ) {
                        Icon(Icons.Default.Remove, null)
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    IconButton(
                        onClick = {
                            quantity++
                        }
                    ) {
                        Icon(Icons.Default.Add, null)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // ADD TO CART BUTTON
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen
                )
            ) {

                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Add to Cart",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FeatureItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = ForestGreen,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = subtitle,
            color = Color.Gray,
            fontSize = 16.sp
        )
    }
}