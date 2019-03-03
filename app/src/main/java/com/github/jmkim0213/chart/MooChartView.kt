package com.github.jmkim0213.chart

import android.content.Context
import android.graphics.*
import android.util.*
import android.view.MotionEvent
import android.view.View
import com.github.jmkim0213.chart.bar.MooChartBarData
import com.github.jmkim0213.chart.base.MooChartAxis
import com.github.jmkim0213.chart.line.MooChartLineData

interface MooChartViewDelegate {
    fun chartViewLineAxisText(chartView: MooChartView, value: Float, index: Int): String
    fun chartViewRightAxisText(chartView: MooChartView, value: Float, index: Int): String
    fun chartViewAxisText(chartView: MooChartView, axis: MooChartAxis): String
    fun chartViewDidSelected(chartView: MooChartView, axis: MooChartAxis?)
}

class MooChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val viewSize            : RectF                 = RectF(0.0f, 0.0f, 0.0f, 0.0f)
    private val paint               : Paint                 = Paint()

    var barData                     : MooChartBarData?      = null
    var lineData                    : MooChartLineData?     = null
    var axises                      : List<MooChartAxis>    = ArrayList()

    var axisFont                    : Float                 = 9.0f
    var axisColor                   : Int                   = Color.BLACK
    var axisInterval                : Int                   = 3
    var axisDividerColor            : Int                   = Color.LTGRAY
    var axisBackgroundColor         : Int                   = Color.WHITE
    var axisDividerMargin           : Float                 = 21.0f

    var lineAxisFont                : Float                 = 8.0f
    var lineAxisColor               : Int                   = Color.BLACK
    var lineAxisInterval            : Int                   = 2

    var axisMargin                  : Float                 = 3.0f
    var lineAxisMargin              : Float                 = 3.0f
    var rightAxisMargin             : Float                 = 3.0f

    var leftMargin                  : Float                 = 30.0f
    var rightMargin                 : Float                 = 15.0f
    var bottomMargin                : Float                 = 25.0f

    var horizontalIndicatorWidth    : Float                 = 1.0f
    var highlightIndicatorWidth     : Float                 = 1.0f

    var horizontalIndicatorColor    : Int                   = Color.GRAY
    var highlightIndicatorColor     : Int                   = Color.GRAY

    private var selectedAxis        : MooChartAxis?         = null

    var delegate                    : MooChartViewDelegate? = null

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

    private fun findAxisByMotionEvent(event: MotionEvent): MooChartAxis? {
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
            val viewWidth = this.viewSize.width()

            val maxValue = it.maxValue
            val axisHeight = chartHeight / maxValue

            var value = this.lineAxisInterval
            var index = 0
            do {
                val leftText = this.lineAxisText(value.toFloat(), index)
                val rightText = this.rightAxisText(value.toFloat(), index)
                val leftTextSize = this.textSize(leftText, this.lineAxisFont)

                val indicatorY = chartHeight - (axisHeight * value)
                val x = chartLeft - leftTextSize.width() - this.lineAxisMargin

                val y = indicatorY + (leftTextSize.height() / 2)

                // draw left axis
                this.paint.color = this.lineAxisColor
                this.paint.textSize = this.lineAxisFont
                canvas.drawText(leftText, x, y, this.paint)

                // draw right axis
                val rightX = (viewWidth - this.rightMargin) + this.rightAxisMargin
                this.paint.color = this.lineAxisColor
                this.paint.textSize = this.lineAxisFont
                canvas.drawText(rightText, rightX, y, this.paint)
                // draw indicator
                val rect = RectF(chartLeft, indicatorY, chartLeft + chartWidth, indicatorY + this.horizontalIndicatorWidth)

                this.paint.color = this.horizontalIndicatorColor
                canvas.drawRect(rect, this.paint)

                index += 1
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
            canvas.restore()
        }
    }

    private fun textSize(text: String, fontSize: Float): Rect {
        val rect = Rect()
        this.paint.textSize = fontSize
        this.paint.getTextBounds(text, 0, text.length, rect)
        return rect
    }

    private fun lineAxisText(value: Float, index: Int): String {
        return this.delegate?.chartViewLineAxisText(this, value, index) ?: "$value"
    }

    private fun rightAxisText(value: Float, index: Int): String {
        return this.delegate?.chartViewRightAxisText(this, value, index) ?: "$value"
    }

    private fun axisText(axis: MooChartAxis): String {
        return this.delegate?.chartViewAxisText(this, axis) ?: axis.text
    }

    private fun notifyDidSelected(axis: MooChartAxis?) {
        this.delegate?.chartViewDidSelected(this, axis)
    }
}