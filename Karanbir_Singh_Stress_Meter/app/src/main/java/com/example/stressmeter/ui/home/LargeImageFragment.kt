package com.example.stressmeter.ui.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.stressmeter.R
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class LargeImageFragment(private val imageResId: Int, private val imageScore: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_large_image, container, false)

        // Display the selected image in full size
        val imageView: ImageView = view.findViewById(R.id.imageViewLarge)
        imageView.setImageResource(imageResId)

        // Cancel button to close the fragment without saving
        val cancelButton: Button = view.findViewById(R.id.buttonCancel)
        cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Submit button to save the score and close the app
        val submitButton: Button = view.findViewById(R.id.buttonSubmit)
        submitButton.setOnClickListener {
            saveScore(imageScore)
            requireActivity().finish()  // Close the app
        }

        return view
    }

    // Save the selected image's score with timestamp
    private fun saveScore(score: Int) {
        val timestamp = System.currentTimeMillis() / 1000
        val filename = "stress_timestamp.csv"
        val fileOutputStream: FileOutputStream = requireActivity().openFileOutput(filename, Context.MODE_APPEND)
        val outputStreamWriter = OutputStreamWriter(fileOutputStream)
        outputStreamWriter.write("$timestamp,$score\n")
        outputStreamWriter.close()
    }
}
