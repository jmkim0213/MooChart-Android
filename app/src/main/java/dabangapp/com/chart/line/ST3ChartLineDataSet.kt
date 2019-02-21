package dabangapp.com.chart.line

import android.graphics.Color
import dabangapp.com.chart.bar.ST3ChartBarDataEntry
import dabangapp.com.chart.base.ST3ChartDataSet

class ST3ChartLineDataSet: ST3ChartDataSet<ST3ChartLineDataEntry> {
    override var entries    : List<ST3ChartLineDataEntry>   = ArrayList()
    override var color      : Int                           = Color.BLUE
    var width               : Float                         = 1.0f

    var circleRadius        : Float                         = 5.0f
    var holeRadius          : Float                         = 1.0f
    var circleBorder        : Float                         = 1.0f

    var circleColor         : Int                           = Color.WHITE
    var holeColor           : Int                           = Color.BLUE
    var circleBorderColor   : Int                           = Color.WHITE

    constructor(entries: List<ST3ChartLineDataEntry>) {
        this.entries = entries
    }
}