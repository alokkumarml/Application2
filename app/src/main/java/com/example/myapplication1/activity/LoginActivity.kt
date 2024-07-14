package com.example.myapplication1.activity


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.myapplication1.MainActivity
import com.example.myapplication1.R
import com.example.myapplication1.activity.ui.theme.MyApplication1Theme
import com.example.myapplication1.activity.viewmodal.LoginActivityViewModal
import com.example.myapplication1.sealed.ApiState
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginActivityViewModal by viewModels()

    var dialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Greeting2("Android")

                    LoginActivityUI() {
                        finish()
                    }
                }
            }
        }


        dialog = ProgressDialog(this)

        dialog!!.setMessage("Please Wait...")
        observer()


    }

    private fun observer() {


        lifecycleScope.launch {


            // 1 type implement ->       viewModel.ISLOGGINEDFlow.observe(this, Observer { it ->

            // 2 type implement
            viewModel.dataStoreManager.ISLOGGINEDFlow.collect {
                //  exampleBooleanCheckBox.isChecked = exampleBoolean

                Log.e("Check111111111", "exampleBoolean" + it)

                if (it == true) {

                    runOnUiThread {

                        dialog?.dismiss()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()

                    }
                }

            }


            // })


            viewModel.apiStateFlow.collect {

                when (it) {

                    is ApiState.Loading -> {


                        //  Toast.makeText(this@LoginActivity, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show()

                    }

                    is ApiState.Failure -> {

                        dialog?.dismiss()
                        //  Toast.makeText(this@LoginActivity, "bbbbbbbb", Toast.LENGTH_SHORT).show()
                    }

                    is ApiState.Success -> {

                        dialog?.dismiss()
                        Toast.makeText(this@LoginActivity, it.response.result, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is ApiState.Empty -> {
                        dialog?.dismiss()
                        // Toast.makeText(this@LoginActivity, "ddddddddddd", Toast.LENGTH_SHORT).show()

                    }

                }


            }

        }
    }



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview3() {
//    MyApplication1Theme {
////       LoginActivityUI {
////
////       }
//    }
//}


    @Composable
    fun LoginActivityUI(finish: () -> Unit) {

        val context = LocalContext.current


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Circle Image View
            Image(
                painter = painterResource(R.drawable.loadingpage),
                contentDescription = "Loading Page",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email or Mobile Number TextField
            var emailOrMobile by remember { mutableStateOf("") }
            OutlinedTextField(
                value = emailOrMobile,
                onValueChange = { emailOrMobile = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp),
                placeholder = { Text("Phone Number & EmailId") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password TextField
            var password by remember { mutableStateOf("") }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp),
                placeholder = { Text("Password") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Button
            Button(
                onClick = { /* Handle login click */

                    val loginRequest = buildJsonObject {
                        put("emailid", emailOrMobile)
                        put("password", password)
                    }

                    if (emailOrMobile.isNullOrBlank() && password.isNullOrBlank()){


                        Toast.makeText(context,"Enter The Feild",Toast.LENGTH_SHORT).show()

                    }else {


                        dialog?.show()
                        viewModel.getPost(loginRequest)

                    }

                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Log In",
                    fontSize = 20.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
//                    modifier = Modifier.clickable {
//
//
//
//                    }

                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Create New Account Text
            Text(
                text = "Create New Account",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        context.startActivity(Intent(context, RegisterActivity::class.java))
                        finish()

                    },
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }

}

