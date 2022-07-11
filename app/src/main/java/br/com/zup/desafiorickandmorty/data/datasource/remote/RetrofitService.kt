package br.com.zup.desafiorickandmorty.data.datasource.remote

import br.com.zup.desafiorickandmorty.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    companion object {
        const val BASE_URL ="https://rickandmortyapi.com/api/"

        // inicializamos o retrofit com as configurações
        private val retrofit: Retrofit by lazy {

            //Configura parametros de conexão
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.writeTimeout(30, TimeUnit.SECONDS)


            // Se estivermos em modo DEBUG habilitamos os logs
            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logInterceptor)
            }

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }


        @JvmStatic
        val apiService: RickAndMortyAPI
            get() = retrofit.create(RickAndMortyAPI::class.java)
    }
}