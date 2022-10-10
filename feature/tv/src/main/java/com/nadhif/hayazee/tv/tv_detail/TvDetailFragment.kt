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
    lateinit var tvDetailVmFactory: TvDetailViewModel.Factory
    private val tvDetailViewModel by viewModels<TvDetailViewModel> { tvDetailVmFactory }

    private var movie: Movie? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(Constant.MOVIE_DATA)
            id = it.getString(Constant.MOVIE_ID) ?: movie?.id.toString()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupView()
        observeVm()
        observeFavoriteMovie()
        observeFavoriteButtonClicked()
        id?.let {
            tvDetailViewModel.isFavoriteTv(it.toInt())
            tvDetailViewModel.getTvDetail(it)
        }


    }

    private fun observeFavoriteButtonClicked() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            tvDetailViewModel.isAddToFavorite.collectLatest {
                setFavoriteIcon(it)
            }
        }
    }

    private fun observeFavoriteMovie() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            tvDetailViewModel.favoriteTvState.collectLatest { favoriteMovie ->
                setFavoriteIcon(favoriteMovie != null)
            }
        }
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            tvDetailViewModel.tvDetailState.collectLatest {
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

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ivFavorite.setImageResource(com.nadhif.hayazee.baseview.R.drawable.ic_favorite_filled)
        } else {
            binding.ivFavorite.setImageResource(com.nadhif.hayazee.baseview.R.drawable.ic_favorite_outlined)

        }
    }

    private fun setupListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            ivFavorite.setOnClickListener { _ ->
                movie?.let { tvDetailViewModel.addTvToFavorite(it) }
            }
        }
    }
}