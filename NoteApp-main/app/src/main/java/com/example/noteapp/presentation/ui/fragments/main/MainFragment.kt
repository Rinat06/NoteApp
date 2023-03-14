package com.example.noteapp.presentation.ui.fragments.main

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentMainBinding
import com.example.noteapp.domain.model.Note
import com.example.noteapp.presentation.ui.fragments.main.adapter.MainAdapter
import com.example.noteapp.presentation.ui.utils.base.BaseFragment
import com.example.noteapp.presentation.ui.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    override val binding by viewBinding(FragmentMainBinding::bind)
    private val adapter by lazy { MainAdapter(this::onItemClick, this::onItemLongClick) }
    override fun initRequests() {
        super.initRequests()
        viewModel.getAllNotes()
    }

    override fun initSubscribers() {
        super.initSubscribers()
        collectGetAllNotes()
    }

    override fun initListener() {
        super.initListener()
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
        }
    }

    override fun initialize() {
        super.initialize()
        binding.rvNotes.adapter = adapter
    }

    private fun onItemClick(model: Note) {
        findNavController().navigate(R.id.addNoteFragment, bundleOf(KEY_UPDATE_NOTE to model))
    }

    private fun onItemLongClick(model: Note) {
        viewModel.removeNote(model)
        viewModel.removeNoteState.collectUIState(
            onLoading = {},
            onSuccess = {
                requireActivity().showToast(model.title + "has deleted")
                requireActivity().recreate()
            })
    }

    private fun collectGetAllNotes() {
        viewModel.getAllNotesState.collectUIState(
            onLoading = {},
            onSuccess = {
                adapter.addData(it)
            })
    }

    companion object {
        const val KEY_UPDATE_NOTE = "update.note"
    }
}