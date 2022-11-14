package com.camacho.cucsur.apis

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkController {

    private var retrofit: Retrofit? = null
    private var rickAndMortyAPI: RickAndMortyAPI? = null


    private fun createRetrofit(): Retrofit {
        return retrofit.let {
            if (it == null) {
                val retro = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofit = retro
                retro
            } else {
                it
            }
        }
    }

    private fun createRickAndMortyAPI(): RickAndMortyAPI {
        if (rickAndMortyAPI == null) {
            rickAndMortyAPI = createRetrofit().create(RickAndMortyAPI::class.java)
        }
        return rickAndMortyAPI!!
    }

    fun getRickAndMortyAPI(): RickAndMortyAPI {
        return createRickAndMortyAPI()
    }

}