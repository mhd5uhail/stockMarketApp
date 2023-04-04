package com.mhdsuhail.stonks.data.repository

import com.mhdsuhail.stonks.data.csv.CSVParser
import com.mhdsuhail.stonks.data.csv.IntraDayInfoParser
import com.mhdsuhail.stonks.data.local.StockDatabase
import com.mhdsuhail.stonks.data.mapper.toCompanyInfo
import com.mhdsuhail.stonks.data.mapper.toCompanyListing
import com.mhdsuhail.stonks.data.mapper.toCompanyListingEntity
import com.mhdsuhail.stonks.data.remote.StockAPI
import com.mhdsuhail.stonks.domain.model.CompanyInfo
import com.mhdsuhail.stonks.domain.model.CompanyListing
import com.mhdsuhail.stonks.domain.model.IntraDayInfo
import com.mhdsuhail.stonks.domain.repository.StockRepository
import com.mhdsuhail.stonks.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockAPI,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>
) : StockRepository {

    private val dao = db.stockDao

    // Only responsibility of this function is to cache data - SOLID principle
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {

        return flow {

            emit(Resource.Loading(true))

            val localListings = dao.searchCompanyListings(query)

            emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))

            // Do we want to make an API call ?

            val isDbEmpty = localListings.isEmpty() && query.isBlank()

            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {

                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not parse data !"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Unable to connect to api! "))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(
                    Resource.Success(
                        data = dao.searchCompanyListings("").map { it.toCompanyListing() })
                )
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {

        return try {
            val response = api.getIntraDayInfo(symbol = symbol)
            // TODO: parse this csv list
            val intraDatInfoList = intraDayInfoParser.parse(response.byteStream())

            return Resource.Success(data = intraDatInfoList)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Failed to fetch intra day info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Failed to fetch intra day info")
        }

    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol = symbol)
            Resource.Success(data = response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Failed to fetch info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Failed to fetch info")
        }

    }
}