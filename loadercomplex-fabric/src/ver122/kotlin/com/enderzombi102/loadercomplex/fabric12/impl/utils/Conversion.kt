package com.enderzombi102.loadercomplex.fabric12.impl.utils

import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode
import com.enderzombi102.loadercomplex.api.minecraft.util.Position
import net.minecraft.util.math.BlockPos
import net.minecraft.entity.EquipmentSlot as McEquipmentSlot
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.world.GameMode as McGameMode


fun EquipmentSlot.toMC(): McEquipmentSlot =
	McEquipmentSlot.valueOf( name )

fun ItemStack.toMC(): McItemStack =
	stack as McItemStack

fun BlockPos.toLC(): Position =
	Position( x, y, z )

fun Position.toMC(): BlockPos =
	BlockPos( x, y, z )

fun Gamemode.toMC(): McGameMode =
	McGameMode.valueOf( name )

fun McGameMode.toLC(): Gamemode =
	Gamemode.valueOf( name )
