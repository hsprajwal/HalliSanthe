package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// PRODUCT MODEL
data class Product(

    val name: String,

    val price: Int,

    val image: Int
)

class CategoryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            HalliSantheTheme {

                CategoryScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen() {

    val context = LocalContext.current

    val categories = listOf(

        "Toys",
        "Pottery",
        "Bags",
        "Art",
        "Lamps",
        "Baskets"
    )

    var selectedCategory by remember {

        mutableStateOf("Toys")
    }

    Scaffold(

        // TOP BAR
        topBar = {

            TopAppBar(

                title = {
                    Text("Categories")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {

                            (context as? android.app.Activity)
                                ?.finish()
                        }
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            null
                        )
                    }
                }
            )
        },

        // BOTTOM NAVIGATION
        bottomBar = {

            NavigationBar {

                NavigationBarItem(

                    selected = false,

                    onClick = {

                        context.startActivity(

                            Intent(
                                context,
                                BuyerHomeActivity::class.java
                            )
                        )
                    },

                    icon = {
                        Icon(Icons.Default.Home, null)
                    },

                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(

                    selected = true,

                    onClick = { },

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

        Row(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {

            // LEFT CATEGORY LIST
            LazyColumn(

                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()

            ) {

                items(categories) { category ->

                    val isSelected =
                        category == selectedCategory

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),

                        colors = CardDefaults.cardColors(

                            containerColor =

                                if (isSelected)
                                    ForestGreen.copy(alpha = 0.2f)

                                else
                                    MaterialTheme.colorScheme.surface
                        ),

                        onClick = {

                            selectedCategory = category
                        }

                    ) {

                        Text(

                            text = category,

                            modifier = Modifier.padding(14.dp),

                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // RIGHT PRODUCT GRID
            val products =
                getProductsByCategory(selectedCategory)

            LazyVerticalGrid(

                columns = GridCells.Fixed(2),

                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                gridItems(products) { product ->

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
                            }

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
                                    .height(130.dp),

                                contentScale =
                                    ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {

                                // NAME
                                Text(

                                    text = product.name,

                                    fontWeight =
                                        FontWeight.Bold,

                                    fontSize = 18.sp
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(6.dp)
                                )

                                // PRICE
                                Text(

                                    text = "₹${product.price}",

                                    color = ForestGreen,

                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// DUMMY PRODUCTS
fun getProductsByCategory(
    category: String
): List<Product> {

    return when (category) {

        "Toys" -> listOf(

            Product(
                "Wooden Car",
                250,
                R.drawable.img
            ),

            Product(
                "Handmade Doll",
                350,
                R.drawable.img_1
            )

        )

        "Pottery" -> listOf(

            Product(
                "Clay Pot",
                299,
                R.drawable.img_5
            ),

            Product(
                "Decor Vase",
                499,
                R.drawable.img_4
            )
        )

        "Bags" -> listOf(

            Product(
                "Jute Bag",
                699,
                R.drawable.img_5
            )
        )

        "Art" -> listOf(

            Product(
                "Village Painting",
                1200,
                R.drawable.img_1
            )
        )

        "Lamps" -> listOf(

            Product(
                "Clay Lamp",
                350,
                R.drawable.img_2
            ),

            Product(
                "Decor Lantern",
                850,
                R.drawable.img_3
            )
        )

        "Baskets" -> listOf(

            Product(
                "Fruit Basket",
                250,
                R.drawable.img_5
            ),

            Product(
                "Storage Basket",
                450,
                R.drawable.img_3
            )
        )

        else -> listOf(

            Product(
                "Handmade Item",
                499,
                R.drawable.img_1
            )
        )
    }
}