package com.jn.capstoneproject.d_jahit.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.*
import com.jn.capstoneproject.d_jahit.Utils.reduceFileImage
import com.jn.capstoneproject.d_jahit.Utils.uriToFile
import com.jn.capstoneproject.d_jahit.api.ApiConfig
import com.jn.capstoneproject.d_jahit.api.ImageResponse
import com.jn.capstoneproject.d_jahit.api.ImagesItem
import com.jn.capstoneproject.d_jahit.databinding.FragmentProfileBinding
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.Repository.Companion.TAG
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import com.jn.capstoneproject.d_jahit.ui.activity.LoginActivity
import com.jn.capstoneproject.d_jahit.viewmodel.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File



class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    private lateinit var navbar: BottomNavigationView
    private var getFile: File? = null
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private var image:Uri?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar=requireActivity().findViewById(R.id.nav_view)
        navbar.visibility=View.VISIBLE
        sessionManager= SessionManager(requireActivity())
        val id= sessionManager.fetchAccessId()
        if (id !=null){

            viewModel.getUserById(id, object : ApiCallbackString{
                override fun onResponse(success: Boolean, message: String) {
                    onSucces(success,message)
                }
            })
            viewModel.getUser.observe(requireActivity(),{ user ->
                setUser(user)
            })
            viewModel.getSellerById(id)
            viewModel.getSeller.observe(requireActivity()){
                showSellerData(it)
            }
        }


        binding.clheader.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userFragment)
        }
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent= Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            sessionManager.deleteAccessToken()
        }
        binding.imgProfile.setOnClickListener {
            startGallery()
        }

        binding.tvCreateStore.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_createStroreFragment)
        }
        binding.tvCreateStore.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_createStroreFragment)
        }
    }

    private fun showSellerData(data: SellersItem) {
        val sellerid=data.id
        binding.tvCreateStore.visibility=View.GONE
        binding.tvCreateProduct.visibility=View.VISIBLE
        binding.tvSeller.text=data.shopName
        binding.tvCreateProduct.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToAddProductFragment(
                    sellerid,data.shopName
                )
            )
        }
        binding.tvSeller.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToProductSellerFragment(
                    sellerid,data.shopName,data.userId
                )
            )
        }

        Log.d(TAG,"sellerId= $sellerid")

    }

    private fun onSucces(param: Boolean, message: String) {
    if (param){

    }
    }

    private fun setUser(user: UserResponse) {

        binding.tvUsername.text= user.fullName


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
               val selectedImg:Uri =  result.data?.data as Uri
                val myFile = uriToFile(selectedImg, requireActivity())
                image=selectedImg
                getFile=myFile

                showAllertDialog()
            }
        }

    private fun showAllertDialog() {
        sessionManager= SessionManager(requireActivity())
        val builder= AlertDialog.Builder(requireActivity())
        with(builder){
            setTitle("Email")
            setPositiveButton("Ok"){dialog, wich ->
                uploadImage()
                binding.imgProfile.setImageURI(image)
            }
            setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int -> }
            show()
        }
    }

    private fun uploadImage() {
        if(getFile !=null){


            val file = reduceFileImage(getFile as File)
            val requestImageFile=file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart:MultipartBody.Part=MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )
            val client=ApiConfig.getImage().uploadImage(imageMultipart)
            client.enqueue(object : Callback<ImageResponse>{
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                  if (response.isSuccessful){
                      Toast.makeText(requireActivity(),response.body()?.status,Toast.LENGTH_SHORT).show()
                      val responseBody = response.body()

                  }else{
                      Toast.makeText(requireActivity(),response.message(),Toast.LENGTH_SHORT).show()
                  }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(),"Gagal terhubung",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}