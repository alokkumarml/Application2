package com.example.myapplication1.activity

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication1.R
import com.example.myapplication1.databinding.ActivityHomeBinding
import com.example.myapplication1.fragment.add.AddFragment
import com.example.myapplication1.fragment.addlatest.AddLatestFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var permissionLauncher: androidx.activity.result.ActivityResultLauncher<String>

    var binding: ActivityHomeBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        binding!!.content.addlayout.setOnClickListener {


            val splashFragment = AddFragment()
            val transactionmanager = supportFragmentManager.beginTransaction()
            transactionmanager.add(R.id.fragment_containerhome, splashFragment)
            transactionmanager.commit()

        }


    }
}