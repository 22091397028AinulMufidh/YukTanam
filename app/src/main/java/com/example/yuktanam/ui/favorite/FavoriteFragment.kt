package com.example.yuktanam.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yuktanam.databinding.FragmentFavoriteBinding
import com.example.yuktanam.logic.database.addplant.PlantDatabase
import com.example.yuktanam.logic.database.addplant.PlantEntity
import com.example.yuktanam.logic.home.recyclerview.Plant
import com.example.yuktanam.logic.home.recyclerview.PlantAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadFavorites()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = PlantAdapter(emptyList()) { plant, _ ->
            // Hapus favorit saat tombol di-klik
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val plantDao = PlantDatabase.getDatabase(requireContext()).plantDao()
                plantDao.deleteFavorite(
                    PlantEntity(
                        id = plant.id,
                        name = plant.name,
                        origin = plant.origin,
                        imageResource = plant.imageResource,
                        isFavorite = true
                    )
                )
                loadFavorites() // Refresh daftar favorit setelah penghapusan
            }
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun loadFavorites() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val favorites = PlantDatabase.getDatabase(requireContext()).plantDao().getAllFavorites()
            withContext(Dispatchers.Main) {
                if (favorites.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.emptyView.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.updateData(favorites.map { entity ->
                        Plant(
                            id = entity.id,
                            name = entity.name,
                            origin = entity.origin,
                            imageResource = entity.imageResource,
                            isFavorite = entity.isFavorite
                        )
                    })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}