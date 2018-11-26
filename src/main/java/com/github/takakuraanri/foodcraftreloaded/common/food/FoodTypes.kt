package com.github.takakuraanri.foodcraftreloaded.common.food

import com.github.takakuraanri.foodcraftreloaded.minecraft.common.foodRegistry
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.foodTypeRegistry
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import java.util.*
import kotlin.reflect.KClass

interface FoodType: IForgeRegistryEntry<FoodType> {
    var healModifier: Double
    var saturationModifier: Float
    var edibleModifier: Boolean // True for invert, False for passing it
    var durationModifier: Double

    fun createFoods(vararg registryNames: String, init: () -> FoodContainer = { FoodContainer(food { healAmount = 1; saturation = 1.0f }) }) {
        createFoods(registryNames.asIterable(), init)
    }

    fun createFoods(registryNames: Iterable<String>, init: () -> FoodContainer = { FoodContainer(food { healAmount = 1; saturation = 1.0f }) }) {
        registryNames.forEach {
            val created = init()
            created.registryName = ResourceLocation(it)
            created.type = this
            foodRegistry.register(created)
        }
    }
}

inline infix fun <reified T> KClass<T>.resolve(x: ResourceLocation): T? where T: IForgeRegistryEntry<T> {
    val registry = GameRegistry.findRegistry(T::class.java)
    return registry.getValue(x)
}

data class BasicFoodType(
    override var healModifier: Double = 1.0,
    override var saturationModifier: Float = 1.0f,
    override var edibleModifier: Boolean = false,
    override var durationModifier: Double = 1.0,
    val uniqueId: UUID = UUID.randomUUID()
): FoodType, IForgeRegistryEntry.Impl<FoodType>() {
    override fun hashCode(): Int {
        return uniqueId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasicFoodType) return false

        if (uniqueId != other.uniqueId) return false

        return true
    }
}

val DEFAULT_FOOD_TYPE: FoodType = BasicFoodType().setRegistryName("default")

interface Food: IForgeRegistryEntry<Food> {
    var healAmount: Int

    var saturation: Float

    var itemUseDuration: Int // In ticks

    var alwaysEdible: Boolean
}

data class BasicFood(
    override var healAmount: Int = 0, override var saturation: Float = 0.6f, override var alwaysEdible: Boolean = false,
    override var itemUseDuration: Int = 32
): Food, IForgeRegistryEntry.Impl<Food>() {
    override fun hashCode(): Int {
        return registryName.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasicFood) return false

        if (registryName != other.registryName) return false

        return true
    }
}

class FoodContainer(val food: Food = BasicFood(), var type: FoodType = BasicFoodType()): Food by food, Cloneable {
    val properties: MutableMap<FoodProperty<*>, Any> = HashMap()

    override fun clone(): FoodContainer = FoodContainer(food, type)

    fun type(init: FoodType.() -> Unit): FoodType {
        type.init()
        if (!foodTypeRegistry.containsKey(type.registryName))
            foodTypeRegistry.register(type)
        return type
    }

    fun <T> withProperty(property: FoodProperty<T>, value: T) {
        properties[property] = value as Any
    }

    fun <T> withProperty(property: FoodProperty<T>, value: Optional<T>) {
        value.ifPresent { properties[property] = it as Any }
    }

    fun <T> getProperty(property: FoodProperty<T>): Optional<T>
        = Optional.ofNullable(properties[property] as T?)

    operator fun <T> get(property: FoodProperty<T>): T? = properties[property] as T?

    override var healAmount: Int = food.healAmount
        get() = (food.healAmount * type.healModifier).toInt()
    override var saturation: Float = food.saturation
        get() = food.saturation * type.saturationModifier
    override var itemUseDuration: Int = food.itemUseDuration
        get() = (food.itemUseDuration * type.durationModifier).toInt()
    override var alwaysEdible: Boolean = food.alwaysEdible
        get() = if (type.edibleModifier) food.alwaysEdible else !food.alwaysEdible
}

fun food(init: FoodContainer.() -> Unit): FoodContainer {
    val food = FoodContainer()
    food.init()
    foodRegistry.register(food)
    return food
}
