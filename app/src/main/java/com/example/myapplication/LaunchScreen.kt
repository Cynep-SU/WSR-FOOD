package com.example.myapplication

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Image(
                    painter = painterResource(id = R.drawable.splash_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                LogoWithTitle("WSR Food", R.drawable.cooking)
                LaunchedEffect(null){
                    delay(1000)
                    context.startActivity(Intent(context, Second::class.java))
                    this@MainActivity.finish()
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
        ){
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(painter = painterResource(id = logoId), contentDescription = null, modifier = Modifier.size(100.dp))
                Text(text = name, style = Typography.titleMedium)
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

