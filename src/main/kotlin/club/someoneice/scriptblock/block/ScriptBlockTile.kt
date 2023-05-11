package club.someoneice.scriptblock.block

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

class ScriptBlockTile : TileEntity() {
    var blockName: String?
    var command: String?
    var isWalkingEventOn: Boolean
    var isRightClickEventOn: Boolean
    var event: Int

    init {
        blockName = null
        command = null
        isWalkingEventOn = false
        isRightClickEventOn = false
        event = 0
    }

    override fun canUpdate(): Boolean = false

    override fun writeToNBT(nbt: NBTTagCompound) {
        super.writeToNBT(nbt)

        nbt.setString("icon_block_name", blockName)
        nbt.setString("command", command)
        nbt.setInteger("event", event)
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        super.readFromNBT(nbt)

        blockName           = nbt.getString("icon_block_name")
        command             = nbt.getString("command")
        event               = nbt.getInteger("event")
        this.markDirty()
    }

    override fun markDirty() {
        when (event) {
            0 -> {
                this.isWalkingEventOn = false
                this.isRightClickEventOn = false
            }

            1 -> {
                this.isWalkingEventOn = true
                this.isRightClickEventOn = false
            }

            2 -> {
                this.isWalkingEventOn = false
                this.isRightClickEventOn = true
            }

            3 -> {
                this.isWalkingEventOn = true
                this.isRightClickEventOn = true
            }

            else -> {
                event = 0
                markDirty()
            }
        }

        super.markDirty()
    }
}