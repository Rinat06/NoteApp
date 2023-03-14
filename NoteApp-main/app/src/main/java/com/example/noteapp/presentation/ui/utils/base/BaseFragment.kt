package com.example.noteapp.presentation.ui.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.noteapp.presentation.ui.utils.UIState
import com.example.noteapp.presentation.ui.utils.showToast
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment<
        VBinding : ViewBinding,
        ViewModel :
        BaseViewModel,
        >(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected abstract val viewModel: ViewModel

    protected abstract val binding: VBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        initRequests()
        initSubscribers()
        initListener()
    }

    open fun initialize() {}

    open fun initRequests() {}

    open fun initSubscribers() {}

    open fun initListener() {}

    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        uiState: ((UIState<T>) -> Unit)? = null,
        onLoading: () -> Unit,
        onSuccess: (data: T) -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectUIState.collect { state ->
                    uiState?.invoke(state)
                    when (state) {
                        is UIState.Empty -> {}
                        is UIState.Error -> {
                            requireActivity().showToast(state.message)
                        }
                        is UIState.Loading -> {}
                        is UIState.Success -> {
                            onSuccess(state.data)
                        }
                    }
                }

            }
        }
    }
}



