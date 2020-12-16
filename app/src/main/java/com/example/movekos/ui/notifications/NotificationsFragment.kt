package com.example.movekos.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movekos.HowtoOrderActivity
import com.example.movekos.R


class NotificationsFragment : Fragment() {
  private lateinit var cardOrder : CardView
  private lateinit var cardCommunity : CardView
  private lateinit var cardAbout : CardView
  private lateinit var cardCredit : CardView


  private lateinit var notificationsViewModel: NotificationsViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_notifications, container, false)
    cardOrder = root.findViewById(R.id.cardOrder)
    cardCommunity= root.findViewById(R.id.cardCommunity)
    cardAbout = root.findViewById(R.id.cardAbout)
    cardCredit = root.findViewById(R.id.cardCredit)

    cardOrder.setOnClickListener {
      startActivity(Intent(context, HowtoOrderActivity::class.java))
    }

    cardCommunity.setOnClickListener {
    }
    cardAbout.setOnClickListener {
    }
    cardCredit.setOnClickListener {
    }


    return root
  }

  override fun onResume() {
    super.onResume()
    (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
  }

  override fun onStop() {
    super.onStop()
    (activity as AppCompatActivity?)!!.supportActionBar!!.show()
  }
}
