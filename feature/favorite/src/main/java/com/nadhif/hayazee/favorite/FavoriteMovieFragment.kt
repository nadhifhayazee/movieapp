package com.nadhif.hayazee.favorite

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nadhif.hayazee.baseview.adapter.MovieAdapter
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.Constant
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.showErrorSnackBar
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.favorite.databinding.FragmentFavoriteMovieBinding
import com.nadhif.hayazee.favorite.viewmodel.FavoriteMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMovieFragment :
    BaseFragment<FragmentFavoriteMovieBinding>(FragmentFavoriteMovieBinding::inflate) {

    @Inject
    lateinit var favMovieVmFactory: FavoriteMovieViewModel.Factory
    private val favoriteMovieViewModel by viewModels<FavoriteMovieViewModel> { favMovieVmFactory }

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        observeFavoriteMovies()
        favoriteMovieViewModel.getFavoriteMovies()
    }

    private fun setupAdapter() {
        binding.apply {
            movieAdapter = MovieAdapter { movie ->
                navigateToDetail(movie)
            }
            rvFavorite.layoutManager = GridLayoutManager(requireContext(), 2)
            rvFavorite.adapter = movieAdapter
        }
    }

    private fun navigateToDetail(movie: Movie) {
        findNavController().navigate(Constant.geDetailMovieDeeplink(movie.id.toString()))
    }

    private fun observeFavoriteMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            favoriteMovieViewModel.favoriteMoviesState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.apply {
                            shimmerPopularMovie.root.visible()
                            rvFavorite.gone()
                            ncFavoriteMovieLayout.gone()
                        }
                    }
                    is ResponseState.SuccessWithData -> {
                        binding.apply {
                            shimmerPopularMovie.root.gone()
                            rvFavorite.visible()
                            ncFavoriteMovieLayout.gone()
                        }
                        movieAdapter.submitList(it.data)
                    }

                    is ResponseState.Success -> {
                        binding.apply {
                            shimmerPopularMovie.root.gone()
                            rvFavorite.gone()
                            ncFavoriteMovieLayout.visible()
                        }
                    }
                    is ResponseState.Error -> {
                        binding.apply {
                            shimmerPopularMovie.root.gone()
                            rvFavorite.gone()
                            ncFavoriteMovieLayout.visible()
                            root.showErrorSnackBar(it.message)
                        }
                    }
                }
            }
        }
    }

}