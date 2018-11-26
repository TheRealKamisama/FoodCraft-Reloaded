package com.github.takakuraanri.foodcraftreloaded.minecraft

import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.common.food.manufacturedProperty
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.item.FCRItemFood
import net.minecraft.util.ResourceLocation

fun ResourceLocation?.toDotString(): String = "${this?.namespace}.${this?.path?.replace("/", ".")}"

fun FCRItemFood.isManufacturedFood(): Boolean =
    this.food is FoodContainer && (this.food as FoodContainer).properties.containsKey(manufacturedProperty)
