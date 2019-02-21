package dabangapp.com.chart.bar

import android.graphics.Color
import dabangapp.com.chart.base.ST3ChartDataSet

class ST3ChartBarDataSet: ST3ChartDataSet<ST3ChartBarDataEntry> {
    override var entries : List<ST3ChartBarDataEntry> = ArrayList()
    override var color   : Int = Color.BLUE

    constructor(entries: List<ST3ChartBarDataEntry>) {
        this.entries = entries
    }
}