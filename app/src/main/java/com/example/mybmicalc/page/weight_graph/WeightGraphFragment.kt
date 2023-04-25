package com.example.mybmicalc.page.weight_graph

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mybmicalc.R
import com.example.mybmicalc.databinding.WeightGraphFragmentBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WeightGraphFragment : Fragment(R.layout.weight_graph_fragment) {
    private val viewModel: WeightGraphViewModel by viewModels()

    private var _binding: WeightGraphFragmentBinding? = null
    private val binding: WeightGraphFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WeightGraphFragmentBinding.bind(view)

        viewModel.myBodyList.observe(viewLifecycleOwner) { list ->

            val sorted = list.sortedBy { it.date }
            val timestamps = sorted.map { it.date }.toMutableList()
            var entryList = mutableListOf<Entry>()//1本目の線
            sorted.forEachIndexed { index, myBody ->
                entryList.add(
                    Entry(index.toFloat(), myBody.weight.toFloat())
                )
            }
            val lineDataSets = mutableListOf<ILineDataSet>()
            val lineDataSet = LineDataSet(entryList, "体重")
            lineDataSet.color = Color.BLUE
            lineDataSets.add(lineDataSet)
            val lineData = LineData(lineDataSets)
            val lineChart = binding.lineChart
            lineChart.data = lineData

            val xAxisFormatter = object : ValueFormatter() {
                private var simpleDateFormat: SimpleDateFormat =
                    SimpleDateFormat("M/d", Locale.getDefault())

                override fun getFormattedValue(value: Float): String {
                    val timestampMills = timestamps[value.toInt()]
                    val date = Date(timestampMills)
                    return simpleDateFormat.format(date)
                }
            }

            lineChart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = xAxisFormatter
                setDrawGridLines(false)
                isEnabled = true
                textColor = Color.BLACK
            }
            lineChart.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}