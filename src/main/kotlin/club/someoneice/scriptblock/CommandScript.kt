package club.someoneice.scriptblock

import alexsocol.asjlib.toItem
import club.someoneice.scriptblock.init.BlockInit
import club.someoneice.scriptblock.init.ItemInit
import com.google.common.collect.Lists
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound

class CommandScript : CommandBase() {
    override fun getCommandName(): String {
        return "script"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/script"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        val player = getPlayer(sender, sender.commandSenderName)
        when (list[0]) {
            "reload"    -> readFromJson()
            "setRaw"    -> setCommandInPaper(player, list[1], true)
            "set"       -> setCommandInPaper(player, list[1], false)
            "block"     -> player.inventory.addItemStackToInventory(BlockInit.SCRIPT_BLOCK.toItem()?.stack())
        }
    }

    override fun getCommandAliases(): List<String> {
        val list = Lists.newArrayList<String>()
        list.add("scb")
        list.add("sb")
        return list
    }

    override fun addTabCompletionOptions(sender: ICommandSender, array: Array<String>): List<String> {
        val list = Lists.newArrayList<String>()
        list.add("reload")
        list.add("setRaw")
        list.add("set")
        list.add("block")

        return list
    }

    private fun setCommandInPaper(player: EntityPlayer, command: String, raw: Boolean) {
        val item = ItemInit.RECORDING_PAGE.stack()
        item.tagCompound = NBTTagCompound()
        item.tagCompound.setString("command", if (raw) command else CommandList[command] ?: return)
        player.inventory.addItemStackToInventory(item)
    }
}