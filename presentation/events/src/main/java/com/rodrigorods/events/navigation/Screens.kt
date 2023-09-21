package com.rodrigorods.events.navigation

sealed class Screens(val route: String) {
    object Home: Screens("event_list_screen")
    object Detail: Screens("event_detail_screen")
}