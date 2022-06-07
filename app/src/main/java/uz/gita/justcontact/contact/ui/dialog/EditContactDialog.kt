package uz.gita.justcontact.contact.ui.dialog

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest

class EditContactDialog(private val listener: EditDeleteListener) {


    fun editDialog(context: Context, dataItem: EditContactRequest) {

        var editContactDialog = Dialog(context, R.style.DialogStyle)
        /*
        *   <style name="DialogStyle" parent="Theme.AppCompat.Dialog">
            <item name="android:windowMinWidthMajor">100%</item>
            <item name="android:windowMinWidthMinor">100%</item>
            </style>
        * */
        editContactDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        editContactDialog.setCancelable(true)
        editContactDialog.setContentView(R.layout.custom_edit_dialog)

        val editName_editText: EditText = editContactDialog.findViewById(R.id.editName_editText)
        val editLastName_editText: EditText = editContactDialog.findViewById(R.id.editLastName_editText)
        val editNumber_editText: EditText = editContactDialog.findViewById(R.id.editNumber_editText)
        val button_save: Button = editContactDialog.findViewById(R.id.button_save)
        val cancel_edit: Button = editContactDialog.findViewById(R.id.cancel_edit)

        editName_editText.setText(dataItem.firstName)
        editLastName_editText.setText(dataItem.lastName)
        editNumber_editText.setText(dataItem.phone.substring(4,13))

        button_save.isEnabled = false
        editName_editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    button_save.isEnabled = true
                }

                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        button_save.isEnabled = true
                    }
                }
            })

        editLastName_editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                button_save.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    button_save.isEnabled = true
                }
            }
        })

        editNumber_editText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    button_save.isEnabled = true
                }

                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        button_save.isEnabled = true
                    }
                }
            }
        )

        editNumber_editText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 13) {
                    editNumber_editText.setError("Type more!")
                }
            }
        }

        button_save.setOnClickListener {
            if (editName_editText.text.equals("") && editLastName_editText.text.equals("") ) {
                Toast.makeText(context, "Enter something", Toast.LENGTH_SHORT).show()
            } else if (editNumber_editText.text.length != 9) {
                Toast.makeText(context, "13 digit number required", Toast.LENGTH_SHORT).show()
            } else {
                val nameContact = editName_editText.text.toString()
                val lastNameContact = editLastName_editText.text.toString()
                val numberContact = "+998" + editNumber_editText.text.toString()

                val newContactData = EditContactRequest(nameContact, lastNameContact, numberContact, dataItem.id)
//                println("numberContact = ${numberContact}")

                listener.onEdit(newContactData)
                editContactDialog.dismiss()
            }
        }





        cancel_edit.setOnClickListener {
            editContactDialog.dismiss()
        }
//        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.60).toInt()
//        addContactDialog.window!!.setLayout(width, height)
        editContactDialog.show()
    }
}