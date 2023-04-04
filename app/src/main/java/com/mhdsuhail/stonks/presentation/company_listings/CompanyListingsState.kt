package com.mhdsuhail.stonks.presentation.company_listings

import com.mhdsuhail.stonks.domain.model.CompanyListing

data class CompanyListingsState(
    val companies : List<CompanyListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = ""
)
