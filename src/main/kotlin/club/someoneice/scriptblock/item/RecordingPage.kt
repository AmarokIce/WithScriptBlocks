package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class RecordingPage: ItemBase("recording_page") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false;

        val tile = world.getTileEntity(x, y, z)
        if (item.tagCompound == null) item.tagCompound = NBTTagCompound()
        if (tile is ScriptBlockTile && item.tagCompound.hasKey("command")) {
            tile.command = item.tagCompound.getString("command")
            tile.markDirty()
        }
        return true;
    }

    override fun addInformation(item: ItemStack, player: EntityPlayer?, list: List<Any?>, boolean: Boolean) {
        // if (item.tagCompound.hasKey("command")) list.add("Command: ${item.tagCompound.getString("command")}")
    }
}