package com.example.myapplication1.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication1.activity.ui.theme.MyApplication1Theme
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import com.example.myapplication1.R

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting3("Android")
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    MyApplication1Theme {
       // Greeting3("Android")

        LoginScreen()
    }
}


@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circle Image

        // State variables for text fields
        val firstName = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }
        val emailId = remember { mutableStateOf("") }
        val gender = remember { mutableStateOf("") }
        val dateOfBirth = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.loadingpage),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Input fields
        val fieldModifier = Modifier
            .fillMaxWidth()
            .height(48.dp)


        val textColor = Color.Black
        val hintColor = Color.Gray

        CustomTextField(firstName,"First Name", fieldModifier, textColor, hintColor)

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(lastName,"Last Name", fieldModifier, textColor, hintColor)

        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(phoneNumber,"Phone Number", fieldModifier, textColor, hintColor)

        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(emailId,"Email Id", fieldModifier, textColor, hintColor)

        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(gender,"Gender", fieldModifier, textColor, hintColor)

        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(dateOfBirth,"Date Of Birth", fieldModifier, textColor, hintColor)

        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(password,"Password", fieldModifier, textColor, hintColor, isPassword = true)

        Spacer(modifier = Modifier.height(32.dp))

        // Create Account Button
        Button(
            onClick = {
                // Handle button click
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text("Create Account")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Login Account Text
        Text(
            text = "Login Account",
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .clickable {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CustomTextField(
    textState: MutableState<String>,
    placeholder: String,
    modifier: Modifier,
    textColor: Color,
    hintColor: Color,
    isPassword: Boolean = false
) {
   // var text by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .background(Color.White)
            .height(48.dp)

    ) {
        if (textState.value.isEmpty()) {
            Text(text = placeholder, color = hintColor)
        }
        BasicTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
            ),
            keyboardActions = KeyboardActions.Default,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
        )
    }
}