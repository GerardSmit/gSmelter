package pw.gerard.gsmelter.tree.branch.bank

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import pw.gerard.gsmelter.extension.countItems
import pw.gerard.gsmelter.tree.branch.BaseBranch

class ItemsInBankBranch(val items: Array<String>) : BaseBranch() {
    override fun validate(): Boolean {
        for ((name, amount) in items.countItems()) {
            if (Bank.getQuantity(name) < amount) {
                return false
            }
        }

        return true
    }
}