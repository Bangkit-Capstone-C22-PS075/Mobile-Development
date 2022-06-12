package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jn.capstoneproject.d_jahit.*
import com.jn.capstoneproject.d_jahit.databinding.FragmentCreateSellerBinding
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem
import com.jn.capstoneproject.d_jahit.viewmodel.CreateSellerViewmodel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException

class CreateSellerFragment : Fragment() {
    private lateinit var navbar: BottomNavigationView
    private var _binding: FragmentCreateSellerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateSellerViewmodel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private var latLng: LatLng? = null
    private var idUser=""
    private val args: CreateSellerFragmentArgs by navArgs()
    private var getFile: File? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var googleMap: GoogleMap? = null
    private lateinit var sessionManager: SessionManager
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
        sessionManager= SessionManager(requireActivity())
        val sellerId=args.id
        if (sellerId ==""){
            binding.apply {
                btnSave.setOnClickListener {
                    addSeller()

                }
                imgFullscreen.setOnClickListener {
                    findNavController().navigate(
                        CreateSellerFragmentDirections.actionCreateStroreFragmentToMapsFragment(
                            MapsFragment.ACTION_PICK_LOCATION
                        )
                    )
                }
                imgUpload.setOnClickListener {
                    startGallery()
                }

            }
        }
        else{
            viewModel.getSellerById(sellerId)
            viewModel.getSeller.observe(requireActivity()){
                showSellerData(it)
            }
            binding.btnSave.text="Update"
        }
        setFragmentResultListener(MapsFragment.KEY_RESULT) { _, bundle ->
            val location = bundle.getParcelable(MapsFragment.KEY_LATLONG) as LatLng?
            if (location != null) {
                binding.tvLocation.text =
                    getString(R.string.latlon_format, location.latitude, location.longitude)
                latLng = location
            }
        }


    }

    private fun showSellerData(data: SellersItem) {
        binding.apply {
            edtNamaToko.setText(data.shopName)
            edtProvinsi.setText(data.province)
            edtCity.setText(data.city)
            edtStreet.setText(data.streetName)
            edtStreet.setText(data.detailStreet)
            edtFullname.setText(data.sellerName)
//            edtPhoneNumber.setText(data.phoneNumber)
            Glide.with(requireContext())
                .load(data.sellerPhoto)
                .into(imgUpload)
            btnSave.setOnClickListener {
                idUser=data.id
                updateSeller()
            }
            imgUpload.setOnClickListener {
                startGallery()
            }
            imgFullscreen.setOnClickListener {
                findNavController().navigate(
                    CreateSellerFragmentDirections.actionCreateStroreFragmentToMapsFragment(
                        MapsFragment.ACTION_PICK_LOCATION
                    )
                )
            }
        }
    }

    private fun updateSeller() {
        binding.apply {
            val userId = sessionManager.fetchAccessId()
            val namaSeller = edtNamaToko.text.toString().trim()
            val provinsi = edtProvinsi.text.toString().trim()
            val city = edtCity.text.toString().trim()
            val street = edtStreet.text.toString().trim()
            val detail = edtDetail.text.toString().trim()
            val nama = edtFullname.text.toString().trim()
            val number = edtPhoneNumber.text.toString().trim()


            if (userId !=null) {
                val bUserId = userId.toRequestBody()
                val bnameseller=namaSeller.toRequestBody()
                val bprovinsi =provinsi.toRequestBody()
                val bcity = city.toRequestBody()
                val bstreet = street.toRequestBody()
                val bdetail = detail.toRequestBody()
                val bnama = nama.toRequestBody()
                val bnumber = number.toRequestBody()
                val file = Utils.reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "sellerPhoto",
                    file.name,
                    requestImageFile
                )
                viewModel.updateSeller(
                    idUser,
                    bUserId,
                    bnameseller,
                    imageMultipart,
                    bprovinsi,
                    bcity,
                    bstreet,
                    bdetail,
                    bnama,
                    bnumber,
                    latLng,
                    object : ApiCallbackString {
                        override fun onResponse(success: Boolean, message: String) {
                            onSuccess(success, message)
                        }
                    })


            }
        }

    }


    private fun addSeller() {
        if (getFile !=null) {
            val file = Utils.reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "sellerPhoto",
                file.name,
                requestImageFile
            )
            binding.apply {
                val userId = sessionManager.fetchAccessId()
                val namaSeller = edtNamaToko.text.toString().trim()
                val provinsi = edtProvinsi.text.toString().trim()
                val city = edtCity.text.toString().trim()
                val street = edtStreet.text.toString().trim()
                val detail = edtDetail.text.toString().trim()
                val nama = edtFullname.text.toString().trim()
                val number = edtPhoneNumber.text.toString().trim()
                if (userId !=null) {
                    val bUserId = userId.toRequestBody()
                    val bnameseller=namaSeller.toRequestBody()
                    val bprovinsi =provinsi.toRequestBody()
                    val bcity = city.toRequestBody()
                    val bstreet = street.toRequestBody()
                    val bdetail = detail.toRequestBody()
                    val bnama = nama.toRequestBody()
                    val bnumber = number.toRequestBody()


                        viewModel.createSeller(
                            bUserId,
                            bnameseller,
                            imageMultipart,
                            bprovinsi,
                            bcity,
                            bstreet,
                            bdetail,
                            bnama,
                            bnumber,
                            latLng,
                            object : ApiCallbackString {
                                override fun onResponse(success: Boolean, message: String) {
                                    onSuccess(success, message)
                                }
                            })
                    Toast.makeText(requireActivity(),"data Berhasil di update",Toast.LENGTH_SHORT).show()


                }
            }
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
                val selectedImg: Uri =  result.data?.data as Uri
                val myFile = Utils.uriToFile(selectedImg, requireActivity())
                binding.imgUpload.setImageURI(selectedImg)
                getFile=myFile
                var image: Bitmap? = null
                try {
                    image = MediaStore.Images.Media.getBitmap(
                        requireActivity().getContentResolver(),
                        selectedImg
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

    private fun onSuccess(param: Boolean, message: String) {
        if (param){
            Toast.makeText(requireActivity(),"Create Toko$message", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        else{
            Toast.makeText(requireActivity(),message, Toast.LENGTH_SHORT).show()
            Log.e(Repository.TAG, "userId: ${message}")

        }
    }

}