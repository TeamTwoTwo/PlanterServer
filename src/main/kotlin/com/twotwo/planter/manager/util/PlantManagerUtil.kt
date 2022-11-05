package com.twotwo.planter.manager.util

import com.twotwo.planter.manager.domain.PlantManagerCategory
import org.springframework.stereotype.Component

@Component
class PlantManagerUtil {
    fun getCategoryInt(category: PlantManagerCategory): Int {
        val categoryEnumList: List<PlantManagerCategory> = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)
        return categoryEnumList.indexOf(category)
    }
}