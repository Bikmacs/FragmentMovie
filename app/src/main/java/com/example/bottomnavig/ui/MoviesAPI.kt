package com.example.bottomnavig.ui

import com.example.bottomnavig.ui.models.Movies
import retrofit2.Call
import retrofit2.http.GET


interface MoviesAPI {
    @GET("movies")
    fun getData(): Call<List<Movies>>

}