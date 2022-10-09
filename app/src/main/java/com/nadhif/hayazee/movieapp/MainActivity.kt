package com.nadhif.hayazee.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.makeStatusBarTransparent
import com.nadhif.hayazee.common.extension.resetStatusBarColor
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.nadhif.hayazee.movie.R.id.movie_fragment, com.nadhif.hayazee.tv.R.id.tv_fragment,
                com.nadhif.hayazee.favorite.R.id.favorite_fragment -> {
                    resetStatusBarColor()
                    binding.bottomNavView.visible()
                }
                else -> {
                    makeStatusBarTransparent()
                    binding.bottomNavView.gone()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
