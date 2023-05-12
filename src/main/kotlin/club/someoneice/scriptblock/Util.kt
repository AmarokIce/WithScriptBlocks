package club.someoneice.scriptblock

import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.shadew.json.Json
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets

private val gson: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
val CommandList: HashMap<String, String> = Maps.newHashMap()

fun readFromJson() {
    val file = File(System.getProperty("user.dir"), "ScriptBlockData")
    if (!file.isDirectory) {
        file.mkdirs()
        return
    }

    for (files in file.listFiles()!!) {
        if (!files.isFile) continue

        val type = file.name.substring(file.name.indexOf("."))
        if (type == ".json") {
            val bytes = ByteArray(files.length().toInt())
            val inputStream = FileInputStream(files)
            inputStream.read(bytes)
            inputStream.close()

            CommandList[files.nameWithoutExtension] = String(bytes, StandardCharsets.UTF_8)
        } else if (ScriptBlockMain.IS_PINEAPPLE_INSTALL && type == ".json5") {
            val json: Json = Json.json5()
            CommandList[files.nameWithoutExtension] = json.parse(files).asString()
        }
    }
}

fun Any.isList(): Boolean = this is List<*>
fun <E> String.asList(): E = gson.fromJson(this, object: TypeToken<E>() {}.type)
fun String.asList(): List<Any> = gson.fromJson(this, object: TypeToken<List<Any>>() {}.type)
fun Any.asObject(): CacheObject = CacheObject(this)
fun Item.stack(size: Int = 1): ItemStack = ItemStack(this, size)
fun Item.getRegisterName(): String = GameRegistry.findUniqueIdentifierFor(this).toString()
fun Block.getRegisterName(): String = GameRegistry.findUniqueIdentifierFor(this).toString()

class NotListException(message: String): Exception(message)
class CannotProcessException(message: String): Exception(message)
