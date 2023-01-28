package com.motlagh.flickerlist.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.domain.GetListUseCase
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.imageviewer.navigation.ShowImageDestination
import com.motlagh.uikit.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val navigator: Navigator,
) :
    ViewModel(), Navigator by navigator {

    private val _searchedText = MutableStateFlow("")//query
    val searchedText = _searchedText.asStateFlow()

    private val _images = MutableStateFlow(listOf<FlickrModel>())
    val images = _images.asStateFlow()

    fun searchText(text: String) {
        _searchedText.value = text
    }

    init {
        viewModelScope.launch {

            searchedText.debounce(1000).collect { text ->

                if (text.isEmpty()) return@collect
                callApi(text)
            }
        }
    }

    private suspend fun callApi(text: String) {
        getListUseCase(text).collect {
            when (it) {
                is ResultModel.Error -> TODO()
                is ResultModel.Success -> {
                    _images.value = it.value
                }
            }
        }
    }

    fun navigateToShowImage(mainImageAddress: String) {
        navigate(ShowImageDestination.createShowImageRoute(mainImageAddress))
    }


}