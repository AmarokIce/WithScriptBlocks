package club.someoneice.scriptblock

open class CacheObject(private var obj: Any) {
    fun getObject(): Any {
        return obj
    }

    fun <E> get(): E? {
        return obj as? E
    }
}