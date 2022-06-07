package uz.gita.justcontact.contact.ui.screen.contact

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.justcontact.R
import uz.gita.justcontact.contact.data.model.request.contact.AddContactRequest
import uz.gita.justcontact.contact.data.model.request.contact.EditContactRequest
import uz.gita.justcontact.contact.data.model.response.contact.GetAllContactResponse
import uz.gita.justcontact.contact.presentation.contact.ContactViewModel
import uz.gita.justcontact.contact.presentation.contact.MainScreenViewModel
import uz.gita.justcontact.contact.ui.adapter.ContactAdapter
import uz.gita.justcontact.contact.ui.dialog.AddContactDialog
import uz.gita.justcontact.contact.ui.dialog.EditDeleteListener
import uz.gita.justcontact.contact.ui.dialog.MyListener
import uz.gita.justcontact.contact.util.showToast
import uz.gita.justcontact.databinding.FragmentHomeBinding

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val contactViewModel by viewModels<ContactViewModel>()
    private val logViewModel by viewModels<MainScreenViewModel>()

              /////// listenerlar  \\\\\\
    val adapter = ContactAdapter(object : EditDeleteListener {
        override fun onEdit(contact: EditContactRequest) {
            contactViewModel.editContact(contact)
        }

        override fun onDelete(id: Int) {
            contactViewModel.deleteContact(id)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // super.onViewCreated(view, savedInstanceState)
        binding.homeProgressBar.hide()

        clicks()
        setAdapter()
        setObservers()
        contactViewModel.getContactCounted()
        contactViewModel.getAllContact()
    }

    fun refresh(){
        contactViewModel.getContactCounted()
        contactViewModel.getAllContact()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setObservers() {
        contactViewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        contactViewModel.errorLiveData.observe(viewLifecycleOwner, errorContactObserver)
        contactViewModel.notConnectionLiveData.observe(viewLifecycleOwner, notConnectionObserver)
        contactViewModel.countLiveData.observe(viewLifecycleOwner, countContactObserver)
        contactViewModel.allContactListLiveData.observe(viewLifecycleOwner, contactAllListLiveData)
        contactViewModel.addContactLiveData.observe(viewLifecycleOwner, addContactObserver)
        contactViewModel.editContactLiveData.observe(viewLifecycleOwner, editContactObserver)
        contactViewModel.deleteContactLiveData.observe(viewLifecycleOwner, deleteContactObserver)

        logViewModel.logOutSuccessLiveData.observe(this, logOutSuccessObserver)

    }

    private fun setAdapter() {
        binding.listRecycle.adapter = adapter
        binding.listRecycle.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun clicks() {
        binding.buttonAdd2.setOnClickListener {
            val listener = object : MyListener {
                override fun onAddContact(data: AddContactRequest) {
                    contactViewModel.addContact(data)
                }
            }
            val addContactObject = AddContactDialog(listener)
            addContactObject.addDialog(requireContext())
        }

        binding.swipe.setOnRefreshListener {
            refresh()
        }

        binding.logOutButton.setOnClickListener {
            logViewModel.userLogOut()
        }
    }


    private val errorContactObserver = Observer<String> {
        if (it.equals("Xatolik: User topilmadi yoki token eskirgan")) {
            showToast("Qayta logIn qiling!")
            logViewModel.userLogOut()
        }
    }

    private val countContactObserver = Observer<Int> {
        binding.countTV.text = it.toString()
    }

    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) binding.homeProgressBar.show()
        else binding.homeProgressBar.hide()
    }

    private val contactAllListLiveData = Observer<List<GetAllContactResponse>> {
        adapter.submitList(it)
    }

    private val addContactObserver = Observer<Unit> {
        Toast.makeText(requireContext(), "Contact added!", Toast.LENGTH_SHORT).show()
        refresh()
    }


    private val editContactObserver = Observer<Unit> {
        Toast.makeText(requireContext(), "Contact edited", Toast.LENGTH_SHORT).show()
        refresh()
    }

    private val deleteContactObserver = Observer<Unit> {
        Toast.makeText(requireContext(), "Contact deleted!", Toast.LENGTH_SHORT).show()
        refresh()
    }

    private val logOutSuccessObserver = Observer<Unit> {
        showToast("Successful loged out!")
        findNavController().navigate(MainFragmentDirections.actionHomeFragmentToLoginFragment())
    }



}
