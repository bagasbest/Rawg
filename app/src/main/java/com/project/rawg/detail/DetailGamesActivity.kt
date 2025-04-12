package com.project.rawg.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.project.core.data.Resource
import com.project.core.domain.model.Games
import com.project.rawg.R
import com.project.rawg.databinding.ActivityDetailGamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailGamesActivity : AppCompatActivity() {

    private var _binding: ActivityDetailGamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailGamesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailGames = getParcelableExtra(intent, EXTRA_DATA, Games::class.java)
        showDetailGames(detailGames)
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailGames(detailGames: Games?) {
        detailGames?.let {
            binding.apply {
                viewModel.getDetailGames(detailGames.gamesId).observe(this@DetailGamesActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                binding.progressBar.visibility = View.GONE
                                tvWebsite.text = result.data?.get(0)?.website
                                tvWebsite.paintFlags = tvWebsite.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                                tvWebsite.setOnClickListener {
                                    val intent = Intent(Intent.ACTION_VIEW, result.data?.get(0)?.website?.toUri())
                                    startActivity(intent)
                                }
                                setDescription(result.data?.get(0)?.description)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                tvWebsite.text = "-"
                                tvDescription.visibility = View.GONE
                            }
                        }
                    }
                }

                Glide.with(this@DetailGamesActivity)
                    .load(detailGames.backgroundImage)
                    .placeholder(R.drawable.ic_refresh)
                    .error(R.drawable.ic_broken_image)
                    .into(ivImage)

                tvName.text = detailGames.name
                tvCategory.text = detailGames.genres
                tvRatingCount.text = "${getString(R.string.from)} ${detailGames.ratingsCount} ${getString(R.string.users)}"
                ratingBar.rating = detailGames.rating.toFloat()
                tvEsrbRating.text = detailGames.esrbRating
                tvRelease.text = detailGames.released
                tvTags.text = detailGames.tags

                // Inflate a simple view for each URL (e.g., an ImageView)
                val imageList = detailGames.shortScreenshots.split(", ")
                imageList.forEach { url ->
                    // Create an ImageView programmatically
                    val imageView = ImageView(this@DetailGamesActivity).apply {
                        layoutParams = LinearLayout.LayoutParams(600, 400).apply {
                            marginEnd = 16
                        }
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }

                    // Load the image using your preferred image loading library (e.g., Glide)
                    // Load the image with rounded corners
                    Glide.with(this@DetailGamesActivity)
                        .load(url)
                        .into(imageView)

                    // Add the ImageView to the LinearLayout
                    linearLayoutUrls.addView(imageView)
                }

                var statusFavorite = false
                viewModel.checkFavoriteGames(detailGames.gamesId).observe(this@DetailGamesActivity) {
                    statusFavorite = it
                    setStatusFavorite(it)
                }

                fab.setOnClickListener {
                    statusFavorite = !statusFavorite
                    viewModel.setFavoriteGames(detailGames, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }
                btnBack.setOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun setDescription(description: String?) {
        try {
            val pre = """
                        <html>
                        <head>
                          <style type="text/css">
                            @font-face {
                              font-family: MyFont;
                              src: url("file:///android_asset/fonts/Varela-Round-Regular.ttf");
                            }
                             body {
                              font-family: MyFont;
                              margin: 0;
                              padding: 0 1px;
                              line-height: 1.6;
                              color: #000000; /* Default for light mode */
                              background-color: #FFFFFF;
                            }
                            @media (prefers-color-scheme: dark) {
                              body {
                                color: #71717d; /* Text color for dark mode */
                                background-color: #272831; /* Background color for dark mode */
                              }
                            }
                            img {
                              width: 100%;
                              height: auto;
                              object-fit: cover;
                              object-position: center;
                              display: block;
                              margin-left: auto;
                              margin-right: auto;
                            }
                          </style>
                        </head>
                        <body>
                    """.trimIndent()

            val post = "</body></html>"
            val htmlData = description?.replace("<br>", "", ignoreCase = true)
            val myHtmlString = pre + htmlData + post
            binding.tvDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "utf-8", null)
        } catch (_: Exception){
            binding.tvDescription.visibility = View.GONE
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
            binding.fab.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unfavorite))
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}