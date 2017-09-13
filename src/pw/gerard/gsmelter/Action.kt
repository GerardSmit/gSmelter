package pw.gerard.gsmelter

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import pw.gerard.gsmelter.data.Output
import pw.gerard.gsmelter.data.Location

class Action(val bar: Output, val location: Location) {
    val amount = SimpleIntegerProperty(0)
    val done = SimpleBooleanProperty(false)
    val active = SimpleBooleanProperty(false)

    fun amountProperty(): SimpleIntegerProperty {
        return amount
    }

    fun activeProperty(): SimpleIntegerProperty {
        return amount
    }

    fun doneProperty(): SimpleBooleanProperty {
        return done
    }
}