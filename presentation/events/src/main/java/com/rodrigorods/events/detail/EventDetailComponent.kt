package com.rodrigorods.events.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventDetailScreen(
    navController: NavController,
//    viewModel: EventListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val id = navController.previousBackStackEntry
        ?.arguments?.getString("eventID")

    Text(text = "TELA DE DETALHES: $id")
}