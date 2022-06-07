package uz.gita.justcontact.contact.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.contact.AddContactRequest

class AddContactDialog(private val listener: MyListener) {

    lateinit var name_editText: EditText
    lateinit var last_name_editText: EditText
    lateinit var number_editText: EditText
    lateinit var button_add_new: Button
    lateinit var cancel_add: Button

    lateinit var addContactDialog: Dialog


    fun addDialog(context: Context) {
        addContactDialog = Dialog(context, R.style.DialogStyle)
        /*
        *   <style name="DialogStyle" parent="Theme.AppCompat.Dialog">
            <item name="android:windowMinWidthMajor">100%</item>
            <item name="android:windowMinWidthMinor">100%</item>
            </style>
        * */
        addContactDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        addContactDialog.setCancelable(true)
        addContactDialog.setContentView(R.layout.custom_add_dialog)

        name_editText = addContactDialog.findViewById(R.id.name_editText)
        last_name_editText = addContactDialog.findViewById(R.id.last_name_editText)
        number_editText = addContactDialog.findViewById(R.id.number_editText)
        button_add_new = addContactDialog.findViewById(R.id.button_add_new)
        cancel_add = addContactDialog.findViewById(R.id.cancel_add)

        number_editText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 9) {
                    number_editText.setError("Type more!")
                }
            }
        }

        button_add_new.setOnClickListener {
            if (name_editText.text.length<3 || last_name_editText.text.length<3) {
                Toast.makeText(context, "Enter letters at least 3 digit", Toast.LENGTH_SHORT).show()
            } else if (number_editText.text.length != 9) {
                Toast.makeText(context, "13 digit number required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val nameContact = name_editText.text.toString()
                val lastNameContact = last_name_editText.text.toString()
                val numberContact = "+998" + number_editText.text.toString()
                val newContactData = AddContactRequest(nameContact, lastNameContact, numberContact)
//                println("numberContact = ${numberContact}")


                listener.onAddContact(newContactData)
                addContactDialog.dismiss()
            }
        }

        cancel_add.setOnClickListener {
            addContactDialog.dismiss()

//            addContactDialog.setContentView(addDialogView)
        }
//        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.60).toInt()
//        addContactDialog.window!!.setLayout(width, height)
        addContactDialog.show()

    }
}

