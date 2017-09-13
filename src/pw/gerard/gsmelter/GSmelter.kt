package pw.gerard.gsmelter

import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.util.Resources
import com.runemate.game.api.script.framework.LoopingBot
import com.runemate.game.api.script.framework.tree.TreeTask
import com.sun.javafx.collections.ObservableListWrapper
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import pw.gerard.gsmelter.tree.branch.*
import pw.gerard.gsmelter.tree.branch.bank.BankIsOpenBranch
import pw.gerard.gsmelter.tree.branch.bank.ItemsInBankBranch
import pw.gerard.gsmelter.tree.branch.gameobject.ObjectIsNearbyBranch
import pw.gerard.gsmelter.tree.branch.inventory.InventoryIsEmpty
import pw.gerard.gsmelter.tree.branch.inventory.ItemsInInventoryBranch
import pw.gerard.gsmelter.tree.leaf.ExecuteLeaf
import pw.gerard.gsmelter.tree.leaf.bank.CloseBankLeaf
import pw.gerard.gsmelter.tree.leaf.bank.OpenBankLeaf
import pw.gerard.gsmelter.tree.leaf.bank.WithdrawItemsLeaf
import pw.gerard.gsmelter.tree.leaf.furnace.SmeltLeaf
import pw.gerard.gsmelter.tree.leaf.inventory.ClearInventoryLeaf
import pw.gerard.gsmelter.tree.leaf.pathing.WalkToAreaLeaf
import pw.gerard.gsmelter.ui.UiController
import java.util.*
import kotlin.collections.ArrayList

class GSmelter : LoopingBot(), EmbeddableUI {
    var tree: TreeTask? = null
    var botInterface: ObjectProperty<Node>? = null
    val actions = ObservableListWrapper(Collections.synchronizedList(ArrayList<Action>()))

    init {
        embeddableUI = this
    }

    override fun botInterfaceProperty(): ObjectProperty<out Node> {
        if (botInterface == null) {
            // Load the bot interface
            val controller = UiController(this)
            val loader = FXMLLoader()
            loader.setController(controller)
            botInterface = SimpleObjectProperty(loader.load(Resources.getAsStream("pw/gerard/gsmelter/ui.fxml")))
        }

        return botInterface as ObjectProperty<Node>
    }

    override fun onLoop() {
        if (tree == null) {
            return
        }

        var current: TreeTask = tree as TreeTask
        val builder = ArrayList<String>()

        while (!current.isLeaf) {
            val name = current::class.simpleName ?: "Unknown"
            val success = current.validate()
            val data = if (current is BaseBranch && current.lastData != null) " (${current.lastData})" else ""

            current = if (success) {
                builder.add("✓ $name$data")
                current.successTask() ?: throw RuntimeException("Expected success task to be valid")
            } else {
                builder.add("✕ $name$data")
                current.failureTask() ?: throw RuntimeException("Expected failure task to be valid")
            }
        }

        System.out.println("[${current::class.simpleName ?: "Unknown"}] (${builder.joinToString(" > ")})")
        current.execute()
    }

    fun createTree(action: Action): TreeTask {
        return ItemsInInventoryBranch(action.bar.input)
                .success(BankIsOpenBranch()
                        .success(CloseBankLeaf())
                        .failure(ObjectIsNearbyBranch("Furnace")
                                .success(SmeltLeaf(action.bar.name, action.bar.input))
                                .failure(WalkToAreaLeaf(action.location.furnace))))
                .failure(BankIsOpenBranch()
                        .success(InventoryIsEmpty()
                                .success(ItemsInBankBranch(action.bar.input)
                                        .success(WithdrawItemsLeaf(action.bar.input))
                                        .failure(ExecuteLeaf({
                                            // After we're done with the action, mark it as done and run the next action (if possible)
                                            action.done.set(true)
                                            action.active.set(false)

                                            val next = actions.firstOrNull { a -> !a.done.get() }

                                            if (next == null) {
                                                tree = null
                                                stop()
                                            } else {
                                                next.active.set(true)
                                                tree = createTree(next)
                                            }
                                        })))
                                .failure(ClearInventoryLeaf().setClearEvent { action.amount.set(action.amount.get() + Inventory.getQuantity(action.bar.output)) }))
                        .failure(ObjectIsNearbyBranch("Bank booth")
                                .success(OpenBankLeaf())
                                .failure(WalkToAreaLeaf(action.location.bank))))
    }
}