package com.jn.capstoneproject.d_jahit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.databinding.FragmentCreateSellerBinding
import com.jn.capstoneproject.d_jahit.viewmodel.CreateSellerViewmodel

class CreateSellerFragment : Fragment() {
    private lateinit var navbar: BottomNavigationView
    private var _binding: FragmentCreateSellerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateSellerViewmodel by viewModels {
        ViewModelFactory(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentCreateSellerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar = requireActivity().findViewById(R.id.nav_view)
        navbar.visibility = View.GONE
        binding.apply {
            btnSave.setOnClickListener {
                val namaSeller = edtNamaToko.text.toString()
                val provinsi= edtProvinsi.text.toString()
                val city = edtCity.text.toString()
                val street= edtStreet.text.toString()
                val detail = edtDetail.text.toString()
                val nama=edtFullname.text.toString()
                val number=edtPhoneNumber.text.toString()
                viewModel.createSeller(namaSeller,provinsi,city,street,detail,nama,number, object :ApiCallbackString{
                    override fun onResponse(success: Boolean, message: String) {
                        onSuccess(success,message)
                    }
                })
            }

        }

    }

    private fun onSuccess(param: Boolean, message: String) {
        if (param){
            Toast.makeText(requireActivity(),message, Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireActivity(),message, Toast.LENGTH_SHORT).show()
        }
    }


}