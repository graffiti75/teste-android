package br.cericatto.easynvest.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api-simulator-calc.easynvest.com.br"
const val MATURITY_DATE = "2020-12-31T00:00:00"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EasyApiService {
    @GET("calculator/simulate")
    suspend fun getProperties(
        @Query("investedAmount") investedAmount: Double,
        @Query("index") index: String = "CDI",
        @Query("rate") rate: Double,
        @Query("isTaxFree") isTaxFree: Boolean = false,
        @Query("maturityDate") maturityDate: String
    ): EasyProperty

    @GET("calculator/simulate")
    fun getTestProperties(
        @Query("investedAmount") investedAmount: Double = 1000.00,
        @Query("index") index: String = "CDI",
        @Query("rate") rate: Double = 123.00,
        @Query("isTaxFree") isTaxFree: Boolean = false,
        @Query("maturityDate") maturityDate: String = "2020-12-31"
    ): Call<EasyProperty>
}

object EasyApi {
    val retrofitService : EasyApiService by lazy {
        retrofit.create(EasyApiService::class.java)
    }
}