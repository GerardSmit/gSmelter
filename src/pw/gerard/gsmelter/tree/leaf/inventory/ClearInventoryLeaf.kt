package pw.gerard.gsmelter.tree.leaf.inventory

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.script.framework.tree.LeafTask

class ClearInventoryLeaf: LeafTask() {
    private var clearEvent: () -> Any = {}

    fun setClearEvent(newClearEvent: () -> Any): ClearInventoryLeaf {
        clearEvent = newClearEvent
        return this
    }

    override fun execute() {
        if (!Bank.isOpen()) {
            throw RuntimeException("Expected the bank to be opened at this point")
        }

        clearEvent.invoke()
        Bank.depositInventory()
    }
}