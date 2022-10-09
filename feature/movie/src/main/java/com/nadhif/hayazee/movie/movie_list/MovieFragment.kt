package com.nadhif.hayazee.movie.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.nadhif.hayazee.baseview.adapter.MovieAdapter
import com.nadhif.hayazee.baseview.databinding.CarauselItemLayoutBinding
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.BuildConfig
import com.nadhif.hayazee.common.Constant
import com.nadhif.hayazee.common.extension.*
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.movie.R
import com.nadhif.hayazee.movie.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate) {

    @Inject
    lateinit var movieVmFactory: MovieViewModel.Factory

    private val movieViewModel by viewModels<MovieViewModel> { movieVmFactory }

    private lateinit var popularMovieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefresh()
        setupPopularMovies()
        setupNowPlayingMovies()
        observeNowPlayingMovies()
        observePopularMovies()
        movieViewModel.getNowPlayingMovies()
        movieViewModel.getPopularMovies()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            movieViewModel.getNowPlayingMovies()
            movieViewModel.getPopularMovies()
        }
    }

    private fun observeNowPlayingMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            movieViewModel.nowPlayingMovieState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        showNowPlayingMoviesLoading()
                    }

                    is ResponseState.Success -> {
                        binding.apply {
                            shimmerNowPlaying.root.gone()
                            swipeRefreshLayout.isRefreshing = false
                            carouselBanner.gone()
                            ncNowPlayingMovieLayout.visible()
                        }
                    }

                    is ResponseState.SuccessWithData -> {
                        binding.apply {
                            shimmerNowPlaying.root.gone()
                            swipeRefreshLayout.isRefreshing = false
                            carouselBanner.visible()
                            carouselBanner.setData(it.data)
                        }
                    }

                    is ResponseState.Error -> {
                        showErrorMessage(it.message ?: "Retrive data failed")
                    }
                }
            }
        }
    }


    private fun observePopularMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            movieViewModel.popularMoviesState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        showPopularMoviesLoading()
                    }

                    is ResponseState.SuccessWithData -> {
                        binding.apply {
                            swipeRefreshLayout.isRefreshing = false
                            shimmerPopularMovie.root.gone()
                            rvMovies.visible()
                            popularMovieAdapter.submitList(it.data)
                        }
                    }

                    is ResponseState.Success -> {
                        binding.apply {
                            swipeRefreshLayout.isRefreshing = false
                            shimmerPopularMovie.root.gone()
                            rvMovies.gone()
                            ncPopularMovieLayout.visible()
                        }
                    }


                    is ResponseState.Error -> {
                        showErrorMessage(it.message ?: "Retrive data failed")
                    }
                }

            }
        }
    }

    private fun setupNowPlayingMovies() {
        binding.carouselBanner.apply {
            registerLifecycle(viewLifecycleOwner)
            carouselListener = object : CarouselListener {

                override fun onBindViewHolder(
                    binding: ViewBinding,
                    item: CarouselItem,
                    position: Int
                ) {
                    val carouselBinding = binding as CarauselItemLayoutBinding
                    carouselBinding.apply {
                        ivBackdrop.loadImage(
                            BuildConfig.BACKDROP_URL + item.imageUrl
                        )
                        tvTitle.text = item.caption
                    }
                }

                override fun onClick(position: Int, carouselItem: CarouselItem) {

                }

                override fun onCreateViewHolder(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup
                ): ViewBinding {
                    return CarauselItemLayoutBinding.inflate(layoutInflater, parent, false)
                }
            }
        }
    }

    private fun setupPopularMovies() {
        binding.apply {

            popularMovieAdapter = MovieAdapter {
                navigateToMovieDetail(it)
            }
            rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
            rvMovies.adapter = popularMovieAdapter
        }
    }

    private fun showNowPlayingMoviesLoading() {
        binding.apply {
            carouselBanner.invisible()
            shimmerNowPlaying.root.visible()
        }
    }

    private fun showPopularMoviesLoading() {
        binding.apply {
            rvMovies.invisible()
            shimmerPopularMovie.root.visible()
        }
    }


    private fun showErrorMessage(message: String) {
        binding.apply {
            swipeRefreshLayout.isRefreshing = false
            root.showErrorSnackBar(message)
        }
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.MOVIE_DATA, movie)
        findNavController().navigate(R.id.action_movie_fragment_to_movie_detail_fragment, bundle)
    }
}