package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jn.capstoneproject.d_jahit.Constanta.REQUIRED_PERMISSIONS
import com.jn.capstoneproject.d_jahit.databinding.FragmentHomeBinding
import com.jn.capstoneproject.d_jahit.ui.activity.CameraActivity
import com.jn.capstoneproject.d_jahit.ui.activity.MainActivity
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import com.jn.capstoneproject.d_jahit.Utils


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private var currentFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkAllPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                MainActivity.REQUIRED_PERMISSIONS,
                MainActivity.REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.btnSearch.setOnClickListener {
            startCameraX()
        }
    }
    private fun checkAllPermissionsGranted() = MainActivity.REQUIRED_PERMISSIONS.all {
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
        if (it.resultCode == MainActivity.CAMERA_X_RESULT) {
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