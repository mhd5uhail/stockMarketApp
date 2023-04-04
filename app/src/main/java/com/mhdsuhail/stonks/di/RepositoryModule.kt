package com.mhdsuhail.stonks.di

import com.mhdsuhail.stonks.data.csv.CSVParser
import com.mhdsuhail.stonks.data.csv.CompanyListingParser
import com.mhdsuhail.stonks.data.repository.StockRepositoryImpl
import com.mhdsuhail.stonks.domain.model.CompanyListing
import com.mhdsuhail.stonks.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ) : CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun  bindsStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}