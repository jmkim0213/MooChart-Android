package dabangapp.com.chart.bar

import dabangapp.com.chart.base.ST3ChartDataEntry

class ST3ChartBarDataEntry: ST3ChartDataEntry {
    override var value   : Float = 0.0f

    constructor(value: Float) {
        this.value = value
    }
}
