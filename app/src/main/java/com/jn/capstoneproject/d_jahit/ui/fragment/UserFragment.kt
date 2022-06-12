package com.jn.capstoneproject.d_jahit.ui.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.SessionManager
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.databinding.FragmentUserBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import com.jn.capstoneproject.d_jahit.viewmodel.ProfileViewModel
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


class UserFragment : Fragment() {

    private lateinit var navbar: BottomNavigationView
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private lateinit var sessionManager: SessionManager
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private var password=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentUserBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar=requireActivity().findViewById(R.id.nav_view)
        navbar.visibility=View.GONE
        sessionManager= SessionManager(requireActivity())
        val id= sessionManager.fetchAccessId()
        if (id !=null){
            viewModel.getUserById(id, object : ApiCallbackString {
                override fun onResponse(success: Boolean, message: String) {

                }
            })
            viewModel.getUser.observe(requireActivity(),{ user ->
                setUser(user)
            })
        }
        binding.apply {
            tvName.setOnClickListener {
                showAlerdDialogName()
            }
            tvUsername.setOnClickListener {
                showAlerdDialogUsername()
            }
            imgButtonGender.setOnClickListener {
                val choice = arrayOf("Laki-laki","Perempuan")
                var setgender=""
                var selectedItemIndex=0
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("Jenis Kelamin")
                    .setSingleChoiceItems(choice,selectedItemIndex){ dialogInterface: DialogInterface, postiom: Int ->
                        selectedItemIndex=postiom
                     setgender= choice[postiom]
                }
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        tvGender.text=setgender
                })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

                })
                    .setCancelable(true)
                    .show()
            }
            imgButtonDateofbirh.setOnClickListener {
                    val c= Calendar.getInstance()
                    val year= c.get(Calendar.YEAR)
                    val month= c.get(Calendar.MONTH)
                    val day= c.get(Calendar.DAY_OF_MONTH)
                val datePicker= DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    tvDateofbirh.text=("$mdayOfMonth/$mmonth/$myear")
                }, year, month,day)
                datePicker.show()
            }

            tvPhoneNumber.setOnClickListener {
                showAlerdDialogPhone()
            }
            tvEmail.setOnClickListener {
                showAlerdDialogEmail()
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }


        }

    private fun onSucces(success: Boolean, message: String) {
        if(success){
            Toast.makeText(requireActivity(),message,Toast.LENGTH_SHORT).show()
        }
        else{Toast.makeText(requireActivity(),message,Toast.LENGTH_SHORT).show()}
    }

    private fun showAlerdDialogEmail() {
        val builder= AlertDialog.Builder(requireActivity())
        val  inflater= layoutInflater
        val dialogLayout =inflater.inflate(R.layout.edit_text,null)
        val editText= dialogLayout.findViewById<EditText>(R.id.et_edit)
        with(builder){
            setTitle("Email")
            setPositiveButton("Ok"){dialog, wich ->
                binding.tvEmail.text= editText.text.toString()
            }
            setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showAlerdDialogPhone() {
        val builder= AlertDialog.Builder(requireActivity())
        val  inflater= layoutInflater
        val dialogLayout =inflater.inflate(R.layout.edit_text,null)
        val editText= dialogLayout.findViewById<EditText>(R.id.et_edit)
        with(builder){
            setTitle("Nomer telepon")
            setPositiveButton("Ok"){dialog, wich ->
                binding.tvPhoneNumber.text= editText.text.toString()
            }
            setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showAlerdDialogUsername() {
        val builder= AlertDialog.Builder(requireActivity())
        val  inflater= layoutInflater
        val dialogLayout =inflater.inflate(R.layout.edit_text,null)
        val editText= dialogLayout.findViewById<EditText>(R.id.et_edit)
        with(builder){
            setTitle("Username")
            setPositiveButton("Ok"){dialog, wich ->
                binding.tvUsername.text= editText.text.toString()
            }
            setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showAlerdDialogName() {
        val builder= AlertDialog.Builder(requireActivity())
        val  inflater= layoutInflater
        val dialogLayout =inflater.inflate(R.layout.edit_text,null)
        val editText= dialogLayout.findViewById<EditText>(R.id.et_edit)
        with(builder){
            setTitle("Nama Lengkap")
            setPositiveButton("Ok"){dialog, wich ->
                binding.tvName.text= editText.text.toString()
            }
            setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int -> }
            setView(dialogLayout)
            show()
        }

    }

    private fun setUser(user: UserResponse?) {
        binding.apply {
            val id=user?.id
            val password=user?.password?.trim()
            tvName.text= user?.fullName
            tvUsername.text=user?.username
            tvEmail.text=user?.email
            tvGender.text=user?.gender
            tvPhoneNumber.text=user?.phoneNumber
            tvDateofbirh.text=user?.dateOfBirth
            btnSave.setOnClickListener {
                if (id != null){
                    val name=tvName.text.toString().trim()
                    val username=tvUsername.text.toString().trim()
                    val gender=tvGender.text.toString().trim()
                    val dateOfBirh=tvDateofbirh.text.toString().trim()
                    val phoneNumber=tvPhoneNumber.text.toString().trim()
                    val email=tvEmail.text.toString().trim()
                    val bodyname=name.toRequestBody()
                    val bodyusername=username.toRequestBody()
                    val bodypass=password!!.toRequestBody()
                    val bodygender=gender.toRequestBody()
                    val bodytgl=dateOfBirh.toRequestBody()
                    val bodynumber=phoneNumber.toRequestBody()
                    val bodyemail=email.toRequestBody()
                    viewModel.updateUser(id,name,username,password,gender,dateOfBirh,phoneNumber,email,object :ApiCallbackString{
                        override fun onResponse(success: Boolean, message: String) {
                            onSucces(success,message)
                        }
                    })
                }
            }
        }
    }
}


