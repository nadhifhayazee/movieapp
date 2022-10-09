package com.nadhif.hayazee.tv.tv_list

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
import com.nadhif.hayazee.tv.R
import com.nadhif.hayazee.tv.databinding.FragmentTvBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject

@AndroidEntryPoint
class TvFragment : BaseFragment<FragmentTvBinding>(FragmentTvBinding::inflate) {

    @Inject
    lateinit var tvVmFactory: TvViewModel.Factory

    private val tvViewModel by viewModels<TvViewModel> { tvVmFactory }

    private lateinit var popularTvAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupSwipeRefresh()
        setupPopularMovies()
        setupNowPlayingMovies()
        observeNowPlayingMovies()
        observePopularMovies()
        tvViewModel.getNowPlayingTv()
        tvViewModel.getPopularTv()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            tvViewModel.getNowPlayingTv()
            tvViewModel.getPopularTv()
        }
    }

    private fun observeNowPlayingMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            tvViewModel.nowPlayingTvState.collectLatest {
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
            tvViewModel.popularTvState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        showPopularMoviesLoading()
                    }

                    is ResponseState.SuccessWithData -> {
                        binding.apply {
                            swipeRefreshLayout.isRefreshing = false
                            shimmerPopularMovie.root.gone()
                            rvMovies.visible()
                            popularTvAdapter.submitList(it.data)
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

            popularTvAdapter = MovieAdapter {
                navigateToMovieDetail(it)
            }
            rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
            rvMovies.adapter = popularTvAdapter
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
        findNavController().navigate(R.id.action_tv_fragment_to_tv_detail_fragment, bundle)

    }
}