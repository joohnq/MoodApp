package com.joohnq.moodapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joohnq.moodapp.view.constants.Colors
import com.joohnq.moodapp.view.constants.Drawables
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppBottomNavigation(navController: NavController = rememberNavController()) {
    val bottomScreens = remember {
        listOf(
            BottomScreens.Home,
            BottomScreens.Journaling,
        )
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(10.dp).height(80.dp)
                .paint(
                    painter = painterResource(Drawables.Shape.BottomNavigation),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                ),
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomScreens.forEach { screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true
                NavigationBarItem(
                    selected = isSelected,
                    colors = NavigationBarItemColors(
                        selectedIconColor = Colors.Brown80,
                        selectedTextColor = Colors.Brown80,
                        selectedIndicatorColor = Colors.Brown10,
                        unselectedIconColor = Colors.Brown30,
                        unselectedTextColor = Colors.Brown30,
                        disabledIconColor = Colors.Brown30,
                        disabledTextColor = Colors.Brown30
                    ),
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(screen.icon),
                            contentDescription = screen.name,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                )
            }
        }
        Box(modifier = Modifier.padding(bottom = 60.dp).background(color = Colors.Transparent)) {
            with(BottomScreens.Add) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {},
                    modifier = Modifier.size(64.dp)
                        .background(color = Colors.Green50, shape = CircleShape),
                    colors = ButtonColors(
                        containerColor = Colors.Green50,
                        contentColor = Colors.White,
                        disabledContainerColor = Colors.Green50,
                        disabledContentColor = Colors.White
                    )
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = name,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}