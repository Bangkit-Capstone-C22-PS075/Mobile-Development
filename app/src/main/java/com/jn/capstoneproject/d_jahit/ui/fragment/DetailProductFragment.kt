package com.jn.capstoneproject.d_jahit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.jn.capstoneproject.d_jahit.databinding.FragmentDetailProductBinding


class DetailProductFragment : Fragment() {
    private var _binding: FragmentDetailProductBinding?= null
    private val binding get() = _binding!!
    private val args: DetailProductFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDetailProductBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data=args.data
        binding.apply {
            tvNameProduk.text=data.name
            tvDescriptionProduct.text= data.definition
        }
    }

}