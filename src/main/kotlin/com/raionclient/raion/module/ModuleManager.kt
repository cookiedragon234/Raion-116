package com.raionclient.raion.module

import com.google.common.reflect.ClassPath
import com.google.gson.JsonObject
import com.raionclient.raion.Raion
import com.raionclient.raion.Raion.logger
import com.raionclient.raion.module.movement.FlightModule
import com.raionclient.raion.module.movement.InventoryMoveModule
import com.raionclient.raion.module.movement.NoSlowModule
import com.raionclient.raion.module.movement.VelocityModule
import com.raionclient.raion.module.player.NoRotateModule
import com.raionclient.raion.module.player.ReachModule
import com.raionclient.raion.module.render.ClickGuiModule
import com.raionclient.raion.module.render.NoRenderModule
import com.raionclient.raion.module.render.StorageEspModule
import com.raionclient.raion.utils.Configurable
import com.raionclient.raion.utils.Saveable
import net.fabricmc.loader.FabricLoader
import net.fabricmc.loader.launch.common.FabricLauncherBase
import net.fabricmc.loader.launch.knot.Knot
import java.io.File
import java.lang.reflect.Modifier

/**
 * @author cookiedragon234 24/Jun/2020
 */
@Suppress("UnstableApiUsage")
object ModuleManager: Saveable {
	val modules: MutableCollection<Module> = hashSetOf()
	
	init {
		this += FlightModule
		this += InventoryMoveModule
		this += NoSlowModule
		this += VelocityModule
		
		this += NoRotateModule
		this += ReachModule
		
		this += ClickGuiModule
		this += NoRenderModule
		this += StorageEspModule
	}
	
	inline operator fun plusAssign(module: Module) {
		modules.add(module)
	}
	
	override val configFile: File = File(Raion.configurationFile, "modules.json")
	
	override fun write(jsonObject: JsonObject) {
		modules.forEach {
			it.write(jsonObject)
		}
	}
	override fun read(jsonObject: JsonObject) {
		modules.forEach {
			it.read(jsonObject)
		}
	}
}
