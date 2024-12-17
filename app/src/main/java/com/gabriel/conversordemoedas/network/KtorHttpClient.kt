package com.gabriel.conversordemoedas.network

import com.gabriel.conversordemoedas.network.model.CurrencyTypeResult
import com.gabriel.conversordemoedas.network.model.ExchangeRateResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json

object KtorHttpClient {

    private const val BASE_URL = "http://10.0.2.2:8081"

    private val client = HttpClient(Android) {
        install(Logging)
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getCurrencyTypes(): Result<CurrencyTypeResult> {
        return requireGet("$BASE_URL/currency_types")
    }

    suspend fun getExchangeRate(from: String, to: String): Result<ExchangeRateResult> {
        return requireGet(url = "$BASE_URL/exchange_rate/$from/$to")
    }

    private suspend inline fun <reified T> requireGet(url: String): Result<T> {
        return try {
            val response: HttpResponse = client.get(url)
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}