package pw.gerard.gsmelter.tree.branch.inventory

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import pw.gerard.gsmelter.extension.countItems
import pw.gerard.gsmelter.tree.branch.BaseBranch

class ItemsInInventoryBranch(val items: Array<String>) : BaseBranch() {
    override fun validate(): Boolean {
        for ((name, amount) in items.countItems()) {
            if (Inventory.getQuantity(name) < amount) {
                return false
            }
        }

        return true
    }
}