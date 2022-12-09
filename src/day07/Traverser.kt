package day07

import day07.Command.Cd

internal class Traverser(private val input: List<String>) {

    private val dirs = mutableSetOf<Dir>()
    private val absolutePath = mutableListOf<String>()
    private val absoluteTextPath get() = absolutePath.toString()

    fun extractDirs(): MutableSet<Dir> {
        dirs.clear()
        absolutePath.clear()

        input.forEach { line ->
            when {
                line.startsWith("$") -> handleCommand(line)
                line.startsWith("dir") -> handleDir(line)
                else -> handleFile(line)
            }
        }

        return dirs
    }

    private fun handleCommand(line: String) {
        val command = Command.from(line)
        if (command !is Cd) return

        if (command.dir == "..") {
            navigateUp()
        } else {
            navigateTo(command.dir)
            if (isFirstTimeInPath()) {
                addNewDirectory()
            }
        }
    }

    private fun handleDir(line: String) {
        val childPath = absolutePath + line.getDirName()
        getCurrentDir().childrenPaths.add(childPath.toString())
    }

    private fun handleFile(line: String) {
        getCurrentDir().size += line.getSize()
    }

    private fun getCurrentDir(): Dir =
        dirs.first { it.path == absoluteTextPath }

    private fun isFirstTimeInPath(): Boolean =
        dirs.none { it.path == absoluteTextPath }

    private fun addNewDirectory() =
        dirs.add(Dir.initialize(absoluteTextPath))

    private fun navigateTo(dir: String) =
        absolutePath.add(dir)

    private fun navigateUp() =
        absolutePath.removeLast()

    private fun String.getDirName(): String =
        split(" ")[1]

    private fun String.getSize(): Long =
        split(" ").first().toLong()
}
