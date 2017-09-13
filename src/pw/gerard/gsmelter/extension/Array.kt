package pw.gerard.gsmelter.extension

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

fun <T> Array<T>.countItems(): Map<T, Int> {
    val inputAmounts = HashMap<T, Int>()
    for (input in this) {
        inputAmounts.put(input, inputAmounts.getOrDefault(input, 0) + 1)
    }
    return inputAmounts
}

fun Array<String>.maxAmountInBank(): Int {
    // The max amount of items that the inventory can hold
    var amount = Math.floor((Inventory.getEmptySlots() / size).toDouble()).toInt()

    // Get the max amount of inputs that the inventory can hold
    val inputAmounts = countItems()

    for ((key, value) in inputAmounts) {
        val quantity = Bank.getQuantity(key)

        if (quantity < value) {
            return 0
        }

        val maxInputAmount = quantity / value
        if (amount > maxInputAmount) {
            amount = maxInputAmount
        }
    }

    return amount
}

fun Array<String>.maxAmountInInventory(): Int {
    var first = true
    var amount = 0

    // Get the max amount of inputs that the inventory can hold
    val inputAmounts = countItems()

    for ((key, value) in inputAmounts) {
        val quantity = Inventory.getQuantity(key)

        if (quantity < value) {
            return 0
        }

        val maxInputAmount = quantity / value
        if (first || amount > maxInputAmount) {
            amount = maxInputAmount
            first = false
        }
    }

    return amount
}