package com.mhdsuhail.stonks.data.csv

import com.mhdsuhail.stonks.data.mapper.toIntraDayInfo
import com.mhdsuhail.stonks.data.remote.dto.IntraDayInfoDto
import com.mhdsuhail.stonks.domain.model.IntraDayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayInfoParser @Inject constructor() : CSVParser<IntraDayInfo> {
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {

        val csvParser = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO) {

            csvParser.readAll().drop(1).mapNotNull { line ->
                val close = line.getOrNull(4) ?: return@mapNotNull null
                val timeStamp = line.getOrNull(0) ?: return@mapNotNull null
                val dto = IntraDayInfoDto(timeStamp = timeStamp, close = close.toDouble())
                dto.toIntraDayInfo()
            }
        }.filter {
            it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
        }.sortedBy {
            it.date.hour
        }.also {
            csvParser.close()
        }
    }
}