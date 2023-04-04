package com.mhdsuhail.stonks.data.mapper

import com.mhdsuhail.stonks.data.remote.dto.IntraDayInfoDto
import com.mhdsuhail.stonks.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntraDayInfoDto.toIntraDayInfo(): IntraDayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timeStamp, formatter)
    return IntraDayInfo(
        date = localDateTime,
        close = close
    )
}

fun IntraDayInfo.toIntraDayInfoDto(): IntraDayInfoDto {
    return IntraDayInfoDto(
        timeStamp = date.toString(),
        close = close
    )
}