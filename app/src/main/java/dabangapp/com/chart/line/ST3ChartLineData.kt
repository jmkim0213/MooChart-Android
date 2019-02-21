package dabangapp.com.chart.line

import dabangapp.com.chart.base.ST3ChartData

//
class ST3ChartLineData: ST3ChartData<ST3ChartLineDataSet> {
    override var dataSets    : List<ST3ChartLineDataSet> = ArrayList()
    override var maxValue    : Float = 0.0f

    constructor(dataSets: List<ST3ChartLineDataSet>) {
        this.dataSets = dataSets
    }
}