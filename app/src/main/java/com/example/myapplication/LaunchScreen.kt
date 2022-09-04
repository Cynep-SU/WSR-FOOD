package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Typography
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme

                CookingAppContext.isOffline
                Image(
                    painter = painterResource(id = R.drawable.splash_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                LogoWithTitle("WSR Food", R.drawable.cooking)
                LaunchedEffect(null) {
                    val intent = Intent(context, OnBoardingScreen::class.java)
                    var fileIsFound = true
                    "https://food.madskill.ru/dishes/version".httpGet()
                        .responseString { _, _, result ->
                            when (result) {
                                is Result.Failure -> {
                                    CookingAppContext.isOffline = true
                                    try {
                                        if (context.openFileInput("dishes.json").bufferedReader()
                                                .readText().isEmpty()
                                        )
                                            throw FileNotFoundException()
                                    } catch (e: FileNotFoundException) {
                                        fileIsFound = false

                                    }
                                }
                                is Result.Success -> {
                                    context.openFileOutput("dishes.json", Context.MODE_PRIVATE)
                                        .use {
                                            val versionsJson =
                                                JSONObject(result.get()).getJSONArray("version")
                                            var dishesJson = JSONArray()
                                            for (i in 0 until versionsJson.length()) {
                                                "https://food.madskill.ru/dishes".httpGet(
                                                    listOf(
                                                        "version" to versionsJson.getString(
                                                            i
                                                        )
                                                    )
                                                ).responseString { _, _, result ->
                                                    if (result is Result.Success) {
                                                        val resultJson = JSONArray(result.value)
                                                        if (i == 0)
                                                            dishesJson = resultJson
                                                        else {
                                                            for (j in 0 until resultJson.length()) {
                                                                dishesJson.put(
                                                                    resultJson.getJSONObject(
                                                                        j
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }.join()
                                            }
                                            println(dishesJson.toString())
                                            it.write(dishesJson.toString().toByteArray())
                                        }
                                }
                            }
                        }.join()
                    // Люблю симулировать загрузку...
                    delay(1000)
                    if (fileIsFound) {
                        context.startActivity(intent)
                        this@MainActivity.finish()
                    } else {
                        AlertDialog.Builder(this@MainActivity).setTitle("Connection Error")
                            .setMessage("For first entering, you need to use the Internet")
                            .setPositiveButton("OK") { _, _ -> this@MainActivity.finish()}.create().show()
                    }
                }
            }
        }
    }
}

@Composable
fun LogoWithTitle(name: String, logoId: Int) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(263.dp)
                .clip(CircleShape)
                .background(Color(0x99FFFFFF))
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = logoId),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Text(text = name, style = Typography.titleMedium)
                if (CookingAppContext.isOffline)
                    CircularProgressIndicator(
                        modifier = Modifier.size(35.6.dp),
                        color = Color.Red,
                        progress = 1F
                    )
                else
                    CircularProgressIndicator(modifier = Modifier.size(35.6.dp), color = Color.Red)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}

