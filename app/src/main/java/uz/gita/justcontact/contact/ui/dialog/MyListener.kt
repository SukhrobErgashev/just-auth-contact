package uz.gita.justcontact.contact.ui.dialog

import uz.gita.justcontact.contact.data.model.request.contact.AddContactRequest

interface MyListener {

    fun onAddContact(data: AddContactRequest)
}

