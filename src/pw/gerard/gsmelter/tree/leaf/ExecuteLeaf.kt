package pw.gerard.gsmelter.tree.leaf

import com.runemate.game.api.script.framework.tree.LeafTask

class ExecuteLeaf(val callback: () -> Any): LeafTask() {
    override fun execute() {
        callback.invoke()
    }
}