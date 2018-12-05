package com.github.takakuraanri.foodcraftreloaded.minecraft.common

import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.common.food.resolveSuperType
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.item.FCRItemFood
import com.github.takakuraanri.foodcraftreloaded.minecraft.toDotString
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.ProgressManager
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object RegisterEventHandler {
    @SubscribeEvent
    fun onRegisterItem(event: RegistryEvent.Register<Item>) {
        foodTypeRegistry.valuesCollection
            .asSequence()
            .filter { it.superType == null }
            .map { it to object : CreativeTabs(it.registryName.toDotString()) {
                    override fun createIcon(): ItemStack {
                        val food = if (foodTypeCache[it].isNotEmpty()) foodMap[foodTypeCache[it].first()] else null
                        return if (food == null) ItemStack.EMPTY else ItemStack(food)
                    }
                }
            }
            .forEach(foodTypeCreativeTabs::plusAssign)

        val bar = ProgressManager.push("Registering foods", foodRegistry.valuesCollection.size)
        foodRegistry.valuesCollection
            .asSequence()
            .map { it to FCRItemFood(it) }
            .onEach { foodMap[it.first] = it.second }
            .onEach { event.registry.register(it.second); bar.step(it.first.registryName?.toString()) }
            .filter { it.first is FoodContainer }
            .map { it.first as FoodContainer }
            .forEach { foodTypeCache[it.type] += it; foodTypeCache[it.type.resolveSuperType()] += it }
        ProgressManager.pop(bar)
    }
}
