package com.example.myapplication1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import com.example.myapplication1.ui.theme.MyApplication1Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setContent {
//            BottomNavigationTheme {
//
//                // remember navController so it does not
//                // get recreated on recomposition
//                val navController = rememberNavController()
//
//                Surface(color = Color.White) {
//                    // Scaffold Component
//                    Scaffold(
//                        // Bottom navigation
//                        bottomBar = {
//                            BottomNavigationBar(navController = navController)
//                        }, content = { padding ->
//                            // Navhost: where screens are placed
//                            NavHostContainer(navController = navController, padding = padding)
//                        }
//                    )
//                }
//            }
//        }


        setContent {
//            MyApplication1Theme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//
//                    val navController = rememberNavController()
//
//                    Scaffold(
//                        bottomBar = { BottomNavigationBar(navController) }
//                    ) {
//                        NavigationGraph(navController)
//                    }
//
////                    Greeting("Android")
//
//
//                }
//            }


            MyApp(this)

            Log.e("aaaaaaaaaa", viewModel.getSomeString())

        }

    }


//    @Composable
//    fun NavigationGraph(navController: NavHostController, mainActivity: MainActivity) {
//        NavHost(navController, startDestination = BottomNavItem.Home.route) {
//            composable(BottomNavItem.Home.route) {
//                /* Home Screen Content */
//
//                val previousRoute = navController.previousBackStackEntry?.destination?.route
//
//                Toast.makeText(this@MainActivity,"Home "+previousRoute,Toast.LENGTH_SHORT).show()
//
//            }
//            composable(BottomNavItem.Search.route) { /* Search Screen Content */
//
//                val previousRoute = navController.previousBackStackEntry?.destination?.route
//                Toast.makeText(this@MainActivity,"Search "+previousRoute,Toast.LENGTH_SHORT).show()
//
//            }
//            composable(BottomNavItem.Add.route){
//
//                val previousRoute = navController.previousBackStackEntry?.destination?.route
//                Toast.makeText(this@MainActivity,"Add "+previousRoute,Toast.LENGTH_SHORT).show()
//
//            }
//            composable(BottomNavItem.Chat.route){
//
//                val previousRoute = navController.previousBackStackEntry?.destination?.route
//                Toast.makeText(this@MainActivity,"Chat "+previousRoute,Toast.LENGTH_SHORT).show()
//
//            }
//            composable(BottomNavItem.Profile.route) { /* Profile Screen Content */
//
//                val previousRoute = navController.previousBackStackEntry?.destination?.route
//                Toast.makeText(this@MainActivity,"Profile "+previousRoute,Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MyApp(mainActivity: MainActivity) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) {
           // NavigationGraph(navController,mainActivity)
        }
    }



    // class



    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Search,
            BottomNavItem.Add,
            BottomNavItem.Chat,
            BottomNavItem.Profile
        )
        var selectedItem by remember { mutableStateOf(0) }
        BottomNavigation {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = item.title,
                            tint = if (selectedItem == index) Color.Blue else Color.Gray
                        )
                    },
                    label = { Text(text = item.title) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index


                        if (item.route.equals("home")){

                            Toast.makeText(this@MainActivity,""+item.route,Toast.LENGTH_SHORT).show()
                        }else if (item.route.equals("search")){

                            Toast.makeText(this@MainActivity,""+item.route,Toast.LENGTH_SHORT).show()
                        }else if (item.route.equals("add")){

                            Toast.makeText(this@MainActivity,""+item.route,Toast.LENGTH_SHORT).show()
                        }else if (item.route.equals("chat")){
                            Toast.makeText(this@MainActivity,""+item.route,Toast.LENGTH_SHORT).show()

                        }else if (item.route.equals("profile")){

                            Toast.makeText(this@MainActivity,""+item.route,Toast.LENGTH_SHORT).show()

                        }


//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.startDestinationId) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
                    }
                )
            }
        }
    }

    }



//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello1122 $name!",
//        modifier = modifier
//    )
//}

    //@Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MyApplication1Theme {
            // Greeting("Android")
        }

    }


//    @Preview(showBackground = true)
//    @Composable
//    fun PreviewLoginActivityUI() {
//        LoginActivityUI()
//    }






sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Home : BottomNavItem("Home",   R.drawable.baseline_home_24, "home")
    object Search : BottomNavItem("Search", R.drawable.baseline_home_24, "search")
    object Add : BottomNavItem("Add", R.drawable.baseline_add_24, "add")
    object Chat : BottomNavItem("Chat", R.drawable.baseline_chat_bubble_24, "chat")
    object Profile : BottomNavItem("Profile", R.drawable.baseline_account_circle_24, "profile")
}









