package com.camacho.cucsur.apis

import com.camacho.cucsur.models.CharactersDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    fun getAllCharacters(@Query("page") page: Int): Call<CharactersDTO>

}