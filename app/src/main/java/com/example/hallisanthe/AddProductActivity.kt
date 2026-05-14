package com.example.hallisanthe

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.util.UUID

class AddProductActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HalliSantheTheme {
                AddProductScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen() {

    val context = LocalContext.current

    // INPUT STATES
    var productName by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var stock by remember {
        mutableStateOf("")
    }

    // IMAGE STATE
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // IMAGE PICKER
    val launcher =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts.GetContent()

        ) { uri ->

            imageUri = uri
        }

    val scrollState =
        rememberScrollState()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Add Product")
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

            Text(
                text = "Upload Handmade Product",

                fontSize = 24.sp,

                color = ForestGreen
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // IMAGE PREVIEW
            imageUri?.let {

                Image(
                    painter =
                        rememberAsyncImagePainter(it),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),

                    contentScale =
                        ContentScale.Crop
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )
            }

            // IMAGE PICK BUTTON
            OutlinedButton(

                onClick = {

                    launcher.launch("image/*")
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Choose Product Image")
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // PRODUCT NAME
            OutlinedTextField(

                value = productName,

                onValueChange = {
                    productName = it
                },

                label = {
                    Text("Product Name")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // DESCRIPTION
            OutlinedTextField(

                value = description,

                onValueChange = {
                    description = it
                },

                label = {
                    Text("Description")
                },

                modifier = Modifier.fillMaxWidth(),

                minLines = 4
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // PRICE
            OutlinedTextField(

                value = price,

                onValueChange = {
                    price = it
                },

                label = {
                    Text("Price")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // CATEGORY
            OutlinedTextField(

                value = category,

                onValueChange = {
                    category = it
                },

                label = {
                    Text("Category")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // STOCK
            OutlinedTextField(

                value = stock,

                onValueChange = {
                    stock = it
                },

                label = {
                    Text("Stock Quantity")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // UPLOAD BUTTON
            Button(

                onClick = {

                    if (

                        productName.isNotEmpty() &&
                        description.isNotEmpty() &&
                        price.isNotEmpty() &&
                        category.isNotEmpty() &&
                        stock.isNotEmpty() &&
                        imageUri != null

                    ) {

                        val currentUser =
                            FirebaseManager.auth.currentUser

                        val artisanId =
                            currentUser?.uid ?: ""

                        val artisanEmail =
                            currentUser?.email ?: ""

                        val imageRef =
                            FirebaseManager.storage.reference

                                .child(
                                    "product_images/${UUID.randomUUID()}"
                                )

                        // UPLOAD IMAGE
                        imageRef.putFile(imageUri!!)

                            .addOnSuccessListener {

                                imageRef.downloadUrl
                                    .addOnSuccessListener { downloadUrl ->

                                        val productData = hashMapOf(

                                            "productName" to productName,

                                            "description" to description,

                                            "price" to price,

                                            "category" to category,

                                            "stock" to stock,

                                            "imageUrl" to
                                                    downloadUrl.toString(),

                                            "artisanId" to artisanId,

                                            "artisanEmail" to artisanEmail,

                                            "timestamp" to
                                                    System.currentTimeMillis()
                                        )

                                        FirebaseManager.firestore
                                            .collection("products")
                                            .add(productData)

                                            .addOnSuccessListener {

                                                Toast.makeText(
                                                    context,
                                                    "Product Uploaded",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // CLEAR FIELDS
                                                productName = ""
                                                description = ""
                                                price = ""
                                                category = ""
                                                stock = ""
                                                imageUri = null
                                            }

                                            .addOnFailureListener {

                                                Toast.makeText(
                                                    context,
                                                    "Firestore Upload Failed",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                    }
                            }

                            .addOnFailureListener {

                                Toast.makeText(
                                    context,
                                    "Image Upload Failed",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                    } else {

                        Toast.makeText(
                            context,
                            "Fill all fields & choose image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Text("Upload Product")
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}