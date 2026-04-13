package com.example.mydairy_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mydairy_app.feature.agent.AgentScreen
import com.example.mydairy_app.feature.calendar.CalendarScreen
import com.example.mydairy_app.feature.detail.DetailScreen
import com.example.mydairy_app.feature.editor.EditorScreen
import com.example.mydairy_app.feature.home.HomeScreen
import com.example.mydairy_app.feature.settings.SettingsScreen
import com.example.mydairy_app.feature.tags.TagManagerScreen

@Composable
fun AppNavHost(): Unit {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.createRoute(),
    ) {
        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument(Screen.Home.DATE_FILTER_ARG) {
                    type = NavType.LongType
                    defaultValue = Screen.Home.NO_DATE_FILTER
                },
                navArgument(Screen.Home.SEARCH_QUERY_ARG) {
                    type = NavType.StringType
                    defaultValue = Screen.Home.EMPTY_QUERY
                },
                navArgument(Screen.Home.TAG_NAME_ARG) {
                    type = NavType.StringType
                    defaultValue = Screen.Home.EMPTY_QUERY
                },
            ),
        ) {
            HomeScreen(
                onOpenEditor = { entryId ->
                    navController.navigate(Screen.Editor.createRoute(entryId))
                },
                onOpenDetail = { entryId ->
                    navController.navigate(Screen.Detail.createRoute(entryId))
                },
                onOpenCalendar = {
                    navController.navigate(Screen.Calendar.route)
                },
                onOpenTagManager = {
                    navController.navigate(Screen.TagManager.route)
                },
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onOpenAgent = {
                    navController.navigate(Screen.Agent.route)
                },
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.Detail.ENTRY_ID_ARG) {
                    type = NavType.LongType
                },
            ),
        ) {
            DetailScreen(
                onBack = { navController.popBackStack() },
                onEdit = { editableId ->
                    navController.navigate(Screen.Editor.createRoute(editableId))
                },
            )
        }

        composable(
            route = Screen.Editor.route,
            arguments = listOf(
                navArgument(Screen.Editor.ENTRY_ID_ARG) {
                    type = NavType.LongType
                    defaultValue = Screen.Editor.NEW_ENTRY_ID
                },
            ),
        ) { backStackEntry ->
            val rawEntryId = backStackEntry.arguments?.getLong(Screen.Editor.ENTRY_ID_ARG)
                ?: Screen.Editor.NEW_ENTRY_ID
            val entryId = rawEntryId.takeIf { it != Screen.Editor.NEW_ENTRY_ID }

            EditorScreen(
                entryId = entryId,
                onBack = { navController.popBackStack() },
            )
        }

        composable(route = Screen.Calendar.route) {
            CalendarScreen(
                onBack = { navController.popBackStack() },
                onSelectDate = { dateStartMillis ->
                    navController.navigate(Screen.Home.createRoute(dateStartMillis)) {
                        popUpTo(Screen.Home.BASE_ROUTE) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(route = Screen.TagManager.route) {
            TagManagerScreen(
                onBack = { navController.popBackStack() },
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
            )
        }

        composable(route = Screen.Agent.route) {
            AgentScreen(
                onBack = { navController.popBackStack() },
                onNavigateHome = { searchQuery, tagName, dateFilterMillis ->
                    navController.navigate(
                        Screen.Home.createRoute(
                            dateFilterMillis = dateFilterMillis,
                            searchQuery = searchQuery,
                            tagName = tagName,
                        ),
                    ) {
                        popUpTo(Screen.Home.BASE_ROUTE) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
