package com.example.democompose.ui.screens.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.democompose.R
import com.example.democompose.data.model.Genre
import com.example.democompose.data.model.Genres
import com.example.democompose.navigation.Navigation
import com.example.democompose.navigation.Screen
import com.example.democompose.navigation.currentRoute
import com.example.democompose.navigation.navigationTitle
import com.example.democompose.ui.component.CircularIndeterminateProgressBar
import com.example.democompose.ui.component.SearchUI
import com.example.democompose.ui.component.appbar.AppBarWithArrow
import com.example.democompose.ui.component.appbar.HomeAppBar
import com.example.democompose.ui.component.appbar.SearchBar
import com.example.democompose.ui.screens.drawer.DrawerUI
import com.example.democompose.ui.theme.FloatingActionBackground
import com.example.democompose.utils.AppConstant
import com.example.democompose.utils.network.DataState
import com.example.democompose.utils.network_connection.ConnectionState
import com.example.democompose.utils.network_connection.connectivityState
import com.example.democompose.utils.pagingLoadingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreName = remember { mutableStateOf("") }
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    // genre api call for first time
    LaunchedEffect(key1 = 0) {
        mainViewModel.genreList()
    }

    if (mainViewModel.genres.value is DataState.Success<Genres>) {
        genreList.value =
            (mainViewModel.genres.value as DataState.Success<Genres>).data.genres as ArrayList
        // All first value as all
        if (genreList.value.first().name != AppConstant.DEFAULT_GENRE_ITEM)
            genreList.value.add(0, Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    }

    Scaffold(scaffoldState = scaffoldState, topBar = {
        when (currentRoute(navController)) {
            Screen.Home.route,
            Screen.Popular.route,
            Screen.TopRated.route,
            Screen.Upcoming.route,
            Screen.NavigationDrawer.route -> {
                if (isAppBarVisible.value) {
                    val appTitle: String =
                        if (currentRoute(navController) == Screen.NavigationDrawer.route) genreName.value
                        else stringResource(R.string.app_title)
                    HomeAppBar(title = appTitle, openDrawer = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }, openFilters = {
                        isAppBarVisible.value = false
                    })
                } else {
                    SearchBar(isAppBarVisible, mainViewModel)
                }
            }
            else -> {
                AppBarWithArrow(navigationTitle(navController)) {
                    navController.popBackStack()
                }
            }
        }
    }, drawerContent = {
        // Drawer content
        DrawerUI(navController, genreList.value as List<Genre>) {
            genreName.value = it
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }
    }, floatingActionButton = {
        when (currentRoute(navController)) {
            Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                FloatingActionButton(
                    onClick = {
                        isAppBarVisible.value = !isAppBarVisible.value
                    }, backgroundColor = FloatingActionBackground
                ) {
                    Icon(Icons.Filled.Search, "", tint = Color.White)
                }
            }
        }
    }, bottomBar = {
        when (currentRoute(navController)) {
            Screen.Home.route,
            Screen.Popular.route,
            Screen.TopRated.route,
            Screen.Upcoming.route -> {
                BottomNavigationUI(navController)
            }
        }
    }, snackbarHost = {
        if (isConnected.not()) {
            Snackbar(
                action = {}, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(R.string.there_is_no_internet))
            }
        }
    }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it), genreList.value)
            Column {
                CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
                if (isAppBarVisible.value.not()) {
                    SearchUI(navController, mainViewModel.searchData) {
                        isAppBarVisible.value = true
                    }
                }
            }
        }
        mainViewModel.searchData.pagingLoadingState {
            searchProgressBar.value = it
        }
    }
}


@Composable
fun BottomNavigationUI(navController: NavController) {
    BottomNavigation {
        val items = listOf(
            Screen.HomeNav,
            Screen.PopularNav,
            Screen.TopRatedNav,
            Screen.UpcomingNav,
        )
        items.forEach { item ->
            BottomNavigationItem(
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                icon = item.navIcon,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }
}