package com.example.myapplication1.fragment.addmusic

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication1.R

class AddMusicFragment : Fragment() {

    companion object {
        fun newInstance() = AddMusicFragment()
    }

    private lateinit var viewModel: AddMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMusicViewModel::class.java)
        // TODO: Use the ViewModel
    }

}