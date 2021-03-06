package com.example.bankApplication_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bankApplication_kotlin.R
import com.example.bankApplication_kotlin.databinding.HomeMybankFragmentPageBinding

class HomeMyBankFragment : Fragment() {
    private lateinit var binding : HomeMybankFragmentPageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_mybank_fragment_page,container,false)
        return binding.root
    }
    companion object{
        fun getInstance() = HomeMyBankFragment()
    }
}