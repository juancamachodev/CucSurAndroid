package com.camacho.cucsur.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camacho.cucsur.apis.NetworkController
import com.camacho.cucsur.models.CharacterDetail
import com.camacho.cucsur.models.CharactersDTO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {

    val charactersList: MutableList<CharacterDetail> = mutableStateListOf()
    var pageCounter: Int? = 1

    init {
        getAllCharacters()
    }

    fun getAllCharacters() {
        viewModelScope.launch {
            pageCounter?.let { page ->
                NetworkController.getRickAndMortyAPI().getAllCharacters(page).enqueue(
                    object : Callback<CharactersDTO> {
                        override fun onResponse(
                            call: Call<CharactersDTO>,
                            response: Response<CharactersDTO>
                        ) {
                            /**
                             * Validacion para evitar un Body vacio
                             */
                            response.body()?.let {
                                pageCounter = it.info.next?.split("=")?.get(1)?.toIntOrNull()
                                charactersList.addAll(it.results)
                            }
                        }

                        override fun onFailure(call: Call<CharactersDTO>, t: Throwable) {
                        }
                    }
                )
            }
        }
    }
}