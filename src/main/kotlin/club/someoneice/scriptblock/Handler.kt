package club.someoneice.scriptblock

import com.google.common.collect.Maps
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import io.netty.buffer.ByteBuf
import net.minecraft.command.ICommandSender
import net.minecraft.command.server.CommandBlockLogic
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ChunkCoordinates
import net.minecraft.world.World
import java.util.*

open class Handler(private val world: World, private val pos: ChunkCoordinates, private val player: EntityPlayer, oStr: String) {
    /**
     * A magic variable for this pool.
     * */
    val MAGIC_MAP: HashMap<String, String> = Maps.newHashMap()

    /**
     * The pool for storage variable.
     * */
    private val pool: HashMap<String, CacheObject> = Maps.newHashMap()

    init {
        cup()
        for (l in oStr.asList()) handle(l as List<Any>)
    }

    private fun cup() {
        MAGIC_MAP["%Player"]    = player.displayName
        MAGIC_MAP["%World"]     = world.provider.dimensionId.toString()
        MAGIC_MAP["%Pos"]       = "${pos.posX} ${pos.posY} ${pos.posZ}"
        MAGIC_MAP["%PlayerPos"] = "${player.posX} ${player.posY} ${player.posZ}"
    }

    /* Main Handle */
    private fun handle(list: List<Any>) {
        when (list[0].toString()) {
            "@Set" -> pool[list[1].toString()] = variable(list[2])
            "@Del" -> pool.remove(list[1].toString())
            "@If"  -> processIf(list)
            "@Command" -> commandHandle(list as List<String>)

            else -> if(SbInput.INPUT_MAP.isNotEmpty()) SbInput.INPUT_MAP[list[0].toString()]?.getList(list)
        }
        for (i in list.indices) if (list[i].toString()[0] == '[') handle(list[i] as List<Any>)
    }

    protected open fun processIf(list: List<Any>) {
        val v1 = variable(list[2]).get<String>()
        val v2 = variable(list[3]).get<String>()

        val go = when (list[1].toString()) {
            "@Is"    -> v1 == v2
            "@IsNot" -> v1 != v2

            else     -> false
        }

        if (go) handle(list[4] as List<Any>)
        else if (list.size > 5) handle(list[5] as List<Any>)
    }

    protected open fun variable(str: Any): CacheObject
    = when (str.toString()[0]) {
        '%' -> pool[str.toString().replace("%", "")] ?: throw CannotProcessException("Cannot find ${str.toString().replace("%", "")} in pool!")
        '[' -> math(str as List<Any>)
        '&' -> event(str.toString())

        else -> str.toString().asObject()
    }

    protected open fun math(list: List<Any>): CacheObject {
        val v1 = (variable(list[1].toString()).get<String>())?.toDouble() ?: throw CannotProcessException("Math get a string but it not a number!")
        val v2 = (variable(list[2].toString()).get<String>())?.toDouble() ?: throw CannotProcessException("Math get a string but it not a number!")

        return when (list[0]) {
            "@Add" -> v1 + v2
            "@Min" -> v1 - v2
            "@Mul" -> v1 * v2
            "@Exc" -> v1 / v2

            else -> v1 + v2
        }.toString().asObject()
    }


    protected open fun event(str: String): CacheObject
    = when(str) {
        "&Player"   -> player.displayName
        "&World"    -> world.provider.dimensionName
        "&Health"   -> player.health
        "&Hunger"   -> player.foodStats.foodLevel
        "&Item"     -> player.heldItem.item.stack().displayName
        "&Lv"       -> player.experienceLevel
        "&Random"   -> Random().nextInt(100)

        else        -> throw CannotProcessException("$str not a valid name!")
    }.asObject()

    /**
     * What should we do in command?
     * - First check all variables in command's String
     * - Then lets us run the command.
     * */
    protected open fun commandHandle(command: List<String>) {
        // Server only!
        if (world.isRemote) return

        val sender: ICommandSender = object : CommandBlockLogic() {
            override fun getPlayerCoordinates(): ChunkCoordinates {
                return pos
            }

            override fun getEntityWorld(): World {
                return world
            }

            override fun func_145756_e() {}

            @SideOnly(Side.CLIENT)
            override fun func_145751_f(): Int {
                return 0
            }

            @SideOnly(Side.CLIENT)
            override fun func_145757_a(buf: ByteBuf) {
                buf.writeInt(pos.posX)
                buf.writeInt(pos.posY)
                buf.writeInt(pos.posZ)
            }
        }

        var cmd = ""

        for (i in command) {
            if (i[0] == '@') continue
            if (i[0] == '%') {
                if (MAGIC_MAP.containsKey(i)) {
                    cmd += "${MAGIC_MAP[i]} "
                    continue
                }

                if (pool.containsKey(i.replace("%", ""))) {
                    cmd += "${pool[i.replace("%", "")]!!.get<Any>().toString()} "
                    continue
                }
            }

            cmd += "$i "
        }

        MinecraftServer.getServer().commandManager.executeCommand(sender, cmd)
    }
}