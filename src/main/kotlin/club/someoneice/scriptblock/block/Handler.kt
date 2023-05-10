package club.someoneice.scriptblock.block

import club.someoneice.scriptblock.util.*
import com.google.common.collect.Maps
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import java.util.*

open class Handler(val world: World, val pos: BlockPos, val player: EntityPlayer, oStr: String) {
    private val oList: List<String> = oStr.asList()

    /**
     * The pool for storage variable.
     * */
    private val pool: HashMap<String, CacheObject> = Maps.newHashMap()

    /* Join In */
    init {
        for (l in oList) handle(l.asList())
    }

    /* Main Handle */
    protected fun handle(list: List<String>) {
        when (list[0]) {
            "@Set" -> pool[list[1]] = variable(list[2])
            "@Del" -> pool.remove(list[1])
            "@If"  -> processIf(list)
            "@Command" -> "" // Todo - Minecraft's command and replace.

            else -> if(SbInput.INPUT_MAP.isNotEmpty()) SbInput.INPUT_MAP[list[0]]?.getList(list)
        }
        for (i in list.indices) if (list[i][0] == '[') handle(list[i].asList())
    }

    protected fun processIf(list: List<String>) {
        val v1 = variable(list[2]).get<String>()
        val v2 = variable(list[3]).get<String>()

        val go = when (list[1]) {
            "@Is"    -> v1 == v2
            "@IsNot" -> v1 != v2

            else     -> false
        }

        if (go) handle(list[4].asList())
        else if (list.size > 5) handle(list[5].asList())
    }

    protected fun variable(str: String): CacheObject {
        return when (str[0]) {
            '%' -> pool[str.replace("%", "")] ?: throw CannotProcessException("Cannot find ${str.replace("%", "")} in pool!")
            '[' -> math(str.asList())
            '&' -> asObject() // TODO

            else -> str.asObject()
        }
    }

    protected fun math(list: List<String>): CacheObject {
        val v1 = variable(list[1]).get<Double>() ?: throw CannotProcessException("${list[1]} not a number!")
        val v2 = variable(list[2]).get<Double>() ?: throw CannotProcessException("${list[2]} not a number!")

        return when (list[0]) {
            "@Add" -> v1 + v2
            "@Min" -> v1 - v2
            "@Mul" -> v1 * v2
            "@Exc" -> v1 / v2

            else -> v1 + v2
        }.asObject()
    }

    protected fun event(list: List<String>) {
        TODO("The Minecraft's Event.")
    }
}