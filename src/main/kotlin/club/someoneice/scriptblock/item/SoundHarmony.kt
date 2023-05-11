package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.ItemBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.world.World

class SoundHarmony: ItemBase("sound_harmony") {
    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false;

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile) {
            tile.event += 1
            tile.markDirty()
            when (tile.event) {
                1 -> player.addChatComponentMessage(ChatComponentTranslation("scb.sh.walk"))
                2 -> player.addChatComponentMessage(ChatComponentTranslation("scb.sh.click"))
                3 -> player.addChatComponentMessage(ChatComponentTranslation("scb.sh.both"))
                else -> player.addChatComponentMessage(ChatComponentTranslation("scb.sh.none"))
            }
        }

        return true
    }
}