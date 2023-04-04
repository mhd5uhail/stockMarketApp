package com.mhdsuhail.stonks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistings")
    suspend fun clearCompanyListings()

    @Query("""
        SELECT * 
        FROM companylistings
        WHERE
        LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListings(query: String)  : List<CompanyListingEntity>
}