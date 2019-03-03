package com.github.jmkim0213.chart.base

import android.graphics.Color

interface MooChartDataSet<Entry> {
    var entries : List<Entry>
    var color   : Int
}
