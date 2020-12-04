 package com.example.movekos.ui.home

  import android.content.Intent
  import android.os.Bundle
  import android.view.LayoutInflater
  import android.view.View
  import android.view.ViewGroup
  import android.widget.ImageButton
  import android.widget.TextView
  import androidx.fragment.app.Fragment
  import androidx.lifecycle.Observer
  import androidx.lifecycle.ViewModelProvider
  import com.example.movekos.HomeActivity
  import com.example.movekos.OrderActivity
  import com.example.movekos.R
  import com.example.movekos.ui.dashboard.ProfileViewModel
  import com.google.android.material.floatingactionbutton.FloatingActionButton
  import com.google.firebase.auth.FirebaseAuth
  import com.google.firebase.database.FirebaseDatabase
  import kotlinx.android.synthetic.main.fragment_home.*

  class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mgoToOrder : FloatingActionButton

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
      homeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)
      val root = inflater.inflate(R.layout.fragment_home, container, false)
      val textView: TextView = root.findViewById(R.id.text_home)
      mgoToOrder = root.findViewById(R.id.goToOrder)
      homeViewModel.text.observe(viewLifecycleOwner, Observer {
        textView.text = it
      })

      mgoToOrder.setOnClickListener {
        startActivity(Intent(context, OrderActivity::class.java))

      }

      return root
    }
}