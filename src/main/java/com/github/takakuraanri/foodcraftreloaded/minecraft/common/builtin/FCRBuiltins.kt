package com.github.takakuraanri.foodcraftreloaded.minecraft.common.builtin

import com.github.takakuraanri.foodcraftreloaded.common.food.*
import com.github.takakuraanri.foodcraftreloaded.common.food.ManufactureProperties.*
import com.github.takakuraanri.foodcraftreloaded.minecraft.common.MODID
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.minecraft.util.ResourceLocation
import java.awt.Color

enum class BuiltinFruits(val color: Color) {
    /**
     * Pear
     * 梨
     */
    PEAR(Color(0xe5db3b)),

    /**
     * Litchi
     * 荔枝
     */
    LITCHI(Color(0xf6edd0)),

    /**
     * Peach
     * 桃
     */
    PEACH(Color(0xffafaf)),

    /**
     * Orange
     * 橘子
     */
    ORANGE(Color(0xf6ae24)),

    /**
     * Mango
     * 芒果
     */
    MANGO(Color(0xffd986)),

    /**
     * Lemon
     * 柠檬
     */
    LEMON(Color(0xfcf393)),

    /**
     * Grapefruit
     * 柚子
     */
    GRAPEFRUIT(Color(0xece382)),

    /**
     * Persimmon
     * 柿子
     */
    PERSIMMON(Color(0xeb8c30)),

    /**
     * Papaya
     * 木瓜
     */
    PAPAYA(Color(0xf18a25)),

    /**
     * Hawthorn
     * 山楂
     */
    HAWTHORN(Color(0xea7b0e)),

    /**
     * Pomegranate
     * 石榴
     */
    POMEGRANATE(Color(0xf46c30)),

    /**
     * Date
     * 红枣
     */
    DATE(Color(0xb57c63)),

    /**
     * Cherry
     * 樱桃
     */
    CHERRY(Color(0xfd6d0d)),

    /**
     * Coconut
     * 椰子
     */
    COCONUT(Color(0xfcf4d6)),

    /**
     * Banana
     * 香蕉
     */
    BANANA(Color(0xf7eb6a));

    val properties: List<ManufactureProperties> = listOf(SHREDDED, CHUNK, SLICE, JAM, JUICE, SODA, YOGURT)
}

enum class BuiltinVegetables(val color: Color) {
    /**
     * 茄子
     * Eggplant
     */
    EGGPLANT(Color(0xc300ff)),

    /**
     * 黄瓜
     * Cucumber
     */
    CUCUMBER(Color(0x06ad1a)),

    /**
     * 白菜
     * Cabbage
     */
    CABBAGE(Color(0xe2ffe6)),

    /**
     * 莴苣(生菜)
     * Lettuce
     */
    LETTUCE(Color(0x98f9a7)),

    /**
     * 蒿子杆
     * Artemisia stalk
     */
    ARTEMISIA_STALK(Color(0x61f979)),

    /**
     * 菠菜
     * Spinach
     */
    SPINACH(Color(0x027714)),

    /**
     * 芹菜
     * Celery
     */
    CELERY(Color(0x15541f)),

    /**
     * 番茄
     * Tomato
     */
    TOMATO(Color(0xff2121)),

    /**
     * 大米
     * Rice
     */
    RICE(Color(0xeeeedd)),

    /**
     * 糯米
     * Glutinous rice
     */
    GLUTINOUS_RICE(Color(0xffffdd)),

    /**
     * 黑米
     * Black rice
     */
    BLACK_RICE(Color(0xaaaaaa)),

    /**
     * 玉米
     * Corn
     */
    CORN(Color(0xffff99)),

    /**
     * 花生
     * Peanut
     */
    PEANUT(Color(0xeedd00)),

    /**
     * 水萝卜(小萝卜)
     * Radish
     */
    RADISH(Color(0xff4444)),

    /**
     * 白萝卜
     * White radish
     */
    WHITE_RADISH(Color(0xffdddd)),

    /**
     * 红椒
     * Red pepper
     */
    RED_PEPPER(Color(0xFF7F39)),

    /**
     * 青椒
     * Green pepper
     */
    GREEN_PEPPER(Color(0x75dd09)),

    /**
     * 朝天椒
     * Facing heaven pepper
     */
    FACING_HEAVEN_PEPPER(Color(0xb21816)),

    /**
     * 胡椒
     * Pepper
     */
    PEPPER(Color(0x463337)),

    /**
     * 花椒
     * Zanthoxylum
     */
    ZANTHOXYLUM(Color(0x374832)),

    /**
     * 红薯
     * Red sweet potato
     */
    RED_SWEET_POTATO(Color(0xFFA265)),

    /**
     * 白薯
     * White sweet potato
     */
    WHITE_SWEET_POTATO(Color(0xFFF3EC)),

    /**
     * 紫薯
     * Purple sweet potato
     */
    PURPLE_SWEET_POTATO(Color(0xC56C97)),

    /**
     * 芝麻
     * Sesame
     */
    SESAME(Color(0xFFF973)),

    /**
     * 蒜
     * Garlic
     */
    GARLIC(Color(0xFBF6FF)),

    /**
     * 姜
     * Ginger
     */
    GINGER(Color(0xDBB121)),

    /**
     * 洋葱
     * Onion
     */
    ONION(Color(0xDCD8AE)),

    /**
     * 葱
     * Green onion
     */
    GREEN_ONION(Color(0x84F941));

    val properties: List<ManufactureProperties> = listOf(SHREDDED, CHUNK, SLICE, JAM, JUICE, SODA, YOGURT, RING, POWDER)
}

val fruitType = BasicFoodType(1.0, 0.4f)
val vegetableType = BasicFoodType(1.0, 0.3f)

val manufactureType = BasicFoodType(0.0, 0f, false, 16384.0)

fun generateManufactures(container: FoodContainer) = runBlocking {
    container.properties.forEach {
        if (it.key is ManufactureProperties) {
            val manufacture = it.key as ManufactureProperties
            food {
                registryName = ResourceLocation(container.registryName?.namespace
                    ?: MODID, "${container.registryName?.path}_${it.key}")
                type = manufactureType
                withProperty(originalFood, container)
                withProperty(color, container.getProperty(color))
                withProperty(manufacturedProperty, manufacture)
                when (manufacture) {
                    JUICE -> withProperty(colorTintIndex, 1)
                    SODA -> withProperty(colorTintIndex, 1)
                }
            }
        }
    }
}

fun registerBuiltins() = runBlocking {
    launch {
        // Foods
        BuiltinFruits.values().forEach {
            food {
                registryName = ResourceLocation(MODID, it.name.toLowerCase())
                healAmount = 1
                saturation = 1f
                type = fruitType
                withProperty(color, it.color.rgb)
                properties.putAll(it.properties.map { property -> property to ManufactureData() }.toMap())
            }
        }
    }
    launch {
        // Vegetables
        BuiltinVegetables.values().forEach {
            food {
                registryName = ResourceLocation(MODID, it.name.toLowerCase())
                healAmount = 1
                saturation = 1f
                type = vegetableType
                withProperty(color, it.color.rgb)
                properties.putAll(it.properties.map { property -> property to ManufactureData() }.toMap())
            }
        }
    }
}
