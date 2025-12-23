package com.example.travenorowner.features.search

import androidx.lifecycle.viewModelScope
import com.example.travenorowner.data.DestinationsRepository
import com.example.travenorowner.features.BaseViewModel
import com.example.travenorowner.features.search.SearchEvent.*
import com.example.travenorowner.model.Destination
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    val destinations: DestinationsRepository
) : BaseViewModel<SearchState, SearchAction, SearchEvent>(initialState = SearchState()) {

    init {
        getAllDestinations()
    }

    private fun getAllDestinations() {
        viewModelScope.launch {
            destinations.getAllDestination()
                .onStart {
                    mutableStateFlow.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                .catch { error ->

                }
                .collect { data ->
                    mutableStateFlow.update {
                        it.copy(
                            destinations = data,
                            filterDestination = data,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    override fun handleAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnNavigateToDetailScreen -> {
                sendEvent(
                    NavigateToDetailScreen(action.destinationId)
                )
            }

            is SearchAction.OnSearchQueryChange -> {
                mutableStateFlow.update {
                    it.copy(
                        searchQuery = action.query,
                        filterDestination = filterPlaces(
                            query = action.query,
                            destination = state.destinations
                        )
                    )
                }

            }
        }
    }
}


data class SearchState(
    val destinations: List<Destination> = emptyList(),
    val filterDestination: List<Destination> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
)

sealed interface SearchEvent {
    data class NavigateToDetailScreen(val destinationId: String) :
        SearchEvent
}

sealed interface SearchAction {
    data class OnNavigateToDetailScreen(val destinationId: String) :
        SearchAction

    data class OnSearchQueryChange(val query: String) : SearchAction
}

fun filterPlaces(
    destination: List<Destination>,
    query: String
): List<Destination> {

    if (query.isBlank()) return destination

    return destination
        .filter {
            it.destinationName.contains(query, ignoreCase = true) ||
                    it.city.contains(query, ignoreCase = true)
        }
        .sortedBy {
            it.destinationName.lowercase()
        }
}
