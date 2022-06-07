package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jn.capstoneproject.d_jahit.Constanta.CAMERA_X_RESULT
import com.jn.capstoneproject.d_jahit.Constanta.REQUEST_CODE_PERMISSIONS
import com.jn.capstoneproject.d_jahit.Constanta.REQUIRED_PERMISSIONS
import com.jn.capstoneproject.d_jahit.SessionManager
import com.jn.capstoneproject.d_jahit.Utils
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.adapter.ListProductAdapter
import com.jn.capstoneproject.d_jahit.databinding.FragmentHomeBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.ui.activity.CameraActivity
import com.jn.capstoneproject.d_jahit.ui.activity.DetailChat
import com.jn.capstoneproject.d_jahit.viewmodel.HomeViewModel
import com.jn.capstoneproject.d_jahit.viewmodel.LoginViewModel
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var productAdapter: ListProductAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private var currentFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkAllPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        productAdapter = ListProductAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.btnSearch.setOnClickListener {
            startCameraX()
        }
        sessionManager = SessionManager(requireActivity())
        productAdapter = ListProductAdapter()
        val userId = sessionManager.fetchAccessId()
        if (userId != null) {
//            binding.tvid.text = token
            binding.tvid.visibility = View.INVISIBLE

            binding.rvProduct.apply {
                adapter = productAdapter
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
            viewModel.getAllProduct()
            viewModel.getProduct.observe(requireActivity()) { listProduct ->
                showData(listProduct)
            }

            binding.btnChat.setOnClickListener {
                val intent = Intent(requireActivity(), DetailChat::class.java)
                startActivity(intent)
            }


            productAdapter.setOnItemClickCallback(object : ListProductAdapter.OnItemClickCallback {
                    override fun onItemClicked(story: ProductsItem) {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailProductFragment(
                                story
                            )
                        )
                    }
                })
            }


        }



    private fun onSuccess(params: Boolean, message: String) {
        if (params) {
            Toast.makeText(requireActivity(), "Berhasil", Toast.LENGTH_SHORT).show()
            binding.tvid.visibility = View.VISIBLE
        } else {
            Toast.makeText(requireActivity(), "Berhasil", Toast.LENGTH_SHORT).show()
            binding.tvid.visibility = View.VISIBLE

        }

    }

    private fun showData(data: List<ProductsItem>) {


        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireActivity(), 2)

        }
        productAdapter.setAllData(data)

    }

    private fun checkAllPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        launcherIntentCameraX.launch(Intent(requireContext(), CameraActivity::class.java))
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = Utils.rotateBitmap(BitmapFactory.decodeFile(myFile.path), isBackCamera)

            val os: OutputStream = BufferedOutputStream(FileOutputStream(myFile))
            result.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.close()

            currentFile = myFile


        }
    }
}