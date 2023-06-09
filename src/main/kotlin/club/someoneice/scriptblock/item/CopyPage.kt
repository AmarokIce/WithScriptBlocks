package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import club.someoneice.scriptblock.init.ItemInit
import club.someoneice.scriptblock.stack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class CopyPage : ItemBase("copy_page") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false;

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && tile.command != null) {
            item.splitStack(1)
            val rp = ItemInit.RECORDING_PAGE.stack()
            rp.stackSize = 1
            rp.tagCompound = NBTTagCompound()
            rp.tagCompound.setString("command", tile.command)
            player.inventory.addItemStackToInventory(rp)
        }

        return true
    }
}