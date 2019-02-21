package dabangapp.com.chart.line

import dabangapp.com.chart.base.ST3ChartDataEntry

class ST3ChartLineDataEntry: ST3ChartDataEntry {
    override var value   : Float = 0.0f

    constructor(value: Float) {
        this.value = value
    }
}
