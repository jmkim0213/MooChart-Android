package dabangapp.com.chart.bar

import dabangapp.com.chart.base.ST3ChartData

class ST3ChartBarData: ST3ChartData<ST3ChartBarDataSet> {
    override var dataSets    : List<ST3ChartBarDataSet> = ArrayList()
    override var maxValue    : Float = 0.0f
    var groupSpace           : Float = 0.5f

    constructor(dataSets: List<ST3ChartBarDataSet>) {
        this.dataSets = dataSets
    }
}