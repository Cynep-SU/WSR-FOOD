package com.example.myapplication

import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Retrofit
import okhttp3.RequestBody.Companion.toRequestBody

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
                    Form()
                }
            }
        }
    }
}


@Composable
fun SimpleAlertDialog(text: String = "", state: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = { state.value = false },
        title = { Text("Error") },
        text = { Text(text) },
        confirmButton = {
            Button(onClick = { state.value = false }) {
                Text("OK")
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form() {
    val context = LocalContext.current
    val retrofit = Retrofit.Builder().baseUrl("https://food.madskill.ru").build()
    // Два способа запоминания значений передаваемых пользователем
    var email by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf(true)
    }
    val password = remember {
        mutableStateOf("")
    }
    val passwordError = remember {
        mutableStateOf(true)
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
            onValueChange = {
                email = it
                emailError = !EMAIL_ADDRESS.matcher(it).matches()
            },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            // isError = emailError
            // leadingIcon = {Image(painter = painterResource(id = R.drawable.cooking), contentDescription = null)}
        )
        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = it.isEmpty()
            },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            visualTransformation = PasswordVisualTransformation(),
            // isError = passwordError.value
        )
        TextButton(
            onClick = { /*О да бесполезная кнопка, а пахнет як*/ },
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "Forgot Password?")
        }
        val isShowDialog = remember {
            mutableStateOf(false)
        }
        val dialogText = remember {
            mutableStateOf("")
        }

        Button(
            onClick = {
                if (emailError) {
                    isShowDialog.value = true
                    dialogText.value = "Email is not correct"
                } else if (passwordError.value) {
                    /* Второй способ вызвать диалог, но он material 2 из обычного api андроид,
                    вопрос, что быстрее написать, и что красивее
                     */
                    android.app.AlertDialog.Builder(context).setTitle("Authorization Error")
                        .setMessage("Password is not correct").setPositiveButton("OK") { _, _ -> }
                        .create().show()
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = retrofit.create(CookingApi::class.java).Login("{\"email\": \"$email\", \"password\": \"${password.value}\"}".toRequestBody("application/json".toMediaTypeOrNull()))
                        println(response.code())
                        if (response.code() == 200){
                            dialogText.value = "Вы успешно вошли"
                            isShowDialog.value = true
                        }
                    }
                }
            },
            Modifier
                .fillMaxSize()
                .padding(15.dp, 20.dp)
        ) {
            Text("Login", fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.nunito)))
        }

        if (isShowDialog.value)
            SimpleAlertDialog(state = isShowDialog, text = dialogText.value)
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
            Form()
        }
    }
}