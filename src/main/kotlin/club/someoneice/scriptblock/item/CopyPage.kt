package club.someoneice.scriptblock.item

import club.someoneice.scriptblock.ScriptBlockMain
import club.someoneice.scriptblock.block.ScriptBlockTile
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class CopyPage : Item() {
    init {
        this.setUnlocalizedName("copy_page")
        this.setTextureName(ScriptBlockMain.MODID + ":copy_page")

        GameRegistry.registerItem(this, "copy_page", ScriptBlockMain.MODID)
    }

    override fun onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile) {
            item.stackSize -= 1
            val items = ItemStack(ScriptBlockMain.RecordingPage)
            items.stackTagCompound.setString("command", tile.command)
            player.inventory.addItemStackToInventory(items)
        }

        return true;
    }
}