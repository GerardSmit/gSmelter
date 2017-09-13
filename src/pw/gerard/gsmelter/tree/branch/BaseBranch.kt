package pw.gerard.gsmelter.tree.branch

import com.runemate.game.api.script.framework.tree.BranchTask
import com.runemate.game.api.script.framework.tree.TreeTask
import pw.gerard.gsmelter.tree.leaf.MessageLeaf

abstract class BaseBranch : BranchTask() {
    private var successTask: TreeTask = MessageLeaf("Success task not implemented")
    private var failureTask: TreeTask = MessageLeaf("Failure task not implemented")
    var lastData: String? = null

    override fun failureTask(): TreeTask {
        return failureTask
    }

    override fun successTask(): TreeTask {
        return successTask
    }

    fun success(task: TreeTask): BaseBranch {
        successTask = task
        return this
    }

    fun failure(task: TreeTask): BaseBranch {
        failureTask = task
        return this
    }

}