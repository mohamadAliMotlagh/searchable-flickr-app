package com.motlagh.flickerlist.presenter.ui.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.motlagh.flickerlist.presenter.ui.FlickrUI
import com.motlagh.flickerlist.presenter.ui.navigation.ListImageDestination
import com.motlagh.imageviewer.navigation.ShowImageDestination
import com.motlagh.imageviewer.presenter.ShowImageUI
import com.motlagh.uikit.navigator.NavigationDestination


val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
    ShowImageDestination to { ShowImageUI() },
    ListImageDestination to { FlickrUI() }
)

fun NavGraphBuilder.addFlickrComposableDestinations() {
    composableDestinations.forEach { composable ->
        val destination = composable.key
        composable(destination.route(), destination.arguments, destination.deepLinks) {
            composable.value()
        }
    }
}




