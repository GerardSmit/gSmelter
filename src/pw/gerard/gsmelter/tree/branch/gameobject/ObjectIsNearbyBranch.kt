package pw.gerard.gsmelter.tree.branch.gameobject

import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import pw.gerard.gsmelter.tree.branch.BaseBranch

class ObjectIsNearbyBranch(val name: String, val distance: Double = 5.0) : BaseBranch() {
    override fun validate(): Boolean {
        val nearest = GameObjects.newQuery().names(name).results().nearest()

        if (nearest != null) {
            lastData = String.format("%.2fD", nearest.distanceTo(Players.getLocal()))
        }

        return nearest != null && nearest.distanceTo(Players.getLocal()) < distance
    }
}