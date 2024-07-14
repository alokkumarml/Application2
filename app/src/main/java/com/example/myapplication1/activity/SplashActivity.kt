package com.example.myapplication1.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.MainActivity
import com.example.myapplication1.R
import com.example.myapplication1.activity.ui.theme.MyApplication1Theme
import com.example.myapplication1.preferencesDataStore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        init()

    }

    private fun init() {


        lifecycleScope.launch(Dispatchers.Main) {

            delay(2000)

            val dataStoreManager = DataStoreManager(this@SplashActivity)

            var checkLogin= dataStoreManager.ISLOGGINEDFlow.first()

            if (checkLogin){


                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()

            }else{

                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                finish()

            }

        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loadingpage),
        contentDescription = "Loading Page",
        modifier = Modifier
            .size(150.dp)
            .padding(40.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyApplication1Theme {
        Greeting("Android")
    }
}