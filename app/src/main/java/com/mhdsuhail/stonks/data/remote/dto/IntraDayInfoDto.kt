package com.mhdsuhail.stonks.data.remote.dto

import com.squareup.moshi.Json

data class IntraDayInfoDto (
    val timeStamp: String,
    val close: Double
)