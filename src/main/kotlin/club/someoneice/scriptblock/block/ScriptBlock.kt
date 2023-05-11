package club.someoneice.scriptblock.block

import club.someoneice.scriptblock.CommandList
import club.someoneice.scriptblock.Handler
import club.someoneice.scriptblock.ScriptBlockMain
import club.someoneice.scriptblock.init.ItemBase
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChunkCoordinates
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class ScriptBlock: BlockContainer(Material.wood) {
    init {
        this.setBlockName("scriptblock")
        this.setCreativeTab(CreativeTabs.tabMisc)
        this.setHardness(-1.0F)
        this.setBlockTextureName(ScriptBlockMain.MODID + ":scriptblock")

        GameRegistry.registerBlock(this, "scriptblock")
    }

    override fun getIcon(world: IBlockAccess, side: Int, x: Int, y: Int, z: Int): IIcon {
        val te = world.getTileEntity(x, y, z)
        return if (te is ScriptBlockTile && te.blockName != null) {
            getBlockFromName(te.blockName).getIcon(side, 0) ?: super.getIcon(world, side, x, y, z)
        } else super.getIcon(world, side, x, y, z)
    }

    override fun onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false;

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && tile.isRightClickEventOn) {
            if (player.heldItem != null && player.heldItem.item is ItemBase) return false
            player.addChatComponentMessage(ChatComponentText("Done!"))
            Handler(world, ChunkCoordinates(x, y, z), player, tile.command ?: return false)
            return true
        }
        return false
    }

    override fun onEntityWalking(world: World, x: Int, y: Int, z: Int, entity: Entity) {
        if (world.isRemote) return;

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && entity is EntityPlayer && tile.isWalkingEventOn) {
            Handler(world, ChunkCoordinates(x, y, z), entity, CommandList[tile.command ?: return] ?: return)
        }
    }

    override fun createNewTileEntity(world: World, meta: Int): TileEntity {
        return ScriptBlockTile()
    }
}