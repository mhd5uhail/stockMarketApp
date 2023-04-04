package com.mhdsuhail.stonks.data.remote

import com.mhdsuhail.stonks.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY,
    ) : ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntraDayInfo(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("symbol") symbol : String
    ) : ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("symbol") symbol: String
    ): CompanyInfoDto

    companion object {
        const val API_KEY = "Q2Z68F4DWXSYEDPG"
        const val BASE_URL = "https://www.alphavantage.co"
    }
}