package club.someoneice.scriptblock.block

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

class ScriptBlockTile : TileEntity() {
    var blockName: String? = null
    var command: String? = null;
    var isWalkingEventOn: Boolean = false
    var isRightClickEventOn: Boolean = false

    override fun writeToNBT(nbt: NBTTagCompound) {
        super.writeToNBT(nbt)

        nbt.setString("block_name", blockName)
        nbt.setString("command", command)
        nbt.setBoolean("is_walking_event", isWalkingEventOn)
        nbt.setBoolean("is_right_chick_event", isRightClickEventOn)
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        blockName           = nbt.getString("block_name")
        command             = nbt.getString("command")
        isWalkingEventOn    = nbt.getBoolean("is_walking_event")
        isRightClickEventOn = nbt.getBoolean("is_right_chick_event")

        super.readFromNBT(nbt)
    }

    override fun canUpdate(): Boolean {
        return false
    }
}