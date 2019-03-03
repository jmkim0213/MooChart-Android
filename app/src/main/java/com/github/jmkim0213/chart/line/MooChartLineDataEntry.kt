package com.github.jmkim0213.chart.line

import com.github.jmkim0213.chart.base.MooChartDataEntry

class MooChartLineDataEntry: MooChartDataEntry {
    override var value   : Float = 0.0f

    constructor(value: Float) {
        this.value = value
    }
}
