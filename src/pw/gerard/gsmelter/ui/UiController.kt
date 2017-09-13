package pw.gerard.gsmelter.ui

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.StringConverter
import pw.gerard.gsmelter.Action
import pw.gerard.gsmelter.GSmelter
import pw.gerard.gsmelter.data.Output
import pw.gerard.gsmelter.data.Location
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell


class UiController(private val bot: GSmelter) {

    @FXML
    private var actionsColumnDone: TableColumn<Action, Boolean>? = null

    @FXML
    private var actionsColumnAmount: TableColumn<Action, SimpleIntegerProperty>? = null

    @FXML
    private var actionsColumnBar: TableColumn<Action, Output>? = null

    @FXML
    private var actionsColumnLocation: TableColumn<Action, Location>? = null

    @FXML
    private var actionsList: TableView<Action>? = null

    @FXML
    private var actionsBar: ComboBox<Output>? = null

    @FXML
    private var actionsLocation: ComboBox<Location>? = null

    @FXML
    private var actionsStart: Button? = null

    @FXML
    private var actionsAdd: Button? = null

    @FXML
    private var actionsRemove: Button? = null

    @FXML
    private var actionsStatus: Label? = null

    @FXML
    private fun initialize() {
        // Initialize actions
        actionsList?.items = bot.actions
        actionsList?.selectionModel?.selectedItemProperty()?.addListener { _, _, value -> actionsRemove?.isDisable = value == null }

        // Initialize columns
        actionsColumnAmount?.cellValueFactory = PropertyValueFactory<Action, SimpleIntegerProperty>("amount")
        actionsColumnLocation?.cellValueFactory = PropertyValueFactory<Action, Location>("location")
        actionsColumnBar?.cellValueFactory = PropertyValueFactory<Action, Output>("bar")
        actionsColumnDone?.cellValueFactory = PropertyValueFactory<Action, Boolean>("done")

        actionsColumnDone?.setCellFactory { CheckBoxTableCell<Action, Boolean>({ index -> actionsList!!.items[index].done }) }

        // Initialize bars
        actionsBar?.items = FXCollections.observableArrayList(Output.values().toList())
        actionsBar?.converter = object : StringConverter<Output>() {
            override fun fromString(name: String): Output {
                return Output.valueOf(name)
            }

            override fun toString(position: Output): String {
                return position.name
            }
        }

        // Initialize locations
        actionsLocation?.items = FXCollections.observableArrayList(Location.values().toList())
        actionsLocation?.converter = object : StringConverter<Location>() {
            override fun fromString(name: String): Location {
                return Location.values().first { loc -> loc.label == name }
            }

            override fun toString(location: Location): String {
                return location.label
            }
        }
    }

    fun startBot() {
        val next = bot.actions.firstOrNull { a -> !a.done.get() }

        if (next == null) {
            actionsStatus?.text = "Add at least 1 action"
            actionsStatus?.isVisible = true
        } else {
            actionsStart?.isDisable = true
            actionsAdd?.isDisable = true
            actionsRemove?.isDisable = true
            actionsLocation?.isDisable = true
            actionsBar?.isDisable = true
            actionsStatus?.isVisible = false

            next.active.set(true)
            bot.tree = bot.createTree(next)
        }
    }

    @FXML
    fun addAction() {
        val bar = actionsBar?.selectionModel?.selectedItem
        val location = actionsLocation?.selectionModel?.selectedItem

        if (bar != null && location != null) {
            bot.actions.add(Action(bar, location))
            actionsStatus?.isVisible = false
        } else {
            actionsStatus?.text = "Please select the bar and location"
            actionsStatus?.isVisible = true
        }
    }

    @FXML
    fun removeAction() {
        val item = actionsList?.selectionModel?.selectedItem

        if (item != null) {
            bot.actions.remove(item)
            actionsStatus?.isVisible = false
        }
    }
}

