package pw.gerard.gsmelter.tree.leaf.bank

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.script.framework.tree.LeafTask
import pw.gerard.gsmelter.extension.countItems
import pw.gerard.gsmelter.extension.maxAmountInBank

class WithdrawItemsLeaf(val items: Array<String>): LeafTask() {
    override fun execute() {
        val amount = items.maxAmountInBank()

        if (amount == 0) {
            throw RuntimeException("Expected to be at least one item to be withdrawn")
        }

        for ((name, value) in items.countItems()) {
            val withdrawAmount = amount * value

            while (Inventory.getQuantity(name) < withdrawAmount) {
                Bank.withdraw(name, withdrawAmount - Inventory.getQuantity(name))
            }

            println("Withdrawing $withdrawAmount $name")
        }
    }
}