package com.project.favorite

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.favorite.databinding.ActivityFavoriteBinding
import com.project.rawg.R
import com.project.rawg.adapter.GamesAdapter
import com.project.rawg.detail.DetailGamesActivity
import com.project.rawg.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteViewModelModule)
        loadFavoriteGames()
        binding.apply {
            bottomNavView.selectedItemId = R.id.navigation_favorite
            bottomNavView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_favorite -> {
                        // For favorite, install dynamic module and launch FavoriteActivity.
                        true
                    }
                    else -> {
                        val intent = Intent(this@FavoriteActivity, HomeActivity::class.java)
                        intent.putExtra(HomeActivity.EXTRA_MENU, item.itemId)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                        true
                    }
                }
            }
        }
    }

    private fun loadFavoriteGames() {
        binding.apply {
            val gamesAdapter = GamesAdapter()
            gamesAdapter.onItemClick = { selectedData ->
                val intent = Intent(this@FavoriteActivity, DetailGamesActivity::class.java)
                intent.putExtra(DetailGamesActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            viewModel.favoriteGames.observe(this@FavoriteActivity) { games ->
                gamesAdapter.submitList(games)
                    if (games.isNotEmpty()) {
                        binding.tvEmpty.visibility = View.GONE
                        binding.lottieAnimationView.visibility = View.GONE
                    } else {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.lottieAnimationView.visibility = View.VISIBLE
                    }
            }

            with(binding.rvGames) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gamesAdapter
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK)
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}