package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BuyerHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            HalliSantheTheme {

                BuyerHomeScreen()
            }
        }
    }
}

// PRODUCT MODEL
data class BuyerProduct(

    val name: String,

    val description: String,

    val price: String,

    val image: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerHomeScreen() {

    val context = LocalContext.current

    // DUMMY PRODUCTS
    val productList = listOf(

        BuyerProduct(
            name = "Handmade Basket",
            description = "Eco-friendly bamboo basket",
            price = "₹499",
            image = R.drawable.img_1
        ),

        BuyerProduct(
            name = "Clay Pot",
            description = "Traditional clay pottery",
            price = "₹299",
            image = R.drawable.img
        ),

        BuyerProduct(
            name = "Wooden Toy",
            description = "Handcrafted wooden toy",
            price = "₹399",
            image = R.drawable.img_2
        ),

        BuyerProduct(
            name = "Jute Bag",
            description = "Reusable handmade bag",
            price = "₹699",
            image = R.drawable.img_3
        ),

        BuyerProduct(
            name = "Decor Lamp",
            description = "Traditional handmade lamp",
            price = "₹999",
            image = R.drawable.img_4
        ),

        BuyerProduct(
            name = "Village Wall Art",
            description = "Handmade wall painting",
            price = "₹1499",
            image = R.drawable.img_5
        )
    )

    Scaffold(

        // TOP BAR
        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Halli Santhe"
                    )
                },

                actions = {

                    IconButton(
                        onClick = {

                            context.startActivity(
                                Intent(
                                    context,
                                    CartActivity::class.java
                                )
                            )
                        }
                    ) {

                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null
                        )
                    }
                }
            )
        },

        // BOTTOM NAVIGATION
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

                    onClick = {

                        context.startActivity(
                            Intent(
                                context,
                                CategoryActivity::class.java
                            )
                        )
                    },

                    icon = {
                        Icon(Icons.Default.List, null)
                    },

                    label = {
                        Text("Category")
                    }
                )

                NavigationBarItem(

                    selected = false,

                    onClick = {

                        context.startActivity(
                            Intent(
                                context,
                                CartActivity::class.java
                            )
                        )
                    },

                    icon = {
                        Icon(Icons.Default.ShoppingCart, null)
                    },

                    label = {
                        Text("Cart")
                    }
                )

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
                .padding(16.dp)

        ) {

            // TITLE
            Text(
                text = "Handcrafted Products",

                fontSize = 28.sp,

                fontWeight = FontWeight.Bold,

                color = ForestGreen
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // PRODUCT GRID
            LazyVerticalGrid(

                columns = GridCells.Fixed(2),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                items(productList) { product ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                // NAVIGATION
                                context.startActivity(

                                    Intent(
                                        context,
                                        ProductDetailActivity::class.java
                                    )
                                )
                            },

                        shape =
                            RoundedCornerShape(18.dp)

                    ) {

                        Column {

                            // IMAGE
                            Image(
                                painter =
                                    painterResource(
                                        id = product.image
                                    ),

                                contentDescription = null,

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),

                                contentScale =
                                    ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {

                                // NAME
                                Text(
                                    text = product.name,

                                    fontSize = 18.sp,

                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(4.dp)
                                )

                                // DESCRIPTION
                                Text(
                                    text = product.description,

                                    fontSize = 13.sp,

                                    maxLines = 2
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(8.dp)
                                )

                                // PRICE
                                Text(
                                    text = product.price,

                                    fontSize = 18.sp,

                                    color = ForestGreen,

                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}