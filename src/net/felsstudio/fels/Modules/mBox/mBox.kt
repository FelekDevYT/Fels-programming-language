package net.felsstudio.fels.Modules.mBox

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.lib.Functions
import net.felsstudio.fels.lib.NumberValue
import net.felsstudio.fels.lib.StringValue
import net.felsstudio.fels.lib.Value
import javax.swing.JOptionPane

class mBox : Module {
    override fun init() {
        Functions.set("showErrorMessage") { args: Array<Value> ->
            JOptionPane.showMessageDialog(null, args[0], args[1].toString(), JOptionPane.ERROR_MESSAGE)
            NumberValue.ZERO
        }
        Functions.set("showInformationMessage") { args: Array<Value> ->
            JOptionPane.showMessageDialog(null, args[0], args[1].toString(), JOptionPane.INFORMATION_MESSAGE)
            NumberValue.ZERO
        }
        Functions.set("showQuestionMessage") { args: Array<Value> ->
            JOptionPane.showMessageDialog(null, args[0], args[1].toString(), JOptionPane.QUESTION_MESSAGE)
            NumberValue.ZERO
        }
        Functions.set("showWarningMessage") { args: Array<Value> ->
            JOptionPane.showMessageDialog(null, args[0], args[1].toString(), JOptionPane.WARNING_MESSAGE)
            NumberValue.ZERO
        }
        Functions.set("showPlanMessage") { args: Array<Value> ->
            JOptionPane.showMessageDialog(null, args[0], args[1].toString(), JOptionPane.PLAIN_MESSAGE)
            NumberValue.ZERO
        }
        Functions.set("showInputDialog") { args: Array<Value> ->
            StringValue(
                JOptionPane.showInputDialog(
                    null,
                    args[0],
                    args[1].toString(),
                    JOptionPane.QUESTION_MESSAGE
                )
            )
        }
    }
}
