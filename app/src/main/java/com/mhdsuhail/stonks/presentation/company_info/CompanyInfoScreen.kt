package com.mhdsuhail.stonks.presentation.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhdsuhail.stonks.ui.theme.DarkBlue
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyInfoScreen(symbol: String, viewModel: CompanyInfoViewModel = hiltViewModel()) {
    val state = viewModel.state

    if (state.error == null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp)
        ) {

            state.company?.let { companyInfo ->
                Text(
                    text = companyInfo.name,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyInfo.symbol,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyInfo.industry,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyInfo.country,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyInfo.description,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (state.listOfIntraDayInfo.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Market Summary")
                    StockChart(
                        infos = state.listOfIntraDayInfo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else if (state.error != null) {
                        Text(
                            text = state.error ?: "",
                            color = MaterialTheme.colors.error
                        )
                    }
                }
            }

        }


    }
}