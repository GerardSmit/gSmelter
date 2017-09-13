package pw.gerard.gsmelter.tree.leaf.pathing

import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import com.runemate.game.api.script.framework.tree.LeafTask

class WalkToAreaLeaf(val area: Area) : LeafTask() {
    override fun execute() {
        RegionPath.buildTo(area.randomCoordinate)?.step(true)
        Execution.delayWhile({ Players.getLocal().isMoving }, 4000)
    }
}