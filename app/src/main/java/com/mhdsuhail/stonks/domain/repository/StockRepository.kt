package com.mhdsuhail.stonks.domain.repository

import com.mhdsuhail.stonks.domain.model.CompanyInfo
import com.mhdsuhail.stonks.domain.model.CompanyListing
import com.mhdsuhail.stonks.domain.model.IntraDayInfo
import com.mhdsuhail.stonks.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean, // Forces the function to get the data from the API
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>
}