package club.someoneice.scriptblock

import alexsocol.patcher.KotlinAdapter
import club.someoneice.scriptblock.block.ScriptBlockTile
import club.someoneice.scriptblock.init.BlockInit
import club.someoneice.scriptblock.init.ItemInit
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import cpw.mods.fml.common.registry.GameRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = ScriptBlockMain.MODID, version = ScriptBlockMain.VERSION, modLanguageAdapter = KotlinAdapter.className)
class ScriptBlockMain {
    companion object {
        const val MODID = "scriptblock"
        const val VERSION = "1.0"

        val LOGGER: Logger = LogManager.getLogger(MODID)

        val IS_PINEAPPLE_INSTALL = Loader.isModLoaded("pineapple_psychic")
    }

    @Mod.EventHandler
    fun commonInit(event: FMLInitializationEvent) {
        readFromJson()
        ItemInit
        BlockInit
        GameRegistry.registerTileEntity(ScriptBlockTile::class.java, "scriptblock")
    }

    @Mod.EventHandler
    fun serverInit(event: FMLServerStartingEvent) {
        event.registerServerCommand(CommandScript())
    }
}