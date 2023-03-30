package universal.appfactory.smartcity.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private const val SERVER_URL_1: String = "https://www.snap4city.org/superservicemap/api/"
    private const val SERVER_URL_2: String = "https://www.km4city.org/"
    private const val SERVER_URL_3: String = "https://servicemap.disit.org/WebAppGrafo/api/"

    private val client = OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS).build()

    private val retrofitURL1 = Retrofit.Builder()
        .baseUrl(SERVER_URL_1) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val retrofitURL2 = Retrofit.Builder()
        .baseUrl(SERVER_URL_2) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    fun<T> buildServiceURL1(service: Class<T>): T{
        return retrofitURL1.create(service)
    }

    fun<T> buildServiceURL2(service: Class<T>): T{
        return retrofitURL2.create(service)
    }



}