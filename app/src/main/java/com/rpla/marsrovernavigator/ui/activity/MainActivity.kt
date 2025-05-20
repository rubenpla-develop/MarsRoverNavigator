@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rpla.marsrovernavigator.navigator.RoverNavigator
import com.rpla.marsrovernavigator.ui.navigation.LAST_DATA_INPUT_PARAM_NAME
import com.rpla.marsrovernavigator.ui.navigation.Routes
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
                            RoverNavigator()
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

@Preview(showBackground = true)
@Composable
fun RoverNavigatorPreview() {
    MarsRoverNavigatorTheme {
        RoverNavigator()
    }
}
