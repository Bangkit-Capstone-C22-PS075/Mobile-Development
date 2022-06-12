package com.jn.capstoneproject.d_jahit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.adapter.HistoryAdapter
import com.jn.capstoneproject.d_jahit.database.history.HistoryDatabase
import com.jn.capstoneproject.d_jahit.databinding.FragmentHistoryBinding
import com.jn.capstoneproject.d_jahit.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryFragment : Fragment() {


    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    val db by lazy { HistoryDatabase(requireActivity()) }
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentHistoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        setupRecycle()
    }

    private fun setupRecycle() {
        historyAdapter= HistoryAdapter(arrayListOf())
        binding.rvHistory.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=historyAdapter
        }
    }
//
    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val history=db.historyDao().getHistoryListProduct()
            withContext(Dispatchers.Main){
                historyAdapter.setData(history)
            }
        }
    }

}