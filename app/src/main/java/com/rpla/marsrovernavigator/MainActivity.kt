@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rpla.marsrovernavigator.ui.navigation.LAST_DATA_INPUT_PARAM_NAME
import com.rpla.marsrovernavigator.ui.navigation.Routes
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rpla.marsrovernavigator.ui.theme.MarsRoverNavigatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarsRoverNavigatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = rememberNavController(),
                        startDestination = Routes.RoverNavigation.route,
                    ) {
                        composable(Routes.RoverNavigation.route) {
                            // TODO RoverNavigation composable
                        }

                        composable(
                            Routes.LastDataInput.route,
                            arguments =
                                listOf(
                                    navArgument(LAST_DATA_INPUT_PARAM_NAME) {
                                        defaultValue = ""
                                    },
                                ),
                        ) { navBackStackEntry ->
                            // TODO JsonDataInput Composable
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MarsRoverNavigatorTheme {
        Greeting("Android")
    }
}
