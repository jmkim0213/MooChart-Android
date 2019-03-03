package com.github.jmkim0213.chart.line

import com.github.jmkim0213.chart.base.MooChartData

//
class MooChartLineData: MooChartData<MooChartLineDataSet> {
    override var dataSets    : List<MooChartLineDataSet> = ArrayList()
    override var maxValue    : Float = 0.0f

    constructor(dataSets: List<MooChartLineDataSet>) {
        this.dataSets = dataSets
    }
}