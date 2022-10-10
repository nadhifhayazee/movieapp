package com.nadhif.hayazee.favorite

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private lateinit var tabAdapter: TabAdapter
    private val tabsTitle = listOf("Movie", "Tv")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()

    }

    private fun setupTabLayout() {
        tabAdapter = TabAdapter(this)
        tabAdapter.addFragment(FavoriteMovieFragment())
        tabAdapter.addFragment(FavoriteTvFragment())

        binding.apply {
            viewPagerLayout.adapter = tabAdapter
            TabLayoutMediator(
                tabLayout, viewPagerLayout
            ) { tab, position ->
                tab.text = tabsTitle[position]
            }.attach()
        }
    }
}