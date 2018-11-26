package com.github.takakuraanri.foodcraftreloaded.minecraft.common

import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.builtin.generateManufactures
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.item.FCRItemFood
import com.github.takakuraanri.foodcraftreloaded.minecraft.toDotString
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object RegisterEventHandler {
    @SubscribeEvent
    fun onRegisterItem(event: RegistryEvent.Register<Item>) {
        foodTypeRegistry.valuesCollection.asSequence().map { it to object : CreativeTabs(it.registryName.toDotString()) {
                override fun createIcon(): ItemStack {
                    val food = foodMap[foodTypeCache[it].first()]
                    return if (food == null) ItemStack.EMPTY else ItemStack(food)
                }
            }
        }.forEach(foodTypeCreativeTabs::plusAssign)

        foodRegistry.valuesCollection
            .filter { it is FoodContainer }
            .map { it as FoodContainer }
            .forEach(::generateManufactures)

        foodRegistry.valuesCollection
            .asSequence()
            .map{ it to FCRItemFood(it) }
            .onEach{ foodMap[it.first] = it.second }
            .onEach{ event.registry.register(it.second) }
            .filter { it.first is FoodContainer }
            .map { it.first as FoodContainer }
            .forEach { foodTypeCache[it.type] += it }
    }
}
