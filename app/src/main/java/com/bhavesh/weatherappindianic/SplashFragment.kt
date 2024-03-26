package com.bhavesh.weatherappindianic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bhavesh.weatherappindianic.utils.LocationUtil

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SplashFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper())
            .postDelayed({
                getNext()
            },2000)
    }

    private fun getNext(){
        if(LocationUtil(context = requireContext()).isOnline()) {
            //startActivity(Intent(requireContext(), MapActivity::class.java))
            //requireActivity().finish()
            findNavController().navigate(R.id.action_splashFragment_to_mapFragment)
            //findNavController().navigateUp()
        }else{
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.title_permission_denied))
        dialog.setMessage(getString(R.string.message_permission_denied))
        dialog.setPositiveButton(getString(R.string.retry)) { _, _->
            getNext()
        }
        dialog.setOnCancelListener { }
        dialog.show()
    }
}