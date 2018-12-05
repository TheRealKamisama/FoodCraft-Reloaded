package com.github.takakuraanri.foodcraftreloaded.common.food

import com.github.takakuraanri.foodcraftreloaded.minecraft.common.MODID
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry

val originalFood = BasicFoodProperty(FoodContainer::class.java).setRegistryName("original_food")
val manufacturedProperty = BasicFoodProperty(FoodManufactures::class.java).setRegistryName("manufacture")
val color = BasicFoodProperty(Int::class.java).setRegistryName("color")
val colorTintIndex = BasicFoodProperty(Int::class.java).setRegistryName("color_tint_index")
val productProperty = BasicFoodProperty(FoodProduct::class.java).setRegistryName("product")

interface FoodProperty<T>: IForgeRegistryEntry<FoodProperty<T>> {
    val valueClass: Class<T>
}

data class BasicFoodProperty<T>(override val valueClass: Class<T>): FoodProperty<T>, IForgeRegistryEntry.Impl<FoodProperty<T>>() {
    override fun hashCode(): Int {
        return registryName?.hashCode() ?: super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FoodProperty<*>) return false

        if (registryName != other.registryName) return false

        return true
    }
}

enum class FoodManufactures(
    override var healModifier: Double = 1.0,
    override var saturationModifier: Float = 1f,
    override var edibleModifier: Boolean = false,
    override var durationModifier: Double = 1.0,
    override var superType: FoodType? = manufactureType,
    override val validProperties: List<FoodProperty<*>> = listOf(color, colorTintIndex, originalFood, productProperty)
): FoodType {
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
     * 丝
     */
    SHREDDED;
    
    override fun toString(): String {
        return name.toLowerCase()
    }

    override fun getRegistryName(): ResourceLocation = ResourceLocation(MODID, toString())

    override fun getRegistryType(): Class<FoodType> = javaClass

    override fun setRegistryName(name: ResourceLocation?): FoodType {
        throw IllegalAccessException("Cannot set registryName on an enum")
    }
}
