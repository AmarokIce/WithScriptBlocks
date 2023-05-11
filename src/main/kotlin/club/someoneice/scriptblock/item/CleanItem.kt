package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class CleanItem: ItemBase("clean_item") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false;
        for (py in y-1 .. y+1) for (px in x-1 .. x+1) for (pz in z-1 .. z+1) {
            val tile = world.getTileEntity(px, py, pz)
            if (tile is ScriptBlockTile)
                tile.blockName = null
        }

        return true
    }

    override fun addInformation(item: ItemStack, player: EntityPlayer?, list: List<Any?>, boolean: Boolean) {
        // list.add(ChatComponentTranslation("scb.clean.message"))
    }
}