package club.someoneice.scriptblock.init

import club.someoneice.scriptblock.ScriptBlockMain
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item

abstract class ItemBase(name: String) : Item() {
    init {
        this.unlocalizedName = name
        this.iconString = ScriptBlockMain.MODID + ":" + name
        this.creativeTab = CreativeTabs.tabMisc

        GameRegistry.registerItem(this, name, ScriptBlockMain.MODID)
    }
}