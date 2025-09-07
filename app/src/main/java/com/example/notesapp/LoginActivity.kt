package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        signupBtn = findViewById(R.id.signupBtn)

        loginBtn.setOnClickListener { login() }
        signupBtn.setOnClickListener { signup() }
    }

    private fun login() {
        val e = email.text.toString()
        val p = password.text.toString()
        auth.signInWithEmailAndPassword(e, p).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, NotesActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signup() {
        val e = email.text.toString()
        val p = password.text.toString()
        auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, NotesActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
