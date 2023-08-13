package dev.george.androidtask.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import androidx.appcompat.app.AppCompatActivity
import dev.george.androidtask.R

@Suppress("PropertyName")
abstract class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    abstract val TAG : String
    val binding: B by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Androidtask)
        beforeCreateView()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialization()
        setListener()
    }

    abstract fun beforeCreateView()
    abstract fun initialization()
    abstract fun setListener()


}