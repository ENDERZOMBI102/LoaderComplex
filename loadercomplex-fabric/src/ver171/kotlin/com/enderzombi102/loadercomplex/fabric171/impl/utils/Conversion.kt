package com.enderzombi102.loadercomplex.fabric171.impl.utils

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.util.*
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.entity.EquipmentSlot as McEquipmentSlot
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.world.GameMode as McGameMode
import net.minecraft.util.math.Direction as McDirection
import net.minecraft.util.Hand as McHand
import net.minecraft.util.ActionResult as McActionResult
import net.minecraft.util.UseAction as McUseAction


fun EquipmentSlot.toMC(): McEquipmentSlot =
	McEquipmentSlot.valueOf( name )

fun ItemStack.toMC(): McItemStack =
	stack as McItemStack

fun BlockPos.toLC(): Vec3i =
	Vec3i( x, y, z )

fun Vec3i.toMC(): BlockPos =
	BlockPos( x, y, z )

fun Gamemode.toMC(): McGameMode =
	McGameMode.valueOf( name )

fun McGameMode.toLC(): Gamemode =
	Gamemode.valueOf( name )

fun Direction.toMC(): McDirection =
	McDirection.valueOf( name )

fun McDirection.toLC(): Direction =
	Direction.valueOf( name )

fun Hand.toMC(): McHand =
	McHand.valueOf( name )

fun McHand.toLC(): Hand =
	Hand.valueOf( name )

fun ActionResult.toMC(): McActionResult =
	McActionResult.valueOf( name )

fun McActionResult.toLC(): ActionResult =
	ActionResult.valueOf( name )

fun UseAction.toMC(): McUseAction =
	McUseAction.valueOf( name )

fun McUseAction.toLC(): UseAction =
	UseAction.valueOf( name )

fun PlayerEntity.toMC(): McPlayerEntity =
	`object` as McPlayerEntity

fun ResourceIdentifier.toMC(): Identifier =
	Identifier( this.namespace, this.path )

fun Identifier.toLC(): ResourceIdentifier =
	ResourceIdentifier( this.namespace, this.path )
