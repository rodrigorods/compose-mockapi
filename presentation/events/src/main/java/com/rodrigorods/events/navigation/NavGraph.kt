package com.rodrigorods.events.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rodrigorods.events.EventListScreen
import com.rodrigorods.events.detail.EventDetailScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route)
    {
        composable(route = Screens.Home.route){
            EventListScreen(navController)
        }
        composable(
            route = "${Screens.Detail.route}/{eventID}",
            arguments = listOf(navArgument("eventID") { type = NavType.StringType })
        ){
            val eventIdArg = it.arguments?.getString("eventID") ?: ""
            EventDetailScreen(eventIdArg, navController)
        }
    }
}