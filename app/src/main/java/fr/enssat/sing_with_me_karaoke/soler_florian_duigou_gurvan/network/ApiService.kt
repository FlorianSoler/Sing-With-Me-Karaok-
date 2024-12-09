package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object API {
    val restrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("/playlist.json")
    fun getSongs(): Call<String>
}