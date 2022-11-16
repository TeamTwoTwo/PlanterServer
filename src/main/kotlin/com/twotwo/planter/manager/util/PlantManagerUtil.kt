package com.twotwo.planter.manager.util

import com.twotwo.planter.manager.domain.PlantManagerCategory
import org.springframework.stereotype.Component
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.roundToInt

@Component
class PlantManagerUtil {
    val R = 6372.8 * 1000

    fun getCategoryInt(category: PlantManagerCategory): Int {
        val categoryEnumList: List<PlantManagerCategory> = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)
        return categoryEnumList.indexOf(category)
    }
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return ((R * c)/1000 * 100.0).roundToInt() / 100.0
    }
}

