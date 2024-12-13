package com.example.yuktanam.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.yuktanam.R
import com.example.yuktanam.databinding.FragmentHomeBinding
import com.example.yuktanam.logic.database.addplant.PlantDatabase
import com.example.yuktanam.logic.database.addplant.PlantEntity
import com.example.yuktanam.logic.home.recyclerview.Plant
import com.example.yuktanam.logic.home.recyclerview.PlantAdapter
import com.example.yuktanam.logic.slider.ImageAdapter
import com.example.yuktanam.logic.slider.ImageItem
import com.example.yuktanam.ui.chatbot.ChatbotActivity
import com.example.yuktanam.ui.plants.addplants.AddPlantActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewpager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private lateinit var adapter: PlantAdapter
    private lateinit var database: PlantDatabase

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }

    // Handler dan Runnable untuk animasi otomatis
    private val slideHandler = Handler(Looper.getMainLooper())
    private lateinit var slideRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = PlantDatabase.getDatabase(requireContext()) // Inisialisasi database

        setupViewPager()
        setupRecyclerView()
        buttonAddPlants()
        chatbotAI()

        return binding.root
    }

    private fun chatbotAI() {
        val fabChatbot = binding.fabChatbot
        fabChatbot.setOnClickListener {
            val intent = Intent(requireContext(), ChatbotActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buttonAddPlants() {
        val btnAddPlant = binding.addPlants
        btnAddPlant.setOnClickListener {
            val intent = Intent(requireContext(), AddPlantActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewPager() {
        viewpager2 = binding.viewPager

        val imageList = arrayListOf(
            ImageItem(UUID.randomUUID().toString(), "file:///android_asset/images/slider2.png"),
            ImageItem(UUID.randomUUID().toString(), "file:///android_asset/images/slider2.png"),
            ImageItem(UUID.randomUUID().toString(), "file:///android_asset/images/slider2.png"),
            ImageItem(UUID.randomUUID().toString(), "file:///android_asset/images/slider2.png")
        )

        val imageAdapter = ImageAdapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        setupDotsIndicator(imageList.size)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDotsIndicator(position)
                super.onPageSelected(position)
            }
        }
        viewpager2.registerOnPageChangeCallback(pageChangeListener)

        slideRunnable = Runnable {
            val nextPage = (viewpager2.currentItem + 1) % imageList.size
            viewpager2.setCurrentItem(nextPage, true)
            slideHandler.postDelayed(slideRunnable, 3000)
        }
        slideHandler.postDelayed(slideRunnable, 3000)
    }

    private fun setupDotsIndicator(size: Int) {
        val slideDotLL = binding.slideDot
        val dotsImage = Array(size) { ImageView(requireContext()) }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            slideDotLL.addView(it, params)
        }
        dotsImage[0].setImageResource(R.drawable.active_dot)
        binding.slideDot.tag = dotsImage
    }

    private fun updateDotsIndicator(position: Int) {
        val dotsImage = binding.slideDot.tag as Array<ImageView>
        dotsImage.forEachIndexed { index, imageView ->
            imageView.setImageResource(
                if (position == index) R.drawable.active_dot else R.drawable.non_active_dot
            )
        }
    }

    private fun setupRecyclerView() {
        val plantList = listOf(
            Plant(UUID.randomUUID().toString(), "Leon", "Monstera", R.drawable.sukulen, false),
            Plant(UUID.randomUUID().toString(), "Frisly", "Africa", R.drawable.tropical, false),
            Plant(UUID.randomUUID().toString(), "Gojo", "Monstera", R.drawable.sukulen, false),
            Plant(UUID.randomUUID().toString(), "Deona", "Africa", R.drawable.tropical, false)
        )

        adapter = PlantAdapter(plantList) { plant, position ->
            plant.isFavorite = !plant.isFavorite
            adapter.notifyItemChanged(position)

            CoroutineScope(Dispatchers.IO).launch {
                val plantDao = database.plantDao()
                if (plant.isFavorite) {
                    plantDao.insertFavorite(
                        PlantEntity(
                            id = plant.id,
                            name = plant.name,
                            origin = plant.origin,
                            imageResource = plant.imageResource,
                            isFavorite = plant.isFavorite
                        )
                    )
                } else {
                    plantDao.deleteFavorite(
                        PlantEntity(
                            id = plant.id,
                            name = plant.name,
                            origin = plant.origin,
                            imageResource = plant.imageResource,
                            isFavorite = plant.isFavorite
                        )
                    )
                }
            }

            Toast.makeText(
                requireContext(),
                if (plant.isFavorite) "${plant.name} added to favorites!" else "${plant.name} removed from favorites!",
                Toast.LENGTH_SHORT
            ).show()
        }

        adapter.database = database // Sambungkan database ke adapter

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
        slideHandler.removeCallbacks(slideRunnable)
    }
}