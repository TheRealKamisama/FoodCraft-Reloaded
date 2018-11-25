package com.github.takakuraanri.foodcraftreloaded.minecraft.common

import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.builtin.generateManufactures
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.item.FCRItemFood
import kotlinx.coroutines.runBlocking
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object RegisterEventHandler {
    @SubscribeEvent
    fun onRegisterItem(event: RegistryEvent.Register<Item>) = runBlocking {
        foodRegistry.valuesCollection.filter { it is FoodContainer }.forEach { generateManufactures(it as FoodContainer) }
        foodRegistry.valuesCollection.asSequence().map{ it to FCRItemFood(it) }.onEach{ foodMap[it.first] = it.second }.forEach{ event.registry.register(it.second) }
    }
}
