package dev.george.androidtask.ui.main

import dagger.hilt.android.AndroidEntryPoint
import dev.george.androidtask.base.BaseActivity
import dev.george.androidtask.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override fun beforeCreateView() {}

    override fun initialization() {}

    override fun setListener() {}

}