package com.example.hallisanthe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AddressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddressScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen() {

    val context = LocalContext.current

    data class AddressItem(
        var address: String,
        var phone: String
    )

    val addressList = remember {
        mutableStateListOf(
            AddressItem("Bangalore - 560001", "9876543210"),
            AddressItem("Whitefield, Bangalore", "9123456789")
        )
    }

    var newAddress by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var editIndex by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Address") },
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? android.app.Activity)?.finish()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {

            // 📍 Address Input
            OutlinedTextField(
                value = newAddress,
                onValueChange = { newAddress = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter address") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 📞 Phone Input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter phone number") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newAddress.isNotEmpty() && phoneNumber.isNotEmpty()) {

                        if (editIndex >= 0) {
                            addressList[editIndex] =
                                AddressItem(newAddress, phoneNumber)
                            editIndex = -1
                        } else {
                            addressList.add(AddressItem(newAddress, phoneNumber))
                        }

                        newAddress = ""
                        phoneNumber = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editIndex >= 0) "Update Address" else "Add Address")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📋 Address List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                itemsIndexed(addressList) { index, item ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text(text = item.address)
                            Text(text = "📞 ${item.phone}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {

                                TextButton(onClick = {
                                    // ✏️ Edit
                                    newAddress = item.address
                                    phoneNumber = item.phone
                                    editIndex = index
                                }) {
                                    Text("Edit")
                                }

                                TextButton(onClick = {
                                    // ❌ Delete
                                    addressList.removeAt(index)
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}