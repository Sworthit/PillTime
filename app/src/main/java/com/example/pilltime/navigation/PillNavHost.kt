package com.example.pilltime.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pilltime.feature.addpill.AddPillScreen
import com.example.pilltime.feature.addpill.AddPillViewModelFactory
import com.example.pilltime.feature.home.HomeScreen
import com.example.pilltime.feature.pilldetails.PillDetailsScreen
import com.example.pilltime.feature.pilldetails.PillDetailsViewModel
import com.example.pilltime.feature.pilllist.PillListScreen
import com.example.pilltime.feature.pilllist.PillListViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PillNavHost(
    addPillViewModelFactory: AddPillViewModelFactory,
    pillListViewModelFactory: PillListViewModelFactory,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.navigationBarsPadding()) {
                items.forEach { item ->
                    val isSelected = item.title.lowercase() ==
                            navBackStackEntry?.destination?.route
                    NavigationBarItem(selected = isSelected,
                        label = {
                            Text(text = item.title)
                        },
                        onClick = {
                            navController.navigate(item.title.lowercase()) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        })
                }
            }
        }
    ) {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController)
                }
                composable("pills") {
                    PillListScreen(
                        factory = pillListViewModelFactory,
                        navController = navController
                    )
                }
                composable("add_pill") {
                    AddPillScreen(
                        navController = navController,
                        factory = addPillViewModelFactory
                    )
                }

                composable(
                    route = "pill_details/{pillId}",
                    arguments = listOf(
                        navArgument("pillId") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val pillDetailsViewModel = hiltViewModel<PillDetailsViewModel>()

                    PillDetailsScreen(
                        navController = navController,
                        viewModel  = pillDetailsViewModel
                    )
                }
            }
        }
    }
}
data class BottomNavigationItem(
    val title:String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        unselectedIcon = Icons.Outlined.DateRange,
        selectedIcon = Icons.Filled.DateRange
    ),
    BottomNavigationItem(
        title = "Pills",
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    )
)