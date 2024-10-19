package com.example.stressmeter.ui.results

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stressmeter.R
import com.example.stressmeter.databinding.FragmentResultsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private val resultsList = mutableListOf<Pair<Long, Int>>()  // List to hold timestamp and stress score

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Read data from CSV file
        readCsvFile()

        // Check if data is available
        if (resultsList.isEmpty()) {
            // Handle empty CSV file gracefully
            handleEmptyData()
        } else {
            // Generate graph from the data
            generateLineChart(binding.lineChart)

            // Populate the table with the data
            populateTable()
        }

        return root
    }

    private fun readCsvFile() {
        val filename = "stress_timestamp.csv"
        val fileInputStream: FileInputStream? = try {
            requireContext().openFileInput(filename)
        } catch (e: Exception) {
            Log.e("FileError", "Error opening file: $filename. File might not exist or is inaccessible.", e)
            null
        }

        if (fileInputStream != null) {
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            reader.forEachLine { line ->
                val tokens = line.split(",")
                if (tokens.size == 2) {
                    val timestamp = tokens[0].toLong()
                    val score = tokens[1].toInt()
                    resultsList.add(Pair(timestamp, score))
                }
            }
            reader.close()
        }
    }

    private fun handleEmptyData() {
        // Set message if no data is available
        binding.tvGraphTitle.text = "No data available to display the graph."
        binding.tvSummaryTitle.text = "No data available for summary."
    }

    private fun generateLineChart(lineChart: LineChart) {
        val entries = ArrayList<Entry>()

        // Prepare data for the graph
        resultsList.forEachIndexed { index, (_, score) ->
            entries.add(Entry(index.toFloat(), score.toFloat()))  // X-axis is the index, Y-axis is the score
        }

        // Create a dataset and give it a type
        val dataSet = LineDataSet(entries, "Stress Level")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(true)
        dataSet.circleRadius = 4f

        // Create LineData from dataset
        val lineData = LineData(dataSet)

        // Set the data and other chart properties
        lineChart.data = lineData
        lineChart.invalidate()  // Refresh chart

        // Customize X-Axis
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
    }

    private fun populateTable() {
        resultsList.forEach { (timestamp, score) ->
            val row = TableRow(context)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            // Format timestamp
            val formattedTime = formatTimestamp(timestamp)

            // Create the TextView for the timestamp column
            val timeTextView = TextView(context).apply {
                text = formattedTime
                setPadding(16, 16, 16, 16)
            }

            // Create the TextView for the score column
            val scoreTextView = TextView(context).apply {
                text = score.toString()
                setPadding(16, 16, 16, 16)
            }

            // Add the TextViews to the row
            row.addView(timeTextView)
            row.addView(scoreTextView)

            // Add the row to the table layout
            binding.tableLayout.addView(row)
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        val date = Date(timestamp * 1000)  // Convert from seconds to milliseconds
        return sdf.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
