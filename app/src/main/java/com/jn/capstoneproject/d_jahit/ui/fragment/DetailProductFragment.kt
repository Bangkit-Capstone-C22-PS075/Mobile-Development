package com.jn.capstoneproject.d_jahit.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.adapter.ComentAdapter
import com.jn.capstoneproject.d_jahit.adapter.ListProductAdapter
import com.jn.capstoneproject.d_jahit.adapter.ListProducthorizontalAdapter
import com.jn.capstoneproject.d_jahit.databinding.FragmentDetailProductBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.viewmodel.HomeViewModel


class DetailProductFragment : Fragment() {
    private var _binding: FragmentDetailProductBinding?= null
    private val binding get() = _binding!!
    private val args: DetailProductFragmentArgs by navArgs()
    private lateinit var productAdapter: ListProducthorizontalAdapter
    private lateinit var comentAdapter: ComentAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private lateinit var navbar: BottomNavigationView


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
        navbar=requireActivity().findViewById(R.id.nav_view)
        navbar.visibility=View.GONE

        val data=args.data
        binding.apply {
            tvNameProduk.text=data.name
            tvDescriptionProduct.text= data.definition
            Glide.with(requireContext())
                .load(data.productPhoto)
                .into(imgProduct)
        }
        productAdapter = ListProducthorizontalAdapter()
        binding.rvProduct.apply {
            adapter = productAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        comentAdapter= ComentAdapter()
        binding.rvComent.apply {
            adapter=comentAdapter
            setHasFixedSize(true)
            layoutManager=
                LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }

        viewModel.getAllProduct(object : ApiCallbackString{
            override fun onResponse(success: Boolean, message: String) {

            }
        })
        viewModel.getProduct.observe(requireActivity()) { listProduct ->
            showData(listProduct)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            navbar.visibility=View.VISIBLE
        }
        binding.btnPesan.setOnClickListener {
            showAlerDialog()
        }

    }

    private fun showAlerDialog() {
        val builder= AlertDialog.Builder(requireActivity())
        val  inflater= layoutInflater
        val dialogLayout =inflater.inflate(R.layout.pop_transaksi,null)
        val name=dialogLayout.findViewById<TextView>(R.id.tv_name)
        with(builder){
            setPositiveButton("Ok"){dialog, wich ->

            }
            setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showData(data: List<ProductsItem>) {


        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)

        }
        productAdapter.setAllData(data)

    }

}