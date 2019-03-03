package com.github.jmkim0213.chart.bar

import android.graphics.Color
import com.github.jmkim0213.chart.base.MooChartDataSet

class MooChartBarDataSet: MooChartDataSet<MooChartBarDataEntry> {
    override var entries : List<MooChartBarDataEntry> = ArrayList()
    override var color   : Int = Color.BLUE

    constructor(entries: List<MooChartBarDataEntry>) {
        this.entries = entries
    }
}