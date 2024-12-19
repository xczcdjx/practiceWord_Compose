package com.xczcdjx.word.router

enum class RouteEnum {
    Home,
    Test,
}

sealed class Routes(val route: String) {
    data object Home : Routes(RouteEnum.Home.name)
    data object Test : Routes(RouteEnum.Test.name)
}