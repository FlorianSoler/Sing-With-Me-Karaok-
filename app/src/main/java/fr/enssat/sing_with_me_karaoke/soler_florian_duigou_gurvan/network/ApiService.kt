package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network

import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.Song
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val BASE_URL = "https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object API {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("/playlist.json")
    suspend fun getSongs(): List<Song>

    @GET
    suspend fun getLyrics(@Url url: String): String
}