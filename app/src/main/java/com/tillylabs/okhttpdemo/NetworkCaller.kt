package com.tillylabs.okhttpdemo

import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by steven on 2017-10-13.
 */

class NetworkCaller {

    private val client: OkHttpClient

    private val service: WebService

    interface WebService{
        /**
         * @GET declares an HTTP GET request
         * @Path("user") annotation on the userId parameter marks it as a
         * replacement for the {user} placeholder in the @GET path
         */
        @GET("/data/2.5/weather")
        fun getWeatherData(@Query("q") city: String,
                           @Query("APPID") appid: String,
                           @Query("units") unit: String): Call<JsonElement>

    }

    init {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)
        client = builder.build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        service = retrofit.create(WebService::class.java)
    }

    //api.openweathermap.org/data/2.5/forecast?q={city name},{country code}

    @Throws(Exception::class)
    fun run(url: String): JsonElement? {
        //val request = Request.Builder()
          //      .url("http://api.openweathermap.org/data/2.5/weather?q=Montreal&APPID=c7ad5378667fcc9a675de8d4625ae369&units=metric")
          //      .get()
          //      .build()

        //val response = client.newCall(request).execute()
        return service.getWeatherData(url, APPID, UNIT).execute().body()
    }

    companion object {

        private var networkCaller: NetworkCaller? = null

        val APPID = "c7ad5378667fcc9a675de8d4625ae369"

        val UNIT = "metric"


        val instance: NetworkCaller
            get() {
                if (networkCaller == null) {
                    networkCaller = NetworkCaller()
                }
                return networkCaller!!
            }
    }
}
