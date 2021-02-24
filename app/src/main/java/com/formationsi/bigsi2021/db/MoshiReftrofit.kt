package com.formationsi.bigsi2021.db

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL ="https://android-kotlin-fun-mars-server.appspot.com/"
   // "https://spreadsheets.google.com/feeds/worksheets/1F49X3Jo823vUJ9hrr1vheCeCI2LhCIN_gf9sxMrgK5k/public/basic?alt=json"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface MoshiRetrofitApiService {
    @GET("realestate")
    suspend fun getProperties(): List<School>

}

object MoshiRetrofitApi {
    val retrofitService : MoshiRetrofitApiService by lazy { retrofit.create(MoshiRetrofitApiService::class.java) }
}