import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavig.R
import com.example.bottomnavig.ui.models.Movies

class MovieAdapter(private val context: Context, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<Movies> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        // Bind data to ViewHolder here
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun refresh(newMovies: List<Movies>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder implementation here
        fun bind(movie: Movies) {
            // Bind movie data to views
            itemView.setOnClickListener {
                onItemClick.invoke(movie.imdb_url) // Assuming 'url' is the property in Movies class
            }
        }
    }

    companion object {
        fun create(context: Context, onItemClick: (String) -> Unit): MovieAdapter {
            return MovieAdapter(context, onItemClick)
        }
    }
}
