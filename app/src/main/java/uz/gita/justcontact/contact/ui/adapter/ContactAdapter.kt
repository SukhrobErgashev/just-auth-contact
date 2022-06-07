package uz.gita.justcontact.contact.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest
import uz.gita.justcontact.contact.data.model.response.contact.GetAllContactResponse
import uz.gita.justcontact.contact.ui.dialog.EditContactDialog
import uz.gita.justcontact.contact.ui.dialog.EditDeleteListener

class ContactAdapter(private val listener: EditDeleteListener) :
    ListAdapter<GetAllContactResponse, ContactAdapter.ContactViewHolder>(DiffContact) {


    private var editContactListener: ((GetAllContactResponse) -> Unit)? = null
    private var deleteContactListener: ((Int) -> Unit)? = null


    inner class ContactViewHolder(view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {

        private val contactName: TextView = itemView.findViewById(R.id.textUserName)
        private val contactNumber: TextView = itemView.findViewById(R.id.textUserPhoneNumber)
        private val editButton: Button = itemView.findViewById(R.id.buttonEdit)
        private val deleteButton: Button = itemView.findViewById(R.id.buttonDelete)


        init {
            editButton.setOnClickListener { v ->
                editContactListener?.invoke(getItem(absoluteAdapterPosition))
            }
            deleteButton.setOnClickListener {
                deleteContactListener?.invoke(getItem(absoluteAdapterPosition).id)
            }
        }


        fun setEditContactListener(item: (GetAllContactResponse) -> Unit) {    // (item : GetAllContactResponse): Unit { qilsak GetAllContactResponseda invoke va
            editContactListener = item
        }

        fun setDeleteContactListener(itemId: (Int) -> Unit) {
            deleteContactListener = itemId
        }

        fun bind() {
            val item: GetAllContactResponse = getItem(absoluteAdapterPosition)
            contactName.text = item.firstName + " " + item.lastName
            contactNumber.text = item.phone

            editButton.setOnClickListener {
                val editDialog: EditContactDialog = EditContactDialog(listener)
                editDialog.editDialog(
                    context,
                    EditContactRequest(item.firstName, item.lastName, item.phone, item.id)
                )
            }

            deleteButton.setOnClickListener {
                listener.onDelete(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false),
            context = parent.context
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind()
    }

    object DiffContact : DiffUtil.ItemCallback<GetAllContactResponse>() {
        override fun areItemsTheSame(
            oldItem: GetAllContactResponse,
            newItem: GetAllContactResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetAllContactResponse,
            newItem: GetAllContactResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

}