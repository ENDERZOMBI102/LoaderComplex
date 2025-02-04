package com.enderzombi102.loadercomplex.forge122.impl.utils

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.entity.player.EntityPlayer as McPlayerEntity
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.util.math.Vec3d as McVec3d


fun ItemStack.toMC(): McItemStack =
	stack as McItemStack

fun BlockPos.toLC(): Vec3i =
	Vec3i( x, y, z )

fun Vec3i.toMC(): BlockPos =
	BlockPos( x, y, z )

fun McVec3d.toLC(): Vec3d =
	Vec3d( x, y, z )

fun Vec3d.toMC(): McVec3d =
	McVec3d( x, y, z )

fun PlayerEntity.toMC(): McPlayerEntity =
	`object` as McPlayerEntity

fun ResourceIdentifier.toMC(): ResourceLocation =
	ResourceLocation( this.namespace, this.path )

fun ResourceLocation.toLC(): ResourceIdentifier =
	ResourceIdentifier( this.resourceDomain, this.resourcePath )
