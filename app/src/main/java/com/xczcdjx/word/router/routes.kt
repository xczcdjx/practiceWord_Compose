package com.xczcdjx.word.router

enum class RouteEnum {
    Splash,
    Home,
    Test,
}

sealed class Routes(val route: String) {
    data object Splash : Routes(RouteEnum.Splash.name)
    data object Home : Routes(RouteEnum.Home.name)
    data object Test : Routes(RouteEnum.Test.name)
}