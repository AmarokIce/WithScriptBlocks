package club.someoneice.scriptblock.util

import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets

private val gson: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
val CommandList: HashMap<String, String> = Maps.newHashMap();

fun readFromJson() {
    val file = File(System.getProperty("user.dir") + File.separator + "ScriptBlockData")
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

// If the object is a list, return true.
fun Any.isList() = this is List<*>

// Make String to Json.
fun <T> String.asList(t: T): T {
    return gson.fromJson(this, object: TypeToken<T>() {}.type)
}

// Make String to Json with no type.
fun String.asJsonWithAny(): Any {
    return gson.fromJson(this, object: TypeToken<Any>() {}.type)
}

// Make String(Data in command) to List.
fun String.asList(): List<String> {
    return gson.fromJson(this, object: TypeToken<List<String>>() {}.type)
}

fun String.initDataList(): List<List<String>> {
    return gson.fromJson(this, object: TypeToken<List<List<String>>>() {}.type)
}

fun Any.asObject(): CacheObject {
    return CacheObject(this)
}

fun Any.json() = gson

class NotListException(message: String): Exception(message)
class CannotProcessException(message: String): Exception(message)