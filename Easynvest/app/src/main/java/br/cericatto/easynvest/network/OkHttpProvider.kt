package br.cericatto.easynvest.network

import okhttp3.OkHttpClient

object OkHttpProvider {
    val instance: OkHttpClient = OkHttpClient.Builder().build()
}