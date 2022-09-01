package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class SignUpScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpForm()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpForm() {
    val context = LocalContext.current
    // Два способа запоминания значений передаваемых пользователем
    var email by remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val fullName = remember {
        mutableStateOf("")
    }
    val phoneNumber = remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxWidth()) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
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
        TextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            label = { Text("Full name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
        )

        Button(
            onClick = { /*TODO*/ },
            Modifier
                .fillMaxSize()
                .padding(15.dp, 20.dp).weight(1F)
        ) {
            Text("Register now", fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.nunito)))
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, SignInScreen::class.java))
                (context as ComponentActivity).finish()
            },
            Modifier
                .fillMaxSize()
                .padding(15.dp, 20.dp).weight(1F)
        ) {
            Text("Cancel", fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.nunito)))
        }
        // BasicTextField(value = email, onValueChange = {email = it}, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignUpForm()
        }
    }
}