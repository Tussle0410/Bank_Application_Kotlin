package com.example.bankApplication_kotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bankApplication_kotlin.R
import com.example.bankApplication_kotlin.databinding.RegisterEmailPageBinding
import com.example.bankApplication_kotlin.event.EventObserver
import com.example.bankApplication_kotlin.viewModel.RegisterEmailViewModel
import org.jetbrains.anko.startActivity

class RegisterEmailActivity : AppCompatActivity() {
    private val viewModel : RegisterEmailViewModel by lazy {
        ViewModelProvider(this).get(RegisterEmailViewModel::class.java)
    }
    private lateinit var binding: RegisterEmailPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.register_email_page)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //Back Button Click Observer
        viewModel.backEvent.observe(this,EventObserver{
            finish()
        })
        viewModel.nextEvent.observe(this,EventObserver{
            startActivity<RegisterInfoActivity>()
        })
    }
}