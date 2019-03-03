package com.github.jmkim0213.chart

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.jmkim0213.chart.bar.MooChartBarData
import com.github.jmkim0213.chart.bar.MooChartBarDataEntry
import com.github.jmkim0213.chart.bar.MooChartBarDataSet
import com.github.jmkim0213.chart.base.MooChartAxis
import com.github.jmkim0213.chart.line.MooChartLineData
import com.github.jmkim0213.chart.line.MooChartLineDataEntry
import com.github.jmkim0213.chart.line.MooChartLineDataSet
import com.github.jmkim0213.chart.utils.TypedValueUtils
import java.util.*

class MainActivity : AppCompatActivity(), MooChartViewDelegate {
    private val mNumberOfMonth  : Int           = 36
    private var mSelectTextView : TextView?     = null
    private var mChartView      : MooChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSelectTextView = findViewById(R.id.main_activity_TextView_selected)
        mChartView = findViewById(R.id.main_activity_MooChartView)

        initChartView()
        initChartAxis()
        initChartBar()
        initChartLine()

        mChartView?.setBackgroundColor(0xFFFEFEFE.toInt())
        mChartView?.reloadData()
    }

    private fun initChartView() {
        mChartView?.delegate = this
        mChartView?.leftMargin = TypedValueUtils.dpToPixel(this, 35.0f)
        mChartView?.rightMargin = TypedValueUtils.dpToPixel(this, 20.0f)
        mChartView?.bottomMargin = TypedValueUtils.dpToPixel(this, 25.0f)
        mChartView?.axisMargin = TypedValueUtils.dpToPixel(this, 2.0f)
        mChartView?.lineAxisMargin = TypedValueUtils.dpToPixel(this, 3.0f)
        mChartView?.rightAxisMargin = TypedValueUtils.dpToPixel(this, 3.0f)

        mChartView?.highlightIndicatorColor = 0xFF888888.toInt()
        mChartView?.horizontalIndicatorColor = 0xFFEDEDED.toInt()
        mChartView?.highlightIndicatorWidth = TypedValueUtils.dpToPixel(this, 0.5f)
        mChartView?.horizontalIndicatorWidth = TypedValueUtils.dpToPixel(this, 1.0f)
    }

    private fun initChartAxis() {
        val axises = ArrayList<MooChartAxis>()
        for (i in 0..(mNumberOfMonth - 1)) {
            val text = String.format("2019.%02d", i + 1)
            axises.add(MooChartAxis(text, text))
        }

        mChartView?.axises = axises
        mChartView?.axisFont = TypedValueUtils.spToPixel(this, 10.0f)
        mChartView?.axisColor = 0xFF444444.toInt()
        mChartView?.axisDividerColor = 0xFFD3D3D3.toInt()
        mChartView?.axisInterval = mNumberOfMonth / 4
        mChartView?.axisDividerMargin = TypedValueUtils.dpToPixel(this, 21.0f)
    }

    private fun initChartBar() {
        val random = Random()

        val entries1: ArrayList<MooChartBarDataEntry> = ArrayList()

        for (i in 0..(mNumberOfMonth - 1)) {
            val value = 40 + random.nextInt(60)
            val entry = MooChartBarDataEntry(value.toFloat())
            entries1.add(entry)
        }

        val dataSet1 = MooChartBarDataSet(entries1)
        dataSet1.color = 0xFF80D5DC.toInt()

        val entries2: ArrayList<MooChartBarDataEntry> = ArrayList()
        for (i in 0..(mNumberOfMonth - 1)) {
            val value = 40 + random.nextInt(60)
            val entry = MooChartBarDataEntry(value.toFloat())
            entries2.add(entry)
        }

        var dataSet2 = MooChartBarDataSet(entries2)
        dataSet2.color = 0xFFBBDDFF.toInt()


        var data = MooChartBarData(listOf(dataSet1, dataSet2))
        data.maxValue = 150.0f
        data.groupSpace = 0.5f

        mChartView?.barData = data
    }


    private fun initChartLine() {
        val random = Random()
        val entries1: ArrayList<MooChartLineDataEntry> = ArrayList()
        for (i in 0..(this.mNumberOfMonth - 1)) {
            val value = 5000 + random.nextInt(5000)
            val entry = MooChartLineDataEntry(value.toFloat())
            entries1.add(entry)
        }

        var dataSet1 = MooChartLineDataSet(entries1)
        dataSet1.color = 0xFF80DCBC.toInt()
        dataSet1.circleColor = 0xFF80DCBC.toInt()
        dataSet1.holeColor = Color.WHITE
        dataSet1.circleBorderColor = Color.WHITE
        dataSet1.circleRadius = TypedValueUtils.dpToPixel(this, 4.0f)
        dataSet1.holeRadius = TypedValueUtils.dpToPixel(this, 1.0f)
        dataSet1.circleBorder = TypedValueUtils.dpToPixel(this, 1.0f)
        dataSet1.width = TypedValueUtils.dpToPixel(this, 1.0f)

        val entries2: ArrayList<MooChartLineDataEntry> = ArrayList()
        for (i in 0..(this.mNumberOfMonth - 1)) {
            val value = 8000 + random.nextInt(5000)
            val entry = MooChartLineDataEntry(value.toFloat())
            entries2.add(entry)
        }

        var dataSet2 = MooChartLineDataSet(entries2)
        dataSet2.color = 0xFF6CAAE7.toInt()
        dataSet2.circleColor = 0xFF6CAAE7.toInt()
        dataSet2.holeColor = Color.WHITE
        dataSet2.circleBorderColor = Color.WHITE
        dataSet2.circleRadius = TypedValueUtils.dpToPixel(this, 4.0f)
        dataSet2.holeRadius = TypedValueUtils.dpToPixel(this, 1.0f)
        dataSet2.circleBorder = TypedValueUtils.dpToPixel(this, 1.0f)
        dataSet2.width = TypedValueUtils.dpToPixel(this, 1.0f)

        val data = MooChartLineData(listOf(dataSet1, dataSet2))
        data.maxValue = 20000.0f

        mChartView?.lineData = data
        mChartView?.lineAxisFont = TypedValueUtils.spToPixel(this, 10.0f)
        mChartView?.lineAxisColor = 0xFF444444.toInt()
        mChartView?.lineAxisInterval = (data.maxValue / 5).toInt()
    }

    override fun chartViewLineAxisText(chartView: MooChartView, value: Float, index: Int): String {
        return String.format("%.1f억", (value / 10000))
    }

    override fun chartViewRightAxisText(chartView: MooChartView, value: Float, index: Int): String {
        return if (index == 0) {
            "건"
        } else {
            ""
        }
    }

    override fun chartViewAxisText(chartView: MooChartView, axis: MooChartAxis): String {
        return axis.text
    }

    override fun chartViewDidSelected(chartView: MooChartView, axis: MooChartAxis?) {
        val data = axis?.data as? String
        mSelectTextView?.text = "select: $data"
    }

}
