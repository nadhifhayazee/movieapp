package com.nadhif.hayazee.tv.tv_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadhif.hayazee.baseview.databinding.FragmentMovieDetailBinding
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.BuildConfig
import com.nadhif.hayazee.common.Constant
import com.nadhif.hayazee.common.extension.getRuntimeString
import com.nadhif.hayazee.common.extension.loadImage
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class TvDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    @Inject
    lateinit var movieDetailVmFactory: TvDetailViewModel.Factory
    private val movieDetailViewModel by viewModels<TvDetailViewModel> { movieDetailVmFactory }

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(Constant.MOVIE_DATA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBack()
        setupView()
        observeVm()
        movieDetailViewModel.getTvDetail(movie?.id.toString())


    }


    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            movieDetailViewModel.tvDetailState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.SuccessWithData -> {
                        movie = it.data
                        setupView()

                    }
                    is ResponseState.Error -> {

                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            movie?.let {

                tvTitleDetail.text = it.name
                tvOverview.text = it.overview
                ivBackdropDetail.loadImage(
                    BuildConfig.BACKDROP_URL + it.backdrop_path
                )
                ivPosterDetail.loadImage(
                    BuildConfig.IMAGE_URL + it.poster_path
                )
                tvRating.text = it.vote_average.toString()
                tvGenres.text = it.getGenres()
                tvRuntime.text = it.episode_run_time?.getOrNull(0)?.getRuntimeString()
            }
        }
    }

    private fun setupBack() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}