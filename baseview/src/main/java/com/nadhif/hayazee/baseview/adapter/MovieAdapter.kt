package com.nadhif.hayazee.baseview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadhif.hayazee.baseview.databinding.MovieItemLayoutBinding
import com.nadhif.hayazee.common.BuildConfig
import com.nadhif.hayazee.common.extension.loadImage
import com.nadhif.hayazee.common.model.Movie

class MovieAdapter(private val onItemClicked: (Movie) -> Unit) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(diffUtil) {


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(movie: Movie) {

            binding.apply {
                ivPoster.loadImage(BuildConfig.BACKDROP_URL + movie.poster_path)
                tvTitle.text = movie.title ?: movie.original_name
                root.setOnClickListener {
                    onItemClicked.invoke(movie)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}