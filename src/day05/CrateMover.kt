package day05


internal interface CrateMover {
    fun take(list: List<Char>, quantity: Int): List<Char>
}

internal class CrateMover9000 : CrateMover {

    override fun take(list: List<Char>, quantity: Int): List<Char> =
        list.take(quantity).reversed()
}

internal class CrateMover9001 : CrateMover {

    override fun take(list: List<Char>, quantity: Int): List<Char> =
        list.take(quantity)
}
