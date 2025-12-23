package com.example.travenorowner.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travenorowner.features.ownerRequest.OwnerRequestScreen
import com.example.travenorowner.features.search.SearchScreen
import kotlinx.serialization.Serializable
import kotlin.String


@Serializable
data class OwnerRequestScreenRoute(val destinationName: String, val isActive: Boolean)

@Serializable
object SearchScreenRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppRoot(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SearchScreenRoute
        ) {
            composable<SearchScreenRoute> {
                SearchScreen(
                    modifier = modifier.padding(innerPadding),
                    onNavigateToOwnerRequestScreen = navController::navigateToOwnerRequestScreen
                )
            }

            composable<OwnerRequestScreenRoute> {
                OwnerRequestScreen(
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}

fun NavController.navigateToOwnerRequestScreen(destinationId: String, isActive: Boolean) {
    this.navigate(OwnerRequestScreenRoute(destinationName = destinationId, isActive = isActive))
}