package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.CommandList
import club.someoneice.scriptblock.Handler
import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ChunkCoordinates
import net.minecraft.world.World

class BlockHeart: ItemBase("heart_block") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && tile.command != null) {
            Handler(world, ChunkCoordinates(x, y, z), player, CommandList[tile.command] ?: return false)
        }

        return true
    }
}