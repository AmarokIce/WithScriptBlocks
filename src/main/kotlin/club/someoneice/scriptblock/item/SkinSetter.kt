package club.someoneice.scriptblock.item

import alexsocol.asjlib.id
import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class SkinSetter: ItemBase("skin_setter") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        val tile = world.getTileEntity(x, y, z)
        if (item.tagCompound == null) item.tagCompound = NBTTagCompound()
        if (tile is ScriptBlockTile && item.tagCompound.hasKey("skin")) {
            tile.blockName = item.tagCompound.getInteger("skin")
            world.markBlockForUpdate(x, y, z)
            tile.markDirty()
        }
        else item.tagCompound.setInteger("skin", world.getBlock(x, y, z).id)


        return true
    }
}