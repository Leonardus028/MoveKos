package com.example.movekos.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movekos.AuthenticationActivity
import com.example.movekos.R
import com.example.movekos.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


@Suppress("CAST_NEVER_SUCCEEDS")
class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var user: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var userID: String
    private lateinit var logout : Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)


        logout = root.findViewById(R.id.button_logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, AuthenticationActivity::class.java))
            requireActivity().finish()
        }
        user = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users")
        userID = user!!.uid


        val emailTextView: TextView = root.findViewById(R.id.emailAddress)
        val fullNameTextView: TextView = root.findViewById(R.id.fullName)
        reference!!.child(userID!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(
                    User::class.java
                )
                if (userProfile != null) {
                    val fullName = userProfile.fullName
                    val email = userProfile.email
                    fullNameTextView.text = fullName
                    emailTextView.text = email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Something wrong Happened!", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        return root
    }
}