package com.sun.unsplash03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ThemeUnsplash03)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
