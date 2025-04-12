package com.project.rawg.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.core.data.Resource
import com.project.rawg.R
import com.project.rawg.databinding.FragmentCreatorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatorFragment : Fragment() {

    private var _binding: FragmentCreatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreatorViewModel by viewModel()
    private var viewOptions = "grid"
    private lateinit var creatorAdapter: CreatorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            // Initialize the CreatorAdapter and set click listener
            creatorAdapter = CreatorAdapter()

            binding.apply {
                // Observe creator data and update adapter list
                viewModel.creator.observe(viewLifecycleOwner) { creator ->
                    if (creator != null) {
                        when (creator) {
                            is Resource.Loading -> progressBar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                progressBar.visibility = View.GONE
                                creatorAdapter.submitList(creator.data)
                            }
                            is Resource.Error -> {
                                progressBar.visibility = View.GONE
                                viewError.root.visibility = View.VISIBLE
                                viewError.tvError.text =
                                    creator.message ?: getString(R.string.something_wrong)
                            }
                        }
                    }
                }

                // Set initial layout manager (Linear for list)
                rvCreator.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                rvCreator.setHasFixedSize(true)
                rvCreator.adapter = creatorAdapter

                // Set initial fab icon and click listener for toggling view mode
                setFabIcon()
                fab.setOnClickListener {
                    // Toggle view mode
                    viewOptions = if (viewOptions == "list") "grid" else "list"
                    setFabIcon()
                    updateLayoutManager()              // Update layout manager based on mode
                    creatorAdapter.setViewMode(viewOptions)  // Tell adapter which layout to inflate
                }
            }
        }
    }

    private fun setFabIcon() {
        binding.apply {
            if (viewOptions == "list") {
                fab.setImageResource(R.drawable.ic_grid)
            } else {
                fab.setImageResource(R.drawable.ic_list)
            }
        }
    }

    // Use a GridLayoutManager when in grid mode; otherwise use LinearLayoutManager
    private fun updateLayoutManager() {
        binding.rvCreator.layoutManager = if (viewOptions == "list") {
            LinearLayoutManager(context)
        } else {
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}