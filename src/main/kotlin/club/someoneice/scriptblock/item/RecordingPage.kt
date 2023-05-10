package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.ScriptBlockMain
import club.someoneice.scriptblock.block.ScriptBlock
import club.someoneice.scriptblock.block.ScriptBlockTile
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class RecordingPage: Item() {
    init {
        this.setUnlocalizedName("recording_page")
        this.setTextureName(ScriptBlockMain.MODID + ":recording_page")

        GameRegistry.registerItem(this, "recording_page", ScriptBlockMain.MODID)
    }

    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile) tile.command = item.stackTagCompound.getString("command")

        return true;
    }

    override fun addInformation(item: ItemStack, player: EntityPlayer?, list: MutableList<Any?>, boolean: Boolean) {
        list.add("Command: ${item.stackTagCompound.getString("command")}")
    }
}