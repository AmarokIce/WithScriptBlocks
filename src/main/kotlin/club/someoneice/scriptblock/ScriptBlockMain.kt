package club.someoneice.scriptblock

import alexsocol.patcher.KotlinAdapter
import club.someoneice.scriptblock.block.ScriptBlock
import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.item.CopyPage
import club.someoneice.scriptblock.item.RecordingPage
import club.someoneice.scriptblock.util.readFromJson
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = ScriptBlockMain.MODID, version = ScriptBlockMain.VERSION, modLanguageAdapter = KotlinAdapter.className)
class ScriptBlockMain {
    companion object {
        const val MODID = "scriptblock"
        const val VERSION = "1.0"

        val LOGGER: Logger = LogManager.getLogger(MODID)

        val ScripBlock: Block = ScriptBlock()
        val RecordingPage: Item = RecordingPage()
        val CopyPage: Item = CopyPage()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        GameRegistry.registerTileEntity(ScriptBlockTile::class.java, "scriptblock_tile")
        readFromJson()
    }

    @Mod.EventHandler
    fun serverinit(event: FMLServerStartingEvent) {
        event.registerServerCommand(CommandScript())
    }
}