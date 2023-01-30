package com.motlagh.flickerlist.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motlagh.flickerlist.domain.GetListUseCase
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.imageviewer.navigation.ShowImageDestination
import com.motlagh.quicksearch.domain.usecase.GetSavedQueriesUseCase
import com.motlagh.quicksearch.domain.usecase.SaveQueryUseCase
import com.motlagh.uikit.*
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

    companion object {
        const val debounceDelay = 500L
    }

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    private val _images = MutableStateFlow<ViewState<List<FlickrModel>>>(Idle)
    val images = _images.asStateFlow()

    private val _recentSearchItems = MutableStateFlow(listOf<String>())
    val recentSearchItems = _recentSearchItems.asStateFlow()

    fun requestSearchWithQueryText(text: String) {
        _queryText.value = text
    }

    fun retrySearchWithQuery() {
        viewModelScope.launch {
            callApi(queryText.value)
        }
    }

    init {
        viewModelScope.launch {
            launch { loadRecentSearch() }
            queryText.debounce(debounceDelay).collect { text ->

                if (text.isEmpty()) {
                    _images.value = ViewData(listOf())
                    return@collect
                }
                launch { callApi(text) }
                launch { saveSearchedQuery(text) }
            }
        }
    }

    private suspend fun callApi(text: String) {
        _images.value = ViewLoading
        getListUseCase(text).onSuccess {
            if (it.isEmpty()){
                _images.value = ViewError("no results found")
            }else{
                _images.value = ViewData(it)
            }

        }.onFailure {
            _images.value = ViewError("An error occurred.")
        }
    }

    fun navigateToShowImage(mainImageAddress: String) {
        navigate(ShowImageDestination.createShowImageRoute(mainImageAddress))
    }

    private suspend fun saveSearchedQuery(query: String) {
        saveQuery.invoke(query).onFailure {
            _recentSearchItems.value = listOf()
        }
    }

    private suspend fun loadRecentSearch() {
        getQueries().collect { result ->
            result.onSuccess { queriesList ->
                _recentSearchItems.value = queriesList.map { it.query }
            }.onFailure {
                _recentSearchItems.value = listOf()
            }
        }
    }
}