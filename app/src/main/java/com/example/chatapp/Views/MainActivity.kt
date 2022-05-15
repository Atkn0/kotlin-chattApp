package com.example.chatapp.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser!= null){
            val intent = Intent(this, ChatScreen::class.java)
            startActivity(intent)
        }

    }


    fun loginButton(view: View){

        val email = binding.emailtext.text.toString()
        val password = binding.passwordText.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {

            val intent = Intent(this,ChatScreen::class.java)
            startActivity(intent)


        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }



}