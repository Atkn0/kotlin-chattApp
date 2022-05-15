package com.example.chatapp.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Adapter.recyclerViewAdapter
import com.example.chatapp.Model.ChatModel
import com.example.chatapp.databinding.ActivityChatScreenBinding
import com.example.chatapp.databinding.ChatRowBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime

class ChatScreen : AppCompatActivity() {

    lateinit var binding: ActivityChatScreenBinding
    lateinit var db:FirebaseFirestore
    lateinit var Chatlist:ArrayList<ChatModel>
    lateinit var adapter: recyclerViewAdapter
    lateinit var messageMap:HashMap<String,Any>
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()
        Chatlist = ArrayList()

        getDataFromFirebase()

        binding.RecyclerView.layoutManager = LinearLayoutManager(applicationContext)
         adapter = recyclerViewAdapter(liste = Chatlist)
        binding.RecyclerView.adapter = adapter


    }


    fun getDataFromFirebase(){

        db.collection("Chats").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { snapshot, error ->

            if (error!=null){
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            }
            if (snapshot!=null){
                val documents = snapshot.documents

                Chatlist.clear()

                for (document in documents){
                    val email = document["email"] as String
                    val message = document["message"] as String
                    val chatModel = ChatModel(email = email, chatText = message)
                    Chatlist.add(chatModel)

                }
                adapter.notifyDataSetChanged()

            }

        }


    }

    fun sendMessage(view:View){

        val messageText = binding.Messagetext.text.toString()
        val date = Timestamp.now()
        val email = auth.currentUser!!.email

        if (messageText.equals("")){
            Toast.makeText(this, "Mesaj kutusu boş", Toast.LENGTH_LONG).show()
        }
        else{
            messageMap = HashMap()
            messageMap.put("message",messageText)
            messageMap.put("date",date)
            messageMap.put("email",email!!)

            db.collection("Chats").add(messageMap).addOnSuccessListener {

                binding.Messagetext.text.clear()

            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }



    }




}