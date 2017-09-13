package pw.gerard.gsmelter.tree.branch.bank

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import pw.gerard.gsmelter.tree.branch.BaseBranch

class BankIsOpenBranch : BaseBranch() {
    override fun validate(): Boolean {
        return Bank.isOpen()
    }
}