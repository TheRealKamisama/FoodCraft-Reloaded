package com.github.takakuraanri.foodcraftreloaded.common.food

import com.github.takakuraanri.foodcraftreloaded.minecraft.common.MODID
import net.minecraft.util.ResourceLocation

interface FoodProduct: FoodType

enum class FoodProducts(
    override var healModifier: Double = 1.0,
    override var saturationModifier: Float = 1f,
    override var edibleModifier: Boolean = false,
    override var durationModifier: Double = 1.0,
    override var superType: FoodType? = null,
    override val validProperties: List<FoodProperty<*>> = listOf(color, colorTintIndex, originalFood, productProperty)
): FoodProduct {
    /**
     * 果酱
     */
    JAM,

    /**
     * 汁
     */
    JUICE(superType = drinkType),

    /**
     * 苏打水
     */
    SODA(superType = drinkType),

    /**
     * 酸奶
     */
    YOGURT(superType = drinkType);

    override fun getRegistryName(): ResourceLocation = ResourceLocation(MODID, toString())

    override fun setRegistryName(name: ResourceLocation?): FoodProduct {
        throw IllegalAccessException("You shouldn't call setRegistryName on a enum class")
    }

    override fun toString(): String {
        return name.toLowerCase()
    }

    override fun getRegistryType(): Class<FoodType> = FoodType::class.java
}
