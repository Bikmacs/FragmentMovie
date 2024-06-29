package com.example.bottomnavig.ui.notifications

import MovieAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavig.databinding.FragmentNotificationsBinding
import com.example.bottomnavig.ui.MoviesAPI
import com.example.bottomnavig.ui.models.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val moviesAPI: MoviesAPI = retrofit.create(MoviesAPI::class.java)

        moviesAPI.getData().enqueue(object : Callback<List<Movies>> {
            override fun onResponse(call: Call<List<Movies>>, response: Response<List<Movies>>) {
                if (response.isSuccessful) {
                    response.body()?.let { movies ->
                        adapter.refresh(movies)
                    }
                } else {
                    Log.e("Error", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Movies>>, t: Throwable) {
                Log.e("Error", "Failed to fetch data", t)
            }
        })

        return view
    }

    private fun setupRecyclerView() {
        context?.let { context ->
            adapter = MovieAdapter.create(context) { url ->
                navigateToMovieFragment(url)
            }
            binding.rvMovies.layoutManager = LinearLayoutManager(context)
            binding.rvMovies.adapter = adapter
        }
    }

    private fun navigateToMovieFragment(url: String) {
        val pattern = Pattern.compile("https://www.imdb.com/title/(.+?)/")
        val matcher = pattern.matcher(url)
        if (matcher.matches()) {
            val movieId = matcher.group(1)
            findNavController().navigate(NotificationsFragmentDirections.actionNavigationNotificationsToMovieFragment(movieId))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
