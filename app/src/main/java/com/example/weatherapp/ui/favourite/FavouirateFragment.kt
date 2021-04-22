package com.example.weatherapp.ui.favourite

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.Map.MapsActivity
import com.example.weatherapp.databinding.FavouirateFragmentBinding

class FavouirateFragment : Fragment() {

    private lateinit var viewModel: FavouirateViewModel
    lateinit var binding: FavouirateFragmentBinding
    lateinit var favoriteAdapter: FavoriteListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FavouirateFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(FavouirateViewModel::class.java)
        // TODO: Use the ViewModel
        favoriteAdapter= FavoriteListAdapter(arrayListOf(),context,viewModel)
        initUI()

        binding.ivAddCity.setOnClickListener(View.OnClickListener {
            val intent = Intent (context, MapsActivity::class.java)
            context?.startActivity(intent)
        })

        viewModel.getTimezone().observe(viewLifecycleOwner,{
            it?.let {
                favoriteAdapter.update(it)
            }
        })
    }
    private fun initUI(){
        binding.rvSavedCity.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

}