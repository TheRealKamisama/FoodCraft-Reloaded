package com.github.takakuraanri.foodcraftreloaded.minecraft.client

import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.common.food.color
import com.github.takakuraanri.foodcraftreloaded.common.food.colorTintIndex
import com.github.takakuraanri.foodcraftreloaded.common.food.manufacturedProperty
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.MODID
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.foodMap
import net.minecraft.client.renderer.block.model.ModelBakery
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModelHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onModelRegister(event: ModelRegistryEvent) {
        registerModels()
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onColorRegister(event: ColorHandlerEvent.Item) {
        registerItemColors(event.itemColors)
    }
}

fun registerModels() {
    foodMap.forEach {
        if (it.key is FoodContainer && (it.key as FoodContainer).properties.containsKey(manufacturedProperty)) {
            val container = it.key as FoodContainer
            val model = ModelResourceLocation(ResourceLocation(MODID, container[manufacturedProperty].toString()), "inventory")
            ModelLoader.setCustomMeshDefinition(it.value) { model }
            ModelBakery.registerItemVariants(it.value, model)
        } else {
            val model = ModelResourceLocation(it.value.registryName!!, "inventory")
            ModelLoader.setCustomMeshDefinition(it.value) { model }
            ModelBakery.registerItemVariants(it.value, model)
        }
    }
}

fun registerItemColors(itemColors: ItemColors) {
    foodMap.forEach {
        if (it.key is FoodContainer && (it.key as FoodContainer).properties.containsKey(color)) {
            val container = it.key as FoodContainer
            val rgbColor = container.properties[color] as Int? ?: -1
                itemColors.registerItemColorHandler({ _, tintIndex ->
                    val returned =
                        if (container.properties.containsKey(colorTintIndex))
                            if (tintIndex == container.properties[colorTintIndex] as Int?) rgbColor else -1
                        else
                            if (tintIndex == 0) rgbColor else -1
                    returned
            }, arrayOf(it.value))
        }
    }
}
