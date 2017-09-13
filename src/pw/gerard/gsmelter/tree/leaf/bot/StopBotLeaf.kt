package pw.gerard.gsmelter.tree.leaf.bot

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.script.Execution
import com.runemate.game.api.script.framework.AbstractBot
import com.runemate.game.api.script.framework.tree.LeafTask

class StopBotLeaf(val bot: AbstractBot): LeafTask() {
    override fun execute() {
        // Close the bank
        Execution.delayWhile({
            var isOpen = false
            if (Bank.isOpen()) {
                isOpen = true
                Bank.close()
                Execution.delay(500, 1000)
            }
            isOpen
        })

        // Stop the bot
        bot.stop()
    }
}