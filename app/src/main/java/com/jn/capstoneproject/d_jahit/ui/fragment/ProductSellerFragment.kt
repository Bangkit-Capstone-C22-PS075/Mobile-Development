package com.jn.capstoneproject.d_jahit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.SessionManager
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.adapter.ListProductSellerAdapter
import com.jn.capstoneproject.d_jahit.databinding.FragmentProductSellerBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.viewmodel.ProductSellerViewModel


class ProductSellerFragment : Fragment() {
    private var _binding: FragmentProductSellerBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var productAdapter: ListProductSellerAdapter
    private val args: ProductSellerFragmentArgs by navArgs()
    private val viewModel: ProductSellerViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    private lateinit var navbar: BottomNavigationView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentProductSellerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar=requireActivity().findViewById(R.id.nav_view)
        navbar.visibility=View.GONE
        val selleId=args.data
        val name=args.name
        val userId=args.userId
        binding.back.text=name
        productAdapter= ListProductSellerAdapter()
        binding.rvProduct.apply {
            adapter = productAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        viewModel.getAllProduct(selleId,object : ApiCallbackString {
            override fun onResponse(success: Boolean, message: String) {
                onSucces()
            }
        })
        viewModel.getProduct.observe(requireActivity()) { listProduct ->
            showData(listProduct)
        }
        productAdapter.setOnItemClickCallback(object : ListProductSellerAdapter.OnItemClickCallback {
            override fun onItemClicked(story: ProductsItem) {
                findNavController().navigate(
                    ProductSellerFragmentDirections.actionProductSellerFragmentToAddProductFragment(
                        selleId,story.id
                    )
                )

            }
        })
        binding.btnEditSeller.setOnClickListener {
            findNavController().navigate(
                ProductSellerFragmentDirections.actionProductSellerFragmentToCreateStroreFragment(
                    userId
                )
            )
        }


    }

    private fun showData(data: List<ProductsItem>) {
        binding.rvProduct.apply {
            setHasFixedSize(true)
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        }
        productAdapter.setAllData(data)



    }

    private fun onSucces() {

    }



}