package com.raionclient.raion.module.render

import com.google.gson.JsonObject
import com.raionclient.raion.gui.Drawable
import com.raionclient.raion.gui.Panel
import com.raionclient.raion.module.Category
import com.raionclient.raion.module.Module
import com.raionclient.raion.utils.*
import imgui.ImGui
import imgui.impl.gl.ImplGL3
import imgui.impl.glfw.ImplGlfw
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import uno.glfw.GlfwWindow
import java.awt.Color


/**
 * @author cookiedragon234 24/Jun/2020
 */
object ClickGuiModule: Module("Click Gui", "A clickable gui", Category.RENDER, Key.KEY_O) {
	override fun onEnabled() {
		mc.openScreen(ClickGuiScreen)
		println("Opened")
	}
	override fun onDisabled() {
		if (mc.currentScreen == ClickGuiScreen) {
			mc.openScreen(null)
		}
	}
	
	// Dont serialize the enabled state
	override fun write(jsonObject: JsonObject) {
	}
	override fun read(jsonObject: JsonObject) {
	}
	
	object ClickGuiScreen: Screen(LiteralText("Raion ClickGui")) {
		val panels = arrayListOf<Drawable>()
		init {
		
		}
		
		override fun onClose() {
			enabled = false
		}
		
		override fun tick() {
			panels.forEach { it.onUpdate() }
		}
		override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
			val mousePos = Vec2f(mouseX.toFloat(), mouseY.toFloat())
			panels.forEach { it.render(matrices, mousePos) }
		}
		override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
			val mousePos = Vec2f(mouseX.toFloat(), mouseY.toFloat())
			val button = Mouse[button]
			var out = false
			panels.forEach { if (it.mouseDown(mousePos, button, out)) { out = true } }
			return out
		}
		override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
			val mousePos = Vec2f(mouseX.toFloat(), mouseY.toFloat())
			val button = Mouse[button]
			var out = false
			panels.forEach { if (it.mouseUp(mousePos, button, out)) { out = true } }
			return out
		}
		override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {
			val mousePos = Vec2f(mouseX.toFloat(), mouseY.toFloat())
			val button = Mouse[button]
			var out = false
			panels.forEach { if (it.mouseDragged(mousePos, button, out)) { out = true } }
			return out
		}
		//override fun charTyped(chr: Char, keyCode: Int): Boolean {
		//}
		//override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		//}
		//override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		//}
	}
}