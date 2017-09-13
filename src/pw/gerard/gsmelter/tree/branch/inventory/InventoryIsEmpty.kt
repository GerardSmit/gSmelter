package pw.gerard.gsmelter.tree.branch.inventory

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import pw.gerard.gsmelter.tree.branch.BaseBranch

class InventoryIsEmpty : BaseBranch() {
    override fun validate(): Boolean {
        return Inventory.isEmpty()
    }
}