package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.CommandList
import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentText
import net.minecraft.world.World

class SeeingEye: ItemBase("see_eye") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && tile.command != null) {
            player.addChatComponentMessage(ChatComponentText(CommandList[tile.command]?.toString()))
        }

        return true
    }
}