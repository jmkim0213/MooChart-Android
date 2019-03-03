package com.github.jmkim0213.chart.line

import android.graphics.Color
import com.github.jmkim0213.chart.base.MooChartDataSet

class MooChartLineDataSet: MooChartDataSet<MooChartLineDataEntry> {
    override var entries    : List<MooChartLineDataEntry>   = ArrayList()
    override var color      : Int                           = Color.BLUE
    var width               : Float                         = 1.0f

    var circleRadius        : Float                         = 5.0f
    var holeRadius          : Float                         = 1.0f
    var circleBorder        : Float                         = 1.0f

    var circleColor         : Int                           = Color.WHITE
    var holeColor           : Int                           = Color.BLUE
    var circleBorderColor   : Int                           = Color.WHITE

    constructor(entries: List<MooChartLineDataEntry>) {
        this.entries = entries
    }
}