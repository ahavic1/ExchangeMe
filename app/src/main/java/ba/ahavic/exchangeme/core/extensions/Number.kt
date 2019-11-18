package ba.ahavic.exchangeme.core.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round(places: Int = 3) : Double {
    require(places >= 0)
    return BigDecimal.valueOf(this).setScale(places, RoundingMode.HALF_UP).toDouble()
}

fun Double.greaterThanZero(): Boolean = compareTo(0.0) > 0

fun BigDecimal.greaterThanZero() : Boolean = compareTo(BigDecimal.ZERO) > 0

fun BigDecimal.round(places: Int = 3) : BigDecimal {
    require(places >= 0)
    return setScale(places, RoundingMode.HALF_UP)
}