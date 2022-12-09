package day07

internal data class Dir(
    val path: String,
    val childrenPaths: MutableSet<String>,
    var size: Long
) {

    fun getFullSize(dirs: Set<Dir>): Long =
        size + childrenPaths.sumOf { child ->
            dirs.first { child == it.path }.getFullSize(dirs)
        }

    companion object {
        fun initialize(path: String): Dir =
            Dir(path, mutableSetOf(), 0)
    }
}
