package com.taghda.workingtestapplication.data.remote

import androidx.annotation.NonNull
import com.taghda.workingtestapplication.BuildConfig
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

@GET("v1/latest?")
fun searchForImage(
    @Query("access_key") apiKey: String ,
    @Query("base") base: String
): Observable<Call<ExchangeRateResponse>>

}
