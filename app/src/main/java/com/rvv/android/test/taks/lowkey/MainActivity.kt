package com.rvv.android.test.taks.lowkey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.rvv.android.test.taks.lowkey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BigImageViewer.initialize(GlideImageLoader.with(this))
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}
