package club.someoneice.scriptblock

import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
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
        val bytes = ByteArray(files.length().toInt())
        val inputStream = FileInputStream(files)
        inputStream.read(bytes)
        inputStream.close()

        CommandList[files.nameWithoutExtension] = String(bytes, StandardCharsets.UTF_8)
    }
}

fun <T> String.asList(t: T): T = gson.fromJson(this, object: TypeToken<T>() {}.type)
fun String.asList(): List<String> = gson.fromJson(this, object: TypeToken<List<String>>() {}.type)
fun Any.asObject(): CacheObject = CacheObject(this)
fun Item.stack(): ItemStack = ItemStack(this)
class NotListException(message: String): Exception(message)
class CannotProcessException(message: String): Exception(message)