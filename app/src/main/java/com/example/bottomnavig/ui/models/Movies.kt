package com.example.bottomnavig.ui.models

import android.media.Image
import java.net.URL
import retrofit2.http.Url

class Movies(val id:Int, val movie:String, val rating: Double, val image:String, val imdb_url:String) {
}