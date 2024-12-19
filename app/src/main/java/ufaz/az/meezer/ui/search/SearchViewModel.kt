package ufaz.az.meezer.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ufaz.az.meezer.data.api.DeezerApiService
import ufaz.az.meezer.data.api.SearchResult
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val deezerApiService: DeezerApiService
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults

    fun search(query: String, isTrackSearch: Boolean) {
        val type = if (isTrackSearch) "track" else "album"
        var queryy = type + ":" + "\"" + query + "\""
        viewModelScope.launch {
            try {
                val response = deezerApiService.search(queryy)
                _searchResults.value = response.data
            } catch (e: Exception) {
                // Handle errors (e.g., log or show error UI)
                e.printStackTrace()
            }
        }
    }

    fun searchForTracks(query: String) {

    }
}
