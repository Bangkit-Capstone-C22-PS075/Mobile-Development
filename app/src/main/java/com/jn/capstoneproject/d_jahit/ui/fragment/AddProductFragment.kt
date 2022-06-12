package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jn.capstoneproject.d_jahit.*
import com.jn.capstoneproject.d_jahit.databinding.FragmentAddProductBinding
import com.jn.capstoneproject.d_jahit.ml.Modelpakaian
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.viewmodel.AddProductViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class AddProductFragment : Fragment() {
    private lateinit var navbar: BottomNavigationView
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val args: AddProductFragmentArgs by navArgs()
    private val viewModel: AddProductViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private var result=""
    var imageSize = 224
    private lateinit var sessionManager: SessionManager
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar = requireActivity().findViewById(R.id.nav_view)
        navbar.visibility = View.GONE
        sessionManager = SessionManager(requireActivity())
        binding.apply {
            photoProduct.setOnClickListener { startGallery() }

            btnSave.setOnClickListener {
                addProduct()
            }
        }
    }

    private fun addProduct() {
            val name = binding.edtName.text.toString()
            val category = result
            val sellerId = args.data
            val produkid=args.idproduct
            if (produkid.isEmpty()){
                if (getFile != null) {
                    val definatio = binding.edtDeskripsi.text.toString()
                    val price1 = binding.edtPrice1.text.toString()
                    val price2 = binding.edtPrice2.text.toString()
                    val dprice = price1.toDouble()
                    val dprice2 = price2.toDouble()
                    var bodyname = name.toRequestBody()
                    val bodyseller = sellerId.toRequestBody()
                    val bodycategory = category.toRequestBody()
                    val bodydefination = definatio.toRequestBody()

                    val file = Utils.reduceFileImage(getFile as File)
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "productPhoto",
                        file.name,
                        requestImageFile
                    )
                    viewModel.addProduct(
                        bodyseller,
                        imageMultipart,
                        bodyname,
                        bodycategory,
                        bodydefination,
                        dprice,
                        dprice2,
                        object : ApiCallbackString {
                            override fun onResponse(success: Boolean, message: String) {
                                onSucces(success, message)
                            }
                        })
                }
                else{
                    Toast.makeText(requireActivity(),"Gambar Wajib dimasukan", Toast.LENGTH_SHORT).show()
                }

            }
            else {
                Toast.makeText(requireActivity(),"idProduk tidak ada", Toast.LENGTH_SHORT).show()
                updateProduct()
        }

    }

    private fun updateProduct() {
        val produkid=args.idproduct
        viewModel.getProductById(produkid)
        viewModel.getProdukByid.observe(requireActivity()){
            showProduct(it)
        }
    }

    private fun showProduct(data: ProductsItem) {
        binding.apply {
            edtName.setText(data.name)
            edtDeskripsi.setText(data.definition)
            val price1=data.price1
            val price2=data.price2
            val doublePrice1=price1.toString()
            val doublePrice=price2.toString()
            edtPrice1.setText(doublePrice1)
            edtPrice2.setText(doublePrice)
            Glide.with(requireContext())
                .load(data.productPhoto)
                .into(photoProduct)
        }
    }

    private fun onSucces(params: Boolean, message: String) {
        if (params) {
            findNavController().popBackStack()
        }
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                val myFile = Utils.uriToFile(selectedImg, requireActivity())
                binding.photoProduct.setImageURI(selectedImg)
                getFile = myFile
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
        result = classes[maxPos]
        model.close()
    }
}