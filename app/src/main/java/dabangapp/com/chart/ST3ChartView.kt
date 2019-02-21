package dabangapp.com.chart

import android.content.Context
import android.graphics.*
import android.util.*
import android.view.MotionEvent
import android.view.View
import dabangapp.com.chart.bar.ST3ChartBarData
import dabangapp.com.chart.base.ST3ChartAxis
import dabangapp.com.chart.line.ST3ChartLineData
import dabangapp.com.chart.utils.DBLog

interface ST3ChartViewDelegate {
    fun chartViewLineAxisText(chartView: ST3ChartView, value: Float): String
    fun chartViewAxisText(chartView: ST3ChartView, axis: ST3ChartAxis): String
    fun chartViewDidSelected(chartView: ST3ChartView, axis: ST3ChartAxis?)
}

class ST3ChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val viewSize            : RectF                 = RectF(0.0f, 0.0f, 0.0f, 0.0f)
    private val paint               : Paint                 = Paint()

    var barData                     : ST3ChartBarData?      = null
    var lineData                    : ST3ChartLineData?     = null
    var axises                      : List<ST3ChartAxis>    = ArrayList()

    var axisFont                    : Float                 = 9.0f
    var axisColor                   : Int                   = Color.BLACK
    var axisInterval                : Int                   = 3
    var axisDividerColor            : Int                   = Color.LTGRAY
    var axisBackgroundColor         : Int                   = Color.WHITE

    var lineAxisFont                : Float                 = 8.0f
    var lineAxisColor               : Int                   = Color.BLACK
    var lineAxisInterval            : Int                   = 2

    var axisMargin                  : Float                 = 3.0f

    var leftMargin                  : Float                 = 30.0f
    var rightMargin                 : Float                 = 15.0f
    var bottomMargin                : Float                 = 25.0f

    var horizontalIndicatorWidth    : Float                 = 1.0f
    var highlightIndicatorWidth     : Float                 = 1.0f

    var horizontalIndicatorColor    : Int                   = Color.GRAY
    var highlightIndicatorColor     : Int                   = Color.GRAY

    private var selectedAxis        : ST3ChartAxis?         = null

    var delegate                    : ST3ChartViewDelegate? = null

    private val chartArea               : RectF
        get() {
            val left    = this.leftMargin
            val top           = 0.0f
            val right   = this.viewSize.width() - this.rightMargin
            val bottom  = this.viewSize.height() - this.bottomMargin

            return RectF(left, top, right, bottom)
        }

    init {
        this.paint.isAntiAlias = true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!super.onTouchEvent(event)) else { return true }
        if (event != null) else { return false }

        this.handleMotionEvent(event)

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.e("", "onDraw!: $canvas")
        if (canvas != null) else { return }

        this.onDrawChartBar(canvas)
        this.onDrawLineAxis(canvas)
        this.onDrawHighlightIndicator(canvas)
        this.onDrawAxis(canvas)
        this.onDrawAxisDivider(canvas)
        this.onDrawChartBar(canvas)
        this.onDrawChartLine(canvas)
        this.onDrawHighlightLineCircle(canvas)
    }

    fun reloadData() {
        this.invalidate()
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        this.viewSize.set(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat())
    }

    private fun handleMotionEvent(event: MotionEvent) {
        val findAxis = this.findAxisByMotionEvent(event)
        if (this.selectedAxis != findAxis) {
            this.selectedAxis = findAxis
            this.notifyDidSelected(findAxis)
            this.invalidate()
        }
    }

    private fun findAxisByMotionEvent(event: MotionEvent): ST3ChartAxis? {
        val chartWidth = this.chartArea.width()
        val groupCount = this.axises.size
        val groupWidth = chartWidth / groupCount

        val findIndex = Math.max(Math.min(((event.x - this.leftMargin) / groupWidth).toInt(), groupCount - 1), 0)
        return this.axises[findIndex]
    }

    private fun onDrawLineAxis(canvas: Canvas) {
        this.lineData?.let {
            canvas.save()

            val chartLeft = this.chartArea.left
            val chartWidth = this.chartArea.width()
            val chartHeight = this.chartArea.height()

            val maxValue = it.maxValue
            val axisHeight = chartHeight / maxValue

            var value = this.lineAxisInterval
            do {
                val text = this.lineAxisText(value.toFloat())
                val textSize = this.textSize(text, this.lineAxisFont)

                val indicatorY = chartHeight - (axisHeight * value)
                val x = chartLeft - textSize.width()
                val y = indicatorY + (textSize.height() / 2)

                val rect = RectF(chartLeft, indicatorY, chartLeft + chartWidth, indicatorY + this.horizontalIndicatorWidth)

                this.paint.color = this.horizontalIndicatorColor
                canvas.drawRect(rect, this.paint)

                this.paint.color = this.lineAxisColor
                this.paint.textSize = this.lineAxisFont
                canvas.drawText(text, x, y, this.paint)

                value = (value + this.lineAxisInterval)
            } while (value < maxValue)

            canvas.restore()
        }
    }

    private fun onDrawHighlightIndicator(canvas: Canvas) {
        this.selectedAxis?.let {
            canvas.save()
            val selectedIndex = this.axises.indexOf(it)

            val chartLeft = this.chartArea.left
            val chartTop = this.chartArea.top
            val chartWidth = this.chartArea.width()
            val viewHeight = this.viewSize.height()

            val groupCount = this.axises.size
            val groupWidth = chartWidth / groupCount

            val left = ((selectedIndex * groupWidth) + groupWidth / 2) + chartLeft

            this.paint.color = this.highlightIndicatorColor

            canvas.drawRect(RectF(left, chartTop, left + this.highlightIndicatorWidth, viewHeight), this.paint)
            canvas.restore()
        }
    }

    private fun onDrawAxis(canvas: Canvas) {
        canvas.save()

        val chartLeft = this.chartArea.left
        val chartWidth = this.chartArea.width()
        val chartHeight = this.chartArea.height()

        val groupCount = this.axises.size
        val groupWidth = chartWidth / groupCount

        for ((index, axis) in this.axises.withIndex()) {
            if ((index + 1) % this.axisInterval == 0) else { continue }

            val text = this.axisText(axis)
            val textSize = this.textSize(text, this.axisFont)

            val x = (groupWidth * index) + ((groupWidth - textSize.width()) / 2) + chartLeft
            val y = chartHeight + this.axisMargin

            this.paint.color = this.axisBackgroundColor
            canvas.drawRect(RectF(x, y, x + textSize.width(), y + textSize.height()), this.paint)

            this.paint.color = this.axisColor
            canvas.drawText(text, x, y + textSize.height(), this.paint)
        }

        canvas.restore()
    }

    private fun onDrawAxisDivider(canvas: Canvas) {
        canvas.save()
        val width = this.viewSize.width()
        val chartHeight = this.chartArea.height()

        this.paint.color = this.axisDividerColor
        canvas.drawRect(0.0f, chartHeight, width, chartHeight + this.horizontalIndicatorWidth, this.paint)

        canvas.restore()
    }

    private fun onDrawChartBar(canvas: Canvas) {
        this.barData?.let {
            canvas.save()

            val chartLeft = this.chartArea.left
            val chartWidth = this.chartArea.width()
            val chartHeight = this.chartArea.height()

            val groupCount = this.axises.size
            val groupWidth = chartWidth / groupCount

            val totalBarWidth = groupWidth * (1 - it.groupSpace)
            val barWidth = totalBarWidth / it.dataSets.size

            var maxBarHeight = chartHeight
            this.lineData?.let {
                val axisHeight = chartHeight / it.maxValue
                maxBarHeight = axisHeight * this.lineAxisInterval
            }

            for ((dataSetIndex, dataSet) in it.dataSets.withIndex()) {
                for ((entryIndex, entry) in dataSet.entries.withIndex()) {
                    val left = (entryIndex * groupWidth) + (dataSetIndex * barWidth) + (totalBarWidth / 2) + chartLeft
                    val top = chartHeight - (entry.value * (maxBarHeight / it.maxValue))
                    val right = left + barWidth

                    this.paint.color = dataSet.color
                    canvas.drawRect(RectF(left, top, right, chartHeight), this.paint)
                }

            }
            canvas.restore()
        }
    }

    private fun onDrawChartLine(canvas: Canvas) {
        this.lineData?.let {
            canvas.save()

            val chartLeft = this.chartArea.left
            val chartWidth = this.chartArea.width()
            val chartHeight = this.chartArea.height()

            val groupCount = this.axises.size
            val groupWidth = chartWidth / groupCount

            for (dataSet in it.dataSets) {
                var lineSegments = ArrayList<PointF>()
                for ((entryIndex, entry) in dataSet.entries.withIndex()) {
                    val x = ((entryIndex * groupWidth) + groupWidth / 2.0f) + chartLeft
                    val y = chartHeight - (entry.value * (chartHeight / it.maxValue))

                    val newPoint = PointF(x, y)
                    lineSegments.add(newPoint)

                    if ((0 < entryIndex) && (entryIndex < dataSet.entries.size - 1)) {
                        lineSegments.add(newPoint)
                    }
                }
                this.paint.color = dataSet.color
                this.paint.strokeWidth = dataSet.width
                for ((index, point) in lineSegments.withIndex()) {
                    if (index % 2 == 1) {
                        val startPoint = lineSegments[index - 1]
                        canvas.drawLine(startPoint.x, startPoint.y, point.x, point.y, this.paint)
                    }
                }
            }
            canvas.restore()
        }
    }

    private fun onDrawHighlightLineCircle(canvas: Canvas) {
        val selectedAxis = this.selectedAxis
        if (selectedAxis != null) else { return }

        this.lineData?.let {
            canvas.save()

            val selectedIndex = this.axises.indexOf(selectedAxis)

            val chartLeft = this.chartArea.left
            val chartWidth = this.chartArea.width()
            val chartHeight = this.chartArea.height()

            val groupCount = this.axises.size
            val groupWidth = chartWidth / groupCount


            for (dataSet in it.dataSets) {
                if (selectedIndex < dataSet.entries.size) {
                    val entry = dataSet.entries[selectedIndex]
                    val x = ((selectedIndex * groupWidth) + groupWidth / 2) + chartLeft
                    val y = chartHeight - (entry.value * (chartHeight / it.maxValue))


                    this.paint.color = dataSet.circleBorderColor
                    canvas.drawCircle(x, y, dataSet.circleRadius + dataSet.circleBorder, this.paint)

                    this.paint.color = dataSet.circleColor
                    canvas.drawCircle(x, y, dataSet.circleRadius, this.paint)

                    this.paint.color = dataSet.holeColor
                    canvas.drawCircle(x, y, dataSet.holeRadius, this.paint)
                }
            }
        }
        canvas.restore()
    }

    private fun textSize(text: String, fontSize: Float): Rect {
        val rect = Rect()
        this.paint.textSize = fontSize
        this.paint.getTextBounds(text, 0, text.length, rect)
        return rect
    }

    private fun lineAxisText(value: Float): String {
        return this.delegate?.chartViewLineAxisText(this, value) ?: "$value"
    }

    private fun axisText(axis: ST3ChartAxis): String {
        return this.delegate?.chartViewAxisText(this, axis) ?: axis.text
    }

    private fun notifyDidSelected(axis: ST3ChartAxis?) {
        this.delegate?.chartViewDidSelected(this, axis)
    }
}