package day07

internal sealed class Command {
    class Cd(val dir: String) : Command()
    object Ls : Command()

    companion object {
        fun from(string: String): Command {
            val components = string.split(" ")

            return if (components[1] == "cd") {
                Cd(components[2])
            } else {
                Ls
            }
        }
    }
}
