package com.github.takakuraanri.foodcraftreloaded.common.food

import kotlinx.serialization.Serializable
import java.util.*

interface FoodProperty<T> {
    val valueClass: Class<T>
}

val originalFood = BasicFoodProperty(FoodContainer::class.java)
val manufacturedProperty = BasicFoodProperty(ManufactureProperties::class.java)
val color = BasicFoodProperty(Int::class.java)
val colorTintIndex = BasicFoodProperty(Int::class.java)

data class BasicFoodProperty<T>(override val valueClass: Class<T>, val uniqueId: UUID = UUID.randomUUID()): FoodProperty<T>

@Serializable
data class ManufactureData(val buildItem: Boolean = true)

enum class ManufactureProperties: FoodProperty<ManufactureData> {
    /**
     * 圈
     */
    RING,

    /**
     * 小块
     */
    CHUNK,

    /**
     * 粉
     */
    POWDER,

    /**
     * 棒，杆，棍
     */
    BAR,

    /**
     * 片
     */
    SLICE,

    /**
     * 果酱
     */
    JAM,

    /**
     * 汁
     */
    JUICE,

    /**
     * 苏打水
     */
    SODA,

    /**
     * 酸奶
     */
    YOGURT,

    /**
     * 丝
     */
    SHREDDED;

    override val valueClass: Class<ManufactureData> = ManufactureData::class.java
    
    override fun toString(): String {
        return name.toLowerCase()
    }
}
