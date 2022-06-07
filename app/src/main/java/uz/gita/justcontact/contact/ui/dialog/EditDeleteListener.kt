package uz.gita.justcontact.contact.ui.dialog

import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest

interface EditDeleteListener {

    fun onEdit(contact: EditContactRequest)

    fun onDelete(id: Int)

}