package com.bhavesh.weatherappindianic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.bhavesh.weatherappindianic.utils.LocationUtil

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper())
            .postDelayed({
                getNext()
        },2000)
    }

    private fun getNext(){
        if(LocationUtil(context = applicationContext).isOnline()) {
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }else{
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_denied))
        dialog.setMessage(getString(R.string.message_permission_denied))
        dialog.setPositiveButton(getString(R.string.retry)) { _, _ ->
            getNext()
        }
        dialog.setOnCancelListener { }
        dialog.show()
    }
}