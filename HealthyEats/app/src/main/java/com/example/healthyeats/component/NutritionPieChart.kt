package com.example.healthyeats.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun NutritionPieChart(nutritionData: List<Pair<String, Float>>) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                // chart setting
                description.isEnabled = false
                setUsePercentValues(true)
                setExtraOffsets(5f, 10f, 5f, 5f)
                dragDecelerationFrictionCoef = 0.95f
                isDrawHoleEnabled = true
                setHoleColor(android.graphics.Color.TRANSPARENT)
                holeRadius = 58f
                transparentCircleRadius = 61f
                setDrawCenterText(true)
                rotationAngle = 0f
                isRotationEnabled = true
                isHighlightPerTapEnabled = true
                setEntryLabelColor(android.graphics.Color.WHITE)
                setEntryLabelTextSize(12f)

                // set data
                val entries = nutritionData.map { PieEntry(it.second, it.first) }
                val dataSet = PieDataSet(entries, "")
                dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
                dataSet.valueTextColor = android.graphics.Color.WHITE
                dataSet.valueTextSize = 16f
                val pieData = PieData(dataSet)
                data = pieData
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}