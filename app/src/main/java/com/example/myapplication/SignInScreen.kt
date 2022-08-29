package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class SignInScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Form("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(name: String) {
    // Два способа запоминания значений передаваемых пользователем
    var email by remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxWidth()) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(80.dp),
                painter = painterResource(id = R.drawable.cooking),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            // leadingIcon = {Image(painter = painterResource(id = R.drawable.cooking), contentDescription = null)}
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            visualTransformation = PasswordVisualTransformation()
            )
        TextButton(onClick = { /*TODO*/ },
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "Forgot Password?")
        }
        Button(onClick = { /*TODO*/ }, Modifier.fillMaxSize().padding(15.dp)) {
            Text("Login")
        }
        // BasicTextField(value = email, onValueChange = {email = it}, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Form("Android")
        }
    }
}