package com.gabriel.conversordemoedas.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyType(
    val name: String,
    val acronym: String,
    val symbol: String,
    @SerialName("country_flag_image_url")
    val countryFlagImageUrl: String
)

@Serializable
data class CurrencyTypeResult(
    val values: List<CurrencyType>
)