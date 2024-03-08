package com.rvv.android.test.taks.lowkey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rvv.android.test.taks.lowkey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
