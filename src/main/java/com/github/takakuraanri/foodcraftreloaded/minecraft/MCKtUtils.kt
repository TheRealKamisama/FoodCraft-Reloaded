package com.github.takakuraanri.foodcraftreloaded.minecraft

import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.common.food.FoodType
import com.github.takakuraanri.foodcraftreloaded.common.food.manufacturedProperty
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.item.FCRItemFood
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.translation.I18n
import net.minecraftforge.registries.IForgeRegistryEntry

fun ResourceLocation?.toDotString(): String = "${this?.namespace}.${this?.path?.replace("/", ".")}"

fun FCRItemFood.isManufacturedFood(): Boolean =
    this.food is FoodContainer && (this.food as FoodContainer).properties.containsKey(manufacturedProperty)

fun translate(key: String, vararg params: Any): String = I18n.translateToLocalFormatted(key, *params)

fun FoodType.getLocalizedName() = translate("type.${this.registryName.toDotString()}")

fun <T> IForgeRegistryEntry<T>.toString() = registryName
