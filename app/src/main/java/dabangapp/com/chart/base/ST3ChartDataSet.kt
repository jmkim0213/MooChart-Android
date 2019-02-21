package dabangapp.com.chart.base

import android.graphics.Color

interface ST3ChartDataSet<Entry> {
    var entries : List<Entry>
    var color   : Int
}
