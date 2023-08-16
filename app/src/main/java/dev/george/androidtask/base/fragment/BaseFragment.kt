package dev.george.androidtask.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {

    // private
    private var _contentId = 0
    private var _binding: T? = null

    // public
    val binding get() = _binding!!

    @Deprecated("Deprecated in Java")
    override fun onAttach(activity: Activity) {
        @Suppress("DEPRECATION")
        super.onAttach(activity)
        if (_contentId == 0) {
            _contentId = ActivityFragmentAnnoationManager.check(this)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = DataBindingUtil.inflate(inflater, _contentId, container, false)
        }
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        _binding?.apply {
            initialization()
            setListener()
            observers()
        }
    }

    protected abstract fun T.initialization()
    protected abstract fun T.setListener()
    protected open fun T.observers() {}

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// {SNACK_BAR} ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    fun showSnackBar(view: View, message: String) =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()



}