package dabangapp.com.chart

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import dabangapp.com.chart.bar.ST3ChartBarData
import dabangapp.com.chart.bar.ST3ChartBarDataEntry
import dabangapp.com.chart.bar.ST3ChartBarDataSet
import dabangapp.com.chart.base.ST3ChartAxis
import dabangapp.com.chart.line.ST3ChartLineData
import dabangapp.com.chart.line.ST3ChartLineDataEntry
import dabangapp.com.chart.line.ST3ChartLineDataSet
import java.util.*

class MainActivity : AppCompatActivity(), ST3ChartViewDelegate {
    private val mNumberOfMonth  : Int           = 12
    private var mSelectTextView : TextView?     = null
    private var mChartView      : ST3ChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSelectTextView = findViewById(R.id.main_activity_TextView_selected)
        mChartView = findViewById(R.id.main_activity_ST3ChartView)

        initChartView()
        initChartAxis()
        initChartBar()
        initChartLine()

        mChartView?.setBackgroundColor(0xFFFEFEFE.toInt())
        mChartView?.delegate = this
        mChartView?.reloadData()
    }

    private fun initChartView() {
//        chartView?.delegate = self
        mChartView?.leftMargin = dpToPixel(30.0f)
        mChartView?.rightMargin = dpToPixel(15.0f)
        mChartView?.bottomMargin = dpToPixel(25.0f)
        mChartView?.axisMargin = dpToPixel(3.0f)

        mChartView?.highlightIndicatorColor = 0xFF888888.toInt()
        mChartView?.horizontalIndicatorColor = 0xFFEDEDED.toInt()
        mChartView?.highlightIndicatorWidth = dpToPixel(0.5f)
        mChartView?.horizontalIndicatorWidth = dpToPixel(1.0f)
    }

    fun initChartAxis() {
        val axises = ArrayList<ST3ChartAxis>()
        for (i in 0..(mNumberOfMonth - 1)) {
            val text = String.format("2019.%02d", i + 1)
            axises.add(ST3ChartAxis(text, text))
        }

        mChartView?.axises = axises
        mChartView?.axisFont = spToPixel(9.0f)
        mChartView?.axisColor = 0xFF444444.toInt()
        mChartView?.axisDividerColor = 0xFFDDDDDD.toInt()
        mChartView?.axisInterval = mNumberOfMonth / 4
    }

    private fun initChartBar() {
        val random = Random()

        val entries1: ArrayList<ST3ChartBarDataEntry> = ArrayList()

        for (i in 0..(mNumberOfMonth - 1)) {
            val value = 40 + random.nextInt(60)
            val entry = ST3ChartBarDataEntry(value.toFloat())
            entries1.add(entry)
        }

        val dataSet1 = ST3ChartBarDataSet(entries1)
        dataSet1.color = 0xFF80D5DC.toInt()

        val entries2: ArrayList<ST3ChartBarDataEntry> = ArrayList()
        for (i in 0..(mNumberOfMonth - 1)) {
            val value = 40 + random.nextInt(60)
            val entry = ST3ChartBarDataEntry(value.toFloat())
            entries2.add(entry)
        }

        var dataSet2 = ST3ChartBarDataSet(entries2)
        dataSet2.color = 0xFFBBDDFF.toInt()


        var data = ST3ChartBarData(listOf(dataSet1, dataSet2))
        data.maxValue = 150.0f
        data.groupSpace = 0.5f

        mChartView?.barData = data
    }


    private fun initChartLine() {
        val random = Random()
        val entries1: ArrayList<ST3ChartLineDataEntry> = ArrayList()
        for (i in 0..(this.mNumberOfMonth - 1)) {
            val value = 5000 + random.nextInt(5000)
            val entry = ST3ChartLineDataEntry(value.toFloat())
            entries1.add(entry)
        }

        var dataSet1 = ST3ChartLineDataSet(entries1)
        dataSet1.color = 0xFF76D6B5.toInt()
        dataSet1.circleColor = 0xFF76D6B5.toInt()
        dataSet1.holeColor = Color.WHITE
        dataSet1.circleBorderColor = Color.WHITE
        dataSet1.circleRadius = dpToPixel(5.0f)
        dataSet1.holeRadius = dpToPixel(1.0f)
        dataSet1.circleBorder = dpToPixel(1.0f)
        dataSet1.width = dpToPixel(1.0f)

        val entries2: ArrayList<ST3ChartLineDataEntry> = ArrayList()
        for (i in 0..(this.mNumberOfMonth - 1)) {
            val value = 8000 + random.nextInt(5000)
            val entry = ST3ChartLineDataEntry(value.toFloat())
            entries2.add(entry)
        }

        var dataSet2 = ST3ChartLineDataSet(entries2)

        dataSet2.color = 0XFF7BB5F0.toInt()
        dataSet2.circleColor = 0XFF7BB5F0.toInt()
        dataSet2.holeColor = Color.WHITE
        dataSet2.circleBorderColor = Color.WHITE
        dataSet2.circleRadius = dpToPixel(5.0f)
        dataSet2.holeRadius = dpToPixel(1.0f)
        dataSet2.circleBorder = dpToPixel(1.0f)
        dataSet2.width = dpToPixel(1.0f)



        val data = ST3ChartLineData(listOf(dataSet1, dataSet2))
        data.maxValue = 20000.0f

        mChartView?.lineData = data
        mChartView?.lineAxisFont = spToPixel(8.0f)
        mChartView?.lineAxisColor = 0xFF444444.toInt()
        mChartView?.lineAxisInterval = (data.maxValue / 5).toInt()
    }

    private fun dpToPixel(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    private fun spToPixel(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
    }

    override fun chartViewLineAxisText(chartView: ST3ChartView, value: Float): String {
        return String.format("%.1f억", (value / 10000))
    }

    override fun chartViewAxisText(chartView: ST3ChartView, axis: ST3ChartAxis): String {
        return axis.text
    }

    override fun chartViewDidSelected(chartView: ST3ChartView, axis: ST3ChartAxis?) {
        val data = axis?.data as? String
        mSelectTextView?.text = "select: $data"
    }

}
