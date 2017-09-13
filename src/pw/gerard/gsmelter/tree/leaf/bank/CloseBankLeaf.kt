package pw.gerard.gsmelter.tree.leaf.bank

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.script.framework.tree.LeafTask

class CloseBankLeaf: LeafTask() {
    override fun execute() {
        Bank.close()
    }
}