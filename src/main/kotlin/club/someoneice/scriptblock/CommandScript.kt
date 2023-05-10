package club.someoneice.scriptblock

import club.someoneice.scriptblock.item.RecordingPage
import club.someoneice.scriptblock.util.readFromJson
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer

class CommandScript : CommandBase() {
    override fun getCommandName(): String {
        return "script"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/script"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        when (list[0]) {
            "reload" -> readFromJson()
            "set"    -> setCommandInPaper(getPlayer(sender, sender.commandSenderName), list[1])
        }
    }

    private fun setCommandInPaper(player: EntityPlayer, command: String) {
        val item = player.heldItem
        if (item.item is RecordingPage) {
            item.stackTagCompound.setString("command", command)
        }
    }
}