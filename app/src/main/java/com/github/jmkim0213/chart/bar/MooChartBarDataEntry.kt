package com.github.jmkim0213.chart.bar

import com.github.jmkim0213.chart.base.MooChartDataEntry

class MooChartBarDataEntry: MooChartDataEntry {
    override var value   : Float = 0.0f

    constructor(value: Float) {
        this.value = value
    }
}
