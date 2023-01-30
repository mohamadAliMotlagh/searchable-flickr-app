package com.motlagh.flickrlist.presenter.ui.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.motlagh.flickrlist.presenter.ui.FlickrUI
import com.motlagh.flickrlist.presenter.ui.navigation.ListImageDestination
import com.motlagh.imageviewer.navigation.ShowImageDestination
import com.motlagh.imageviewer.presenter.ShowImageUI
import com.motlagh.uikit.navigator.NavigationDestination

//it creates a map of destinations.
val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
    ShowImageDestination to { ShowImageUI() },
    ListImageDestination to { FlickrUI() }
)

//it adds all destinations to graph
fun NavGraphBuilder.addFlickrComposableDestinations() {
    composableDestinations.forEach { composable ->
        val destination = composable.key
        composable(destination.route(), destination.arguments, destination.deepLinks) {
            composable.value()
        }
    }
}




