package com.github.jmkim0213.chart.base

import java.util.*


class MooChartAxis {
    var text        : String    = ""
    var data        : Any?     = null

    constructor(text: String, data: Any?) {
        this.text = text
        this.data = data
    }

    override fun hashCode(): Int {
        return "${this.text.hashCode()}_${this.data?.hashCode()}".hashCode()
    }
}
