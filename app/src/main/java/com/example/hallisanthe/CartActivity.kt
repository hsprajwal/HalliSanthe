package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {

    val context = LocalContext.current

    data class CartItem(
        val name: String,
        val price: Int,
        val image: Int,
        var quantity: Int
    )

    val cartItems = remember {
        mutableStateListOf(
            CartItem("Toy", 100, R.drawable.logo, 1),
            CartItem("Bag", 200, R.drawable.logo, 1),
            CartItem("Lamp", 150, R.drawable.logo, 1)
        )
    }

    Scaffold(

        // 🔻 Bottom Navigation
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, BuyerHomeActivity::class.java))
                    },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, CategoryActivity::class.java))
                    },
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("Category") }
                )

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.ShoppingCart, null) },
                    label = { Text("Cart") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, UserActivity::class.java))
                    },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
            }
        },

        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? android.app.Activity)?.finish()
                    }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }

    ) { innerPadding ->

        if (cartItems.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Your cart is empty")
            }

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(10.dp)
            ) {

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(cartItems) { item ->

                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // 🖼️ Image
                                Image(
                                    painter = painterResource(id = item.image),
                                    contentDescription = null,
                                    modifier = Modifier.size(60.dp)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    // 🔥 NAME + PRICE (same line)
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {

                                        Text(item.name)

                                        Text(
                                            text = "₹${item.price}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // ➕➖ Quantity
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        IconButton(onClick = {
                                            if (item.quantity > 1) item.quantity--
                                        }) {
                                            Icon(Icons.Default.Remove, null)
                                        }

                                        Text("${item.quantity}")

                                        IconButton(onClick = {
                                            item.quantity++
                                        }) {
                                            Icon(Icons.Default.Add, null)
                                        }
                                    }
                                }

                                // ❌ Delete (below price naturally)
                                IconButton(onClick = {
                                    cartItems.remove(item)
                                }) {
                                    Icon(Icons.Default.Delete, null)
                                }
                            }
                        }
                    }
                }

                // 💰 Total
                val total = cartItems.sumOf { it.price * it.quantity }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("Total: ₹$total")

                        Button(onClick = {
                            // TODO checkout
                        }) {
                            Text("Checkout")
                        }
                    }
                }
            }
        }
    }
}