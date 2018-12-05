package com.github.takakuraanri.foodcraftreloaded.minecraft.common

import com.github.takakuraanri.foodcraftreloaded.common.food.Food
import com.github.takakuraanri.foodcraftreloaded.common.food.FoodType
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.item.FCRItemFood
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryBuilder

val foodTypeRegistry: IForgeRegistry<FoodType> = RegistryBuilder<FoodType>()
    .setName(ResourceLocation(MODID, "food_type"))
    .setType(FoodType::class.java)
    .create()

val foodRegistry: IForgeRegistry<Food> = RegistryBuilder<Food>()
    .setName(ResourceLocation(MODID, "food"))
    .setType(Food::class.java)
    .create()

val foodMap: MutableMap<Food, FCRItemFood> = hashMapOf()
val foodTypeCreativeTabs: MutableMap<FoodType, CreativeTabs> = hashMapOf()
val foodTypeCache: Multimap<FoodType, Food> = HashMultimap.create()
