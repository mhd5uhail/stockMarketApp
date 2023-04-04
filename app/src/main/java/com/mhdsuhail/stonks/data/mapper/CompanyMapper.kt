package com.mhdsuhail.stonks.data.mapper

import com.mhdsuhail.stonks.data.local.CompanyListingEntity
import com.mhdsuhail.stonks.data.remote.dto.CompanyInfoDto
import com.mhdsuhail.stonks.domain.model.CompanyInfo
import com.mhdsuhail.stonks.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return  CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return  CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo() : CompanyInfo {

    return CompanyInfo(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}