package com.example.stressmeter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stressmeter.R
import com.example.stressmeter.databinding.FragmentHomeBinding
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val totalImages = listOf(
        R.drawable.psm_bar, R.drawable.psm_puppy, R.drawable.psm_puppy3,
        R.drawable.psm_cat, R.drawable.psm_alarm_clock, R.drawable.psm_angry_face,
        R.drawable.psm_baby_sleeping, R.drawable.psm_anxious, R.drawable.psm_bird3,
        R.drawable.fish_normal017, R.drawable.psm_alarm_clock2, R.drawable.psm_barbed_wire2,
        R.drawable.psm_beach3, R.drawable.psm_blue_drop, R.drawable.psm_clutter, R.drawable.psm_clutter3,
        R.drawable.psm_dog_sleeping, R.drawable.psm_exam4, R.drawable.psm_gambling4, R.drawable.psm_headache,
        R.drawable.psm_headache2, R.drawable.psm_hiking3, R.drawable.psm_kettle, R.drawable.psm_lake3,
        R.drawable.psm_lawn_chairs3, R.drawable.psm_lonely, R.drawable.psm_lonely2, R.drawable.psm_mountains11,
        R.drawable.psm_neutral_child, R.drawable.psm_neutral_person2, R.drawable.psm_peaceful_person,
        R.drawable.psm_reading_in_bed2, R.drawable.psm_running3, R.drawable.psm_running4, R.drawable.psm_sticky_notes2,
        R.drawable.psm_stressed_cat, R.drawable.psm_stressed_person, R.drawable.psm_stressed_person12,
        R.drawable.psm_stressed_person3, R.drawable.psm_talking_on_phone2, R.drawable.psm_wine3,
        R.drawable.psm_work4, R.drawable.psm_yoga4, R.drawable.psm_stressed_person8, R.drawable.psm_stressed_person7
    )

    private lateinit var randomImages: List<Int>  // List of current random images
    private lateinit var imageScores: Map<Int, Int>  // Map of image IDs to random scores

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observe the Data for the instruction text
        homeViewModel.text.observe(viewLifecycleOwner) {
            binding.tvInstruction.text = it
        }

        // Check if random images and scores are already set in ViewModel
        if (homeViewModel.randomImages.value != null && homeViewModel.imageScores.value != null) {
            // Use the images and scores stored in the ViewModel
            randomImages = homeViewModel.randomImages.value!!
            imageScores = homeViewModel.imageScores.value!!
            displayImages(randomImages)
        } else {
            // If no saved state in ViewModel, generate random images and scores
            setRandomImages()
        }

        // Setting the "More Images" button click listener to display random images when clicked
        binding.btnMoreImages.setOnClickListener {
            setRandomImages()
        }

        return root
    }

    private fun setRandomImages() {
        randomImages = totalImages.shuffled().take(16)  // Get 16 random images
        imageScores = randomImages.associateWith { Random.nextInt(0, 16) }  // Assign random scores

        // Store the images and scores in the ViewModel
        homeViewModel.setImagesAndScores(randomImages, imageScores)

        displayImages(randomImages)
    }

    private fun displayImages(images: List<Int>) {
        val gridViewImages: GridView = binding.gridViewImages
        val imageAdapter = ImageAdapter(requireContext(), images)
        gridViewImages.adapter = imageAdapter

        gridViewImages.setOnItemClickListener { parent, view, position, id ->
            val selectedImage = randomImages[position]
            val selectedScore = imageScores[selectedImage] ?: 0
            // Open LargeImageFragment to display the selected image
            val fragment = LargeImageFragment(selectedImage, selectedScore)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
