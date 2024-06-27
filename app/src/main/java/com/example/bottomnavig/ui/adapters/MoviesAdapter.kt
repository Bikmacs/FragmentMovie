package com.example.bottomnavig.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavig.databinding.MovieItemBinding
import com.example.bottomnavig.ui.models.Movies

class MoviesAdapter private constructor(private val context: Context) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var items: List<Movies> = emptyList()

    companion object Factory {
        fun create(context: Context): MoviesAdapter {
            return MoviesAdapter(context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.sortedByDescending { it.rating }[position]
        holder.tvMovieName.text = item.movie
        holder.tvRating.text = item.rating.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
       val tvMovieName = binding.movieName
        val tvRating = binding.ratingMovie
    }

    fun refresh(items: List<Movies>) {
        this.items = items
        notifyDataSetChanged()
    }
}
