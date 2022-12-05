package day05


interface CrateMover {
    fun take(list: List<Char>, quantity: Int): List<Char>
}

class CrateMover9000 : CrateMover {

    override fun take(list: List<Char>, quantity: Int): List<Char> =
        list.take(quantity).reversed()
}

class CrateMover9001 : CrateMover {

    override fun take(list: List<Char>, quantity: Int): List<Char> =
        list.take(quantity)
}
