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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.yuktanam.R
import com.example.yuktanam.databinding.FragmentHomeBinding
import com.example.yuktanam.logic.home.recyclerview.Plant
import com.example.yuktanam.logic.home.recyclerview.PlantAdapter
import com.example.yuktanam.logic.slider.ImageAdapter
import com.example.yuktanam.logic.slider.ImageItem
import com.example.yuktanam.ui.addplants.AddPlantActivity
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewpager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private lateinit var adapter: PlantAdapter

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



        setupViewPager()
        setupRecyclerView()
        ButtonAddPlants()

        return binding.root
    }

    // Function Add Plans
    private fun ButtonAddPlants() {
        val btnAddPlant = binding.addPlants
        btnAddPlant.setOnClickListener {
            val intent = Intent(requireContext(), AddPlantActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setupViewPager() {
        viewpager2 = binding.viewPager

        // Daftar gambar untuk ViewPager
        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/866/500/500.jpg?hmac=FOptChXpmOmfR5SpiL2pp74Yadf1T_bRhBF1wJZa9hg"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/270/500/500.jpg?hmac=MK7XNrBrZ73QsthvGaAkiNoTl65ZDlUhEO-6fnd-ZnY"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/866/500/500.jpg?hmac=FOptChXpmOmfR5SpiL2pp74Yadf1T_bRhBF1wJZa9hg"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://fastly.picsum.photos/id/270/500/500.jpg?hmac=MK7XNrBrZ73QsthvGaAkiNoTl65ZDlUhEO-6fnd-ZnY"
            )
        )

        val imageAdapter = ImageAdapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        setupDotsIndicator(imageList.size)

        // Listener untuk mengganti indikator dot
        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDotsIndicator(position)
                super.onPageSelected(position)
            }
        }
        viewpager2.registerOnPageChangeCallback(pageChangeListener)

        // Buat Runnable untuk animasi otomatis
        slideRunnable = Runnable {
            val nextPage = (viewpager2.currentItem + 1) % imageList.size
            viewpager2.setCurrentItem(nextPage, true)
            slideHandler.postDelayed(slideRunnable, 3000) // 3 detik
        }

        // Mulai animasi otomatis
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
        // Contoh data tanaman
        val plantList = listOf(
            Plant("Leon", "Monstera", R.drawable.sukulen, false),
            Plant("Aloe Vera", "Africa", R.drawable.tropical, false),
            Plant("Leon", "Monstera", R.drawable.sukulen, false),
        )

        // Inisialisasi RecyclerView
        adapter = PlantAdapter(plantList)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // Unregister listener dan hentikan handler untuk mencegah kebocoran memori
        viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
        slideHandler.removeCallbacks(slideRunnable)
    }
}