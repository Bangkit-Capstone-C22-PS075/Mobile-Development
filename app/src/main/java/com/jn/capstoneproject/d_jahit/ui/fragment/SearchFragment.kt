package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.Constanta.REQUEST_CODE_PERMISSIONS
import com.jn.capstoneproject.d_jahit.Constanta.REQUIRED_PERMISSIONS
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.Utils.reduceFileImage
import com.jn.capstoneproject.d_jahit.Utils.uriToFile
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.adapter.ListProductAdapter
import com.jn.capstoneproject.d_jahit.adapter.ListSearchAdapter
import com.jn.capstoneproject.d_jahit.api.ApiConfig
import com.jn.capstoneproject.d_jahit.api.ImageResponse
import com.jn.capstoneproject.d_jahit.databinding.FragmentSearchBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem
import com.jn.capstoneproject.d_jahit.viewmodel.RegisterViewModel
import com.jn.capstoneproject.d_jahit.viewmodel.SearchViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import com.jn.capstoneproject.d_jahit.ml.Modelpakaian
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellerProductItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearcFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private val listProduct: ArrayList<ProductsItem> = arrayListOf()
    private lateinit var productAdapter: ListSearchAdapter
    private var getFile: File? = null
    var imageSize = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkAllPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        productAdapter = ListSearchAdapter()
        binding.rvProduct.apply {
            adapter = productAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())

        }
        binding.svproduct.apply {
            queryHint = "Search product"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val category = query.toString()
                    clearFocus()
                    viewModel.seacrCategory(category).observe(requireActivity()) { listtProduct ->
                        showData(listtProduct)
                    }
                    return true
                }


                override fun onQueryTextChange(newText: String?): Boolean {
                    listProduct.clear()
                    return false
                }
            })
        }
        viewModel.getProduct().observe(requireActivity()) { listtProduct ->

            showData(listtProduct)
        }

        binding.imgCamera.setOnClickListener {
            startCamera()
        }

        binding.imgUpload.setOnClickListener {
            starGalery()
        }


    }

    private fun starGalery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, requireActivity())

//            getFile = myFile
//
//            binding.previewImageView.setImageURI(selectedImg)
//            val data: Uri? = data.getData()
            var image: Bitmap? = null
            try {
                image = MediaStore.Images.Media.getBitmap(
                    requireActivity().getContentResolver(),
                    selectedImg
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
            classifyImage(image)

        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherIntentCamera.launch(intent)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            var image: Bitmap = it.data?.extras?.get("data") as Bitmap
            val dimension: Int = Math.min(image.getWidth(), image.getHeight())
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
//            imageView!!.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            classifyImage(image)

        }
    }

    fun classifyImage(image: Bitmap) {

        val model: Modelpakaian = Modelpakaian.newInstance(requireContext())

        // Creates inputs for reference.
        val inputFeature0: TensorBuffer =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        // get 1D array of 224 * 224 pixels in image
        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(
            intValues,
            0,
            image.getWidth(),
            0,
            0,
            image.getWidth(),
            image.getHeight()
        )

        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs: Modelpakaian.Outputs = model.process(inputFeature0)
        val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
        val confidences: FloatArray = outputFeature0.getFloatArray()
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf(
            "blouse",
            "coat",
            "uniform",
            "vest",
            "jacket",
            "long dress",
            "poloshirt",
            "Shirt",
            "short dress",
            "suit",
            "sweater",
            "kebaya"
        )
        val result = classes[maxPos]
        viewModel.seacrCategory(result).observe(requireActivity()) { listtProduct ->
            showData(listtProduct)
        }
        model.close()

    }


    private fun showData(data: List<SellerProductItem>?) {
        if (data != null) {
            productAdapter.setAllData(data)
        } else {
            Toast.makeText(requireActivity(), "data null", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkAllPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}



