package club.someoneice.scriptblock.block

import club.someoneice.scriptblock.CommandList
import club.someoneice.scriptblock.Handler
import club.someoneice.scriptblock.ScriptBlockMain
import club.someoneice.scriptblock.init.ItemBase
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
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

    override fun onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, face: Int, fx: Float, fy: Float, fz: Float): Boolean {
        if (world.isRemote) return false

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && tile.isRightClickEventOn) {
            if (player.heldItem != null && player.heldItem.item is ItemBase) return false
            Handler(world, ChunkCoordinates(x, y, z), player, CommandList[tile.command ?: return false] ?: return false)
            return true
        }
        return false
    }

    override fun onEntityWalking(world: World, x: Int, y: Int, z: Int, entity: Entity) {
        if (world.isRemote) return

        val tile = world.getTileEntity(x, y, z)
        if (tile is ScriptBlockTile && tile.isWalkingEventOn) {
            if (entity is EntityPlayer) Handler(world, ChunkCoordinates(x, y, z), entity, CommandList[tile.command ?: return] ?: return)
        }

        super.onEntityWalking(world, x, y, z, entity)
    }

    override fun createNewTileEntity(world: World, meta: Int): TileEntity {
        return ScriptBlockTile()
    }

    override fun getIcon(world: IBlockAccess?, x: Int, y: Int, z: Int, side: Int): IIcon {
        val tile = world?.getTileEntity(x, y, z)
        return if (tile is ScriptBlockTile && tile.blockName != 0) {
            this.getIcon(side, tile.blockName)
        } else super.getIcon(world, x, y, z, side)
    }

    override fun getIcon(side: Int, meta: Int): IIcon {
        return if (meta != 0) Block.getBlockById(meta).getIcon(side, 0) else this.blockIcon
    }
}