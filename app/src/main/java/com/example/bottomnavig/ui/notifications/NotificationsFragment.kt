package com.example.bottomnavig.ui.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavig.databinding.FragmentNotificationsBinding
import com.example.bottomnavig.ui.MoviesAPI
import com.example.bottomnavig.ui.adapters.MoviesAdapter
import com.example.bottomnavig.ui.models.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

        context?.let {
            adapter = MoviesAdapter.create(it)
            binding.rvMovies.layoutManager = LinearLayoutManager(it)
            binding.rvMovies.adapter = adapter
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val moviesAPI: MoviesAPI = retrofit.create(MoviesAPI::class.java)

        moviesAPI.getData().enqueue(object : Callback<List<Movies>> {

            override fun onResponse(call: Call<List<Movies>>, response: Response<List<Movies>>) {
                if(response.isSuccessful)
                response.body()?.let { movies ->
                    Log.d("QWE", "QWE")
                    adapter.refresh(movies)
                }
            }
            override fun onFailure(call: Call<List<Movies>>, t: Throwable) {
                Log.e("Error", "Failed to fetch data", t)
            }
        })
        val dashboardViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
