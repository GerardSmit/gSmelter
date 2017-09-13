package pw.gerard.gsmelter.tree.leaf

import com.runemate.game.api.script.framework.tree.LeafTask

class MessageLeaf(val message: String): LeafTask() {
    override fun execute() {
        println(message)
    }
}