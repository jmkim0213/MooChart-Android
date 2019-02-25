package dabangapp.com.chart.utils

import android.content.Context
import android.util.TypedValue

class TypedValueUtils {
    companion object {
        fun dpToPixel(context: Context, dp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        }

        fun spToPixel(context: Context, sp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
        }
    }
}
