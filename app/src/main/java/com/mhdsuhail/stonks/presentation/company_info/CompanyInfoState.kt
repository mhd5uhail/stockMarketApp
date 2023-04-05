package com.mhdsuhail.stonks.presentation.company_info

import com.mhdsuhail.stonks.domain.model.CompanyInfo
import com.mhdsuhail.stonks.domain.model.IntraDayInfo

data class CompanyInfoState(
    val listOfIntraDayInfo: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)