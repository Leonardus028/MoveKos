package com.example.movekos

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var firstTimeUser = true
    private var fileUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        supportActionBar?.hide()
        val viewPager = findViewById<ViewPager>(R.id.ViewPager)
        val pagerAdapter: AuthenticationPagerAdapter = this.AuthenticationPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragmet(LoginFragment())
        pagerAdapter.addFragmet(RegisterFragment())
        viewPager.adapter = pagerAdapter

    }
    fun buttonClicks(view: View) {
        val mButton = findViewById<Button>(R.id.button_login)
        mButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    internal inner class AuthenticationPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        private val fragmentList = ArrayList<Fragment>()
        override fun getItem(i: Int): Fragment {
            return fragmentList[i]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragmet(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }



}
