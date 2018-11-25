package com.github.takakuraanri.foodcraftreloaded.minecraft.common

import com.github.takakuraanri.foodcraftreloaded.minecraft.client.ModelHandler
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.builtin.registerBuiltins
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLConstructionEvent

const val MODID: String = "foodcraftreloaded"
@Mod(modid = MODID, dependencies = "required-after:forgelin@[1.3.0,);", useMetadata = true, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object FoodCraftReloadedMod {
    @Mod.EventHandler
    fun construct(event: FMLConstructionEvent) {
        registerBuiltins()
        MinecraftForge.EVENT_BUS.register(RegisterEventHandler)
        MinecraftForge.EVENT_BUS.register(ModelHandler)
    }
}
