package com.github.jmkim0213.chart.bar

import com.github.jmkim0213.chart.base.MooChartData

class MooChartBarData: MooChartData<MooChartBarDataSet> {
    override var dataSets    : List<MooChartBarDataSet> = ArrayList()
    override var maxValue    : Float = 0.0f
    var groupSpace           : Float = 0.5f

    constructor(dataSets: List<MooChartBarDataSet>) {
        this.dataSets = dataSets
    }
}