package com.project.rawg.games

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.core.data.Resource
import com.project.rawg.R
import com.project.rawg.adapter.GamesAdapter
import com.project.rawg.databinding.FragmentGamesBinding
import com.project.rawg.detail.DetailGamesActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!
    private val gamesViewModel: GamesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.apply {
                val currentUser = Firebase.auth.currentUser
                Glide
                    .with(requireActivity())
                    .load(currentUser?.photoUrl)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .circleCrop()
                    .into(ivImage)

                tvName.text = "${getString(R.string.hi)}, ${currentUser?.email}!"

                val gamesAdapter = GamesAdapter()
                gamesAdapter.onItemClick = { selectedData ->
                    val intent = Intent(activity, DetailGamesActivity::class.java)
                    intent.putExtra(DetailGamesActivity.EXTRA_DATA, selectedData)
                    startActivity(intent)
                }

                etSearch.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        // Directly update the MutableStateFlow on the main thread
                        lifecycleScope.launch {
                            gamesViewModel.queryChannel.value = s?.toString() ?: ""
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                gamesViewModel.searchResult.observe(viewLifecycleOwner, Observer { games ->
                    if (games != null) {
                        when (games) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                stopShimmer()
                                Glide.with(requireActivity())
                                    .load(games.data?.get(0)?.backgroundImage)
                                    .into(binding.imageView2)
                                gamesAdapter.submitList(games.data)
                            }
                            is Resource.Error -> {
                                stopShimmer()
                                binding.viewError.root.visibility = View.VISIBLE
                                binding.viewError.tvError.text =
                                    games.message ?: getString(R.string.something_wrong)
                            }
                        }
                    }
                })

                with(binding.rvGames) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = gamesAdapter
                }

                btnSearch.setOnClickListener {
                    headerWrapper.visibility = View.GONE
                    searchWrapper.visibility = View.VISIBLE
                }

                btnCloseSearch.setOnClickListener {
                    headerWrapper.visibility = View.VISIBLE
                    searchWrapper.visibility = View.GONE
                    lifecycleScope.launch {
                        gamesViewModel.queryChannel.value = ""
                    }
                }
            }
        }
    }

    private fun stopShimmer() {
        binding.apply {
            shimmerGames.stopShimmer()
            shimmerGames.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}