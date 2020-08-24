package br.cericatto.easynvest.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api-simulator-calc.easynvest.com.br/calculator/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EasyApiService {
    @GET("simulate")
    suspend fun getProperties(
        @Query("investedAmount") investedAmount: Double,
        @Query("index") index: String = "CDI",
        @Query("rate") rate: Double,
        @Query("isTaxFree") isTaxFree: Boolean = false,
        @Query("maturityDate") maturityDate: String
    ): EasyProperty
}

object EasyApi {
    val retrofitService : EasyApiService by lazy {
        retrofit.create(EasyApiService::class.java)
    }
}