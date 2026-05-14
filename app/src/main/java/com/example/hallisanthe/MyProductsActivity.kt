package com.example.hallisanthe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MyProductsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            HalliSantheTheme {

                MyProductsScreen()
            }
        }
    }
}

// PRODUCT MODEL
data class MyProduct(

    val id: String = "",

    val productName: String = "",

    val description: String = "",

    val price: String = "",

    val category: String = "",

    val stock: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductsScreen() {

    val context = LocalContext.current

    val currentUser =
        FirebaseManager.auth.currentUser

    val artisanId =
        currentUser?.uid ?: ""

    // PRODUCT LIST
    var productList by remember {

        mutableStateOf(
            listOf<MyProduct>()
        )
    }

    // LOAD PRODUCTS
    LaunchedEffect(Unit) {

        FirebaseManager.firestore
            .collection("products")

            .whereEqualTo(
                "artisanId",
                artisanId
            )

            .get()

            .addOnSuccessListener { documents ->

                val tempList =
                    mutableListOf<MyProduct>()

                for (document in documents) {

                    val product = MyProduct(

                        id = document.id,

                        productName =
                            document.getString("productName")
                                ?: "",

                        description =
                            document.getString("description")
                                ?: "",

                        price =
                            document.getString("price")
                                ?: "",

                        category =
                            document.getString("category")
                                ?: "",

                        stock =
                            document.getString("stock")
                                ?: ""
                    )

                    tempList.add(product)
                }

                // DUMMY PRODUCTS
                val dummyProducts = listOf(

                    MyProduct(
                        productName = "Handmade Basket",
                        description = "Eco-friendly bamboo basket",
                        price = "499",
                        category = "Baskets",
                        stock = "10"
                    ),

                    MyProduct(
                        productName = "Clay Pot",
                        description = "Traditional handcrafted clay pot",
                        price = "299",
                        category = "Pottery",
                        stock = "15"
                    ),

                    MyProduct(
                        productName = "Wooden Toy",
                        description = "Hand-carved wooden toy",
                        price = "399",
                        category = "Toys",
                        stock = "8"
                    ),

                    MyProduct(
                        productName = "Jute Bag",
                        description = "Reusable handmade bag",
                        price = "699",
                        category = "Bags",
                        stock = "12"
                    ),

                    MyProduct(
                        productName = "Decor Lamp",
                        description = "Traditional decorative lamp",
                        price = "999",
                        category = "Lamps",
                        stock = "5"
                    ),

                    MyProduct(
                        productName = "Village Wall Art",
                        description = "Handmade wall painting",
                        price = "1499",
                        category = "Art",
                        stock = "4"
                    )
                )

                // FIRESTORE + DUMMY
                productList =
                    tempList + dummyProducts
            }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("My Products")
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

        if (productList.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "No Products Uploaded",
                    fontSize = 20.sp
                )
            }

        } else {

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)

            ) {

                items(productList) { product ->

                    ProductCard(

                        product = product,

                        onDelete = {

                            if (product.id.isNotEmpty()) {

                                FirebaseManager.firestore
                                    .collection("products")
                                    .document(product.id)
                                    .delete()

                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            context,
                                            "Product Deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        productList =
                                            productList.filter {

                                                it.id != product.id
                                            }
                                    }
                            }
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: MyProduct,
    onDelete: () -> Unit
) {

    val context = LocalContext.current

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                // NAVIGATE TO PRODUCT DETAIL
                context.startActivity(

                    Intent(
                        context,
                        ProductDetailActivity::class.java
                    )
                )
            }

    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // PRODUCT NAME
            Text(
                text = product.productName,
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // DESCRIPTION
            Text(
                text = product.description
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // CATEGORY
            Text(
                text =
                    "Category: ${product.category}"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // STOCK
            Text(
                text =
                    "Stock: ${product.stock}"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // PRICE
            Text(
                text =
                    "₹${product.price}",

                color = ForestGreen,

                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // DELETE BUTTON
            Button(

                onClick = onDelete,

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .error
                    ),

                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Delete,

                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Delete Product")
            }
        }
    }
}