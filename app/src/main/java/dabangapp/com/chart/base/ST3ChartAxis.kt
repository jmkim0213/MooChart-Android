package dabangapp.com.chart.base

import java.util.*


class ST3ChartAxis {
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
