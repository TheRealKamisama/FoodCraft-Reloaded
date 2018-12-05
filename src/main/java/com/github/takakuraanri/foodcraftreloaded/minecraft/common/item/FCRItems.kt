package com.github.takakuraanri.foodcraftreloaded.minecraft.common.item

import com.github.takakuraanri.foodcraftreloaded.common.food.Food
import com.github.takakuraanri.foodcraftreloaded.common.food.FoodContainer
import com.github.takakuraanri.foodcraftreloaded.common.food.originalFood
import com.github.takakuraanri.foodcraftreloaded.common.food.resolveSuperType
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.foodTypeCreativeTabs
import com.github.takakuraanri.foodcraftreloaded.minecraft.getLocalizedName
import com.github.takakuraanri.foodcraftreloaded.minecraft.toDotString
import com.github.takakuraanri.foodcraftreloaded.minecraft.translate
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.text.translation.I18n
import net.minecraft.world.World

open class FCRItemFood(var food: Food) : ItemFood(0, false) {
    init {
        registryName = food.registryName
        translationKey = "${food.registryName?.namespace}.${food.registryName?.path}"
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        if (food is FoodContainer) {
            val container = food as FoodContainer
            if (container.properties.containsKey(originalFood)) {
                return I18n.translateToLocalFormatted(
                    "item.foodcraftreloaded.${container.type}",
                    I18n.translateToLocal("item.${container.getProperty(originalFood).map { it.registryName.toDotString() }.orElse("null")}.name").trim())
            }
        }
        return super.getItemStackDisplayName(stack)
    }

    override fun getHealAmount(stack: ItemStack): Int {
        return food.healAmount
    }

    override fun getSaturationModifier(stack: ItemStack): Float {
        return food.saturation
    }

    override fun getMaxItemUseDuration(stack: ItemStack): Int {
        return food.itemUseDuration
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val itemStack = playerIn.getHeldItem(handIn)

        return if (playerIn.canEat(food.alwaysEdible)) {
            playerIn.activeHand = handIn
            ActionResult(EnumActionResult.SUCCESS, itemStack)
        } else {
            ActionResult(EnumActionResult.FAIL, itemStack)
        }
    }

    override fun getCreativeTab(): CreativeTabs? {
        if (food is FoodContainer) {
            val container = food as FoodContainer
            return foodTypeCreativeTabs[container.type.resolveSuperType()]
        }
        return null
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip += translate("tooltip.foodcraftreloaded.heal", food.healAmount)
        if (food is FoodContainer) {
            val container = food as FoodContainer
            tooltip += translate("tooltip.foodcraftreloaded.type", container.type.getLocalizedName())
        }
    }
}



class ItemManufacturedFood(var food: Food)
