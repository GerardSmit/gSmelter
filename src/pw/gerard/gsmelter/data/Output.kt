package pw.gerard.gsmelter.data

enum class Output(val output: String, val input: Array<String>) {
    SILVER("Silver bar", arrayOf("Silver ore")),
    IRON("Iron bar", arrayOf("Iron ore"))
}