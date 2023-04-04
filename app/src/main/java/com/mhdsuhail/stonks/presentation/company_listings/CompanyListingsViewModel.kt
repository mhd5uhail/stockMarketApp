package com.mhdsuhail.stonks.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhdsuhail.stonks.domain.repository.StockRepository
import com.mhdsuhail.stonks.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingsEvents) {

        when (event) {

            is CompanyListingsEvents.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }

            is CompanyListingsEvents.SearchQueryChange -> {

                state = state.copy(searchQuery = event.query);
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    //Note:  This is to ensure that there are search request done for each and every change in text field in quick succession
                    delay(500L)
                    getCompanyListings(event.query)
                }
            }

        }

    }

    fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {

            repository.getCompanyListings(fetchFromRemote, query).collect { result ->

                when(result){

                    is Resource.Success -> {
                        result.data?.let { listings ->
                            state = state.copy(
                                companies = listings
                            )
                        }
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Error -> Unit

                }

            }
        }
    }
}