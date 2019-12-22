package edu.isel.pdm.beegeesapp.bgg

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initModel()
        initTitle()
        initView()
        initBehavior(savedInstanceState)
    }

    // Setting Interface View
    protected abstract fun setContentView()

    // Initialization header
    protected abstract fun initTitle()


    // Initialization interface
    protected abstract fun initView()

    // Initialization data
    protected abstract fun initModel()

    // Behavior initialization
    protected abstract fun initBehavior(savedInstanceState: Bundle?)
}