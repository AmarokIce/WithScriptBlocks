package club.someoneice.scriptblock

import alexsocol.patcher.KotlinAdapter
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = ScriptBlockMain.MODID, version = ScriptBlockMain.VERSION, modLanguageAdapter = KotlinAdapter.className)
class ScriptBlockMain {
    companion object {
        const val MODID = "scriptblock"
        const val VERSION = "1.0"

        val LOGGER: Logger = LogManager.getLogger(MODID)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        readFromJson()
    }

    @Mod.EventHandler
    fun serverinit(event: FMLServerStartingEvent) {
        event.registerServerCommand(CommandScript())
    }
}