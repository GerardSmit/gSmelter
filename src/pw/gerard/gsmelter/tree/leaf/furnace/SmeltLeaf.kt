package pw.gerard.gsmelter.tree.leaf.furnace

import com.runemate.game.api.hybrid.input.Keyboard
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.script.framework.tree.LeafTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import pw.gerard.gsmelter.extension.maxAmountInInventory

class SmeltLeaf(val name: String, val inputs: Array<String>): LeafTask() {
    override fun execute() {
        val amount = inputs.maxAmountInInventory()

        if (amount == 0) {
            throw RuntimeException("Cannot make zero $name")
        }

        val amountInterface = Interfaces.newQuery().textContains("Enter amount:").results().first()

        if (amountInterface != null && amountInterface.isVisible) {
            val delay = 3200 * amount

            if (Players.getLocal().animationId != 899) {
                Execution.delay((1000 + Math.random() * 1000).toLong())
                Keyboard.type(amount.toString(), true)
                println("Smelting $amount $name in ${delay / 1000}s")
            }

            Execution.delayWhile({ ChatDialog.getContinue() == null }, delay, delay + 3000)
        } else {
            val input = Interfaces.newQuery().textContains(name).results().first()

            if (input != null && input.isVisible) {
                input.interact("Smelt X $name")
                Execution.delay(500, 1000)
                Execution.delayWhile({ Interfaces.newQuery().textContains("Enter amount:").results().isEmpty() }, 1000)
            } else {
                val gameObject = GameObjects.newQuery().names("Furnace").results().first() ?: throw RuntimeException("Could not find the furnace")

                if (!gameObject.isVisible) {
                    Camera.turnTo(gameObject)
                }

                gameObject.interact("Smelt")
                Execution.delay(500, 1000)
            }
        }
    }
}