package ufaz.az.meezer.ui.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    // Placeholder method for searching tracks or albums
    fun search(query: String, isTrackSearch: Boolean) {
        // TODO: Implement search logic using Deezer API
    }
}
