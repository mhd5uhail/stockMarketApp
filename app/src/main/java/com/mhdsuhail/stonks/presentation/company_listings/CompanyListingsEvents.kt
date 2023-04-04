package com.mhdsuhail.stonks.presentation.company_listings

sealed class CompanyListingsEvents {
    // Refresh
    // Type on search field
    object Refresh : CompanyListingsEvents()
    data class SearchQueryChange(val query:String) : CompanyListingsEvents()

}