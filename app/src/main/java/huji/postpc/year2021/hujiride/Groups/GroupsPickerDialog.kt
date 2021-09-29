package huji.postpc.year2021.hujiride.Groups

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import huji.postpc.year2021.hujiride.HujiRideApplication

class GroupsPickerDialog(
    val groups: List<String>,
    private val currentSelectedID: String,
    private val onSelect: ((String?) -> Unit)?
) : DialogFragment() {
    companion object {
        const val NOT_FROM_GROUP = "All Groups"
    }

    private val groupscs = groups.map { s -> s.subSequence(0, s.length) }.toTypedArray()
    val app: HujiRideApplication = HujiRideApplication.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val selectedIndex = groups.indexOf(getGroupsName(currentSelectedID)?: NOT_FROM_GROUP)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Choose Group")
                .setSingleChoiceItems(groupscs, selectedIndex) { dialog, which ->
                    val groupsName = groups[which]
                    val groupsId = getIdOfGroup(groupsName)
                    onSelect?.invoke(groupsId)
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getGroupsName(id: String): String? {
        return app.jerusalemNeighborhoods[id]
    }

    private fun getIdOfGroup(name: String): String? {
        for (g in app.jerusalemNeighborhoods) {
            if (g.value == name) {
                return g.key
            }
        }
        return null
    }
}