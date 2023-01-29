package com.motlagh.flickerlist.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.domain.GetListUseCase
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.imageviewer.navigation.ShowImageDestination
import com.motlagh.quicksearch.domain.usecase.GetSavedQueriesUseCase
import com.motlagh.quicksearch.domain.usecase.SaveQueryUseCase
import com.motlagh.uikit.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val getQueries: GetSavedQueriesUseCase,
    private val saveQuery: SaveQueryUseCase,
    private val navigator: Navigator,
) :
    ViewModel(), Navigator by navigator {

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    private val _images = MutableStateFlow(listOf<FlickrModel>())
    val images = _images.asStateFlow()

    private val _recentSearchItems = MutableStateFlow(listOf<String>())
    val recentSearchItems = _recentSearchItems.asStateFlow()

    fun searchText(text: String) {
        _queryText.value = text
    }

    init {
        viewModelScope.launch {
            launch { loadRecentSearch() }
            queryText.debounce(1000).collect { text ->

                if (text.isEmpty()) return@collect
                launch { callApi(text) }
                launch { saveSearchedQuery(text) }
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            callApi(query)
        }
    }

    private suspend fun callApi(text: String) {
        getListUseCase(text).onSuccess {
            _images.value = it
        }.onFailure {

        }
    }

    fun navigateToShowImage(mainImageAddress: String) {
        navigate(ShowImageDestination.createShowImageRoute(mainImageAddress))
    }

    private suspend fun saveSearchedQuery(query: String) {
        saveQuery.invoke(query)
    }

    private suspend fun loadRecentSearch() {
        getQueries().collect {
            it.onSuccess {
                _recentSearchItems.value = it.map { it.query }

            }.onFailure {

            }
        }
    }


}