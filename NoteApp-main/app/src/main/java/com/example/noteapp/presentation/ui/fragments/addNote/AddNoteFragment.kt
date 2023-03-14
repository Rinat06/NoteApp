package com.example.noteapp.presentation.ui.fragments.addNote

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddNoteBinding
import com.example.noteapp.domain.model.Note
import com.example.noteapp.presentation.ui.fragments.main.MainFragment.Companion.KEY_UPDATE_NOTE
import com.example.noteapp.presentation.ui.utils.base.BaseFragment
import com.example.noteapp.presentation.ui.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding, AddNoteViewModel>(R.layout.fragment_add_note) {

    override val viewModel: AddNoteViewModel by viewModels()
    override val binding by viewBinding(FragmentAddNoteBinding::bind)
    private var note: Note? = null
    private var noteIsNull = true

    override fun initialize() {
        super.initialize()
        getNote()
    }

    override fun initRequests() {
        super.initRequests()
        addOrEdit()
    }

    override fun initSubscribers() {
        super.initSubscribers()
        collectAddNote()
        collectEditNote()
    }

    @SuppressLint("SetTextI18n")
    private fun getNote() {
        if (arguments?.getSerializable(KEY_UPDATE_NOTE) == null) {
            note = Note()
        } else {
            note = arguments?.getSerializable(KEY_UPDATE_NOTE) as Note
            binding.etTitle.setText(note!!.title)
            binding.etDescription.setText(note!!.description)
            binding.btnAdd.text = "Edit"
            noteIsNull = false
        }
    }

    private fun addOrEdit() {
        with(binding) {
            btnAdd.setOnClickListener {
                if (binding.etTitle.text.isNotEmpty() && binding.etDescription.text.isNotEmpty()) {
                    note?.title = etTitle.text.toString()
                    note?.description = etDescription.text.toString()
                    if (noteIsNull) {
                        viewModel.addNote(note!!)
                    } else {
                        viewModel.editNote(note!!)
                    }
                } else {
                    requireActivity().showToast("Заполните поля")
                }
            }
        }
    }

    private fun collectAddNote() {
        viewModel.addNoteState.collectUIState(
            onLoading = {},
            onSuccess = {
                findNavController().navigateUp()
            }
        )
    }

    private fun collectEditNote() {
        viewModel.editNoteState.collectUIState(
            onLoading = {},
            onSuccess = {
                findNavController().navigateUp()
            }
        )
    }
}