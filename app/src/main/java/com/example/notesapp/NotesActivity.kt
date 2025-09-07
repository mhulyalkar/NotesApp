package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NotesActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var noteInput: EditText
    private lateinit var addNoteBtn: Button
    private lateinit var logoutBtn: Button
    private lateinit var notesContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        noteInput = findViewById(R.id.noteInput)
        addNoteBtn = findViewById(R.id.addNoteBtn)
        logoutBtn = findViewById(R.id.logoutBtn)
        notesContainer = findViewById(R.id.notesContainer)

        addNoteBtn.setOnClickListener { addNote() }
        logoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        loadNotes()
    }

    private fun addNote() {
        val text = noteInput.text.toString()
        if (text.isEmpty()) return

        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).collection("notes")
            .add(Note(text))
            .addOnSuccessListener {
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                noteInput.text.clear()
                loadNotes()
            }
    }

    private fun loadNotes() {
        notesContainer.removeAllViews()
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val note = doc.toObject(Note::class.java)
                    val tv = TextView(this)
                    tv.text = "• ${note.text}"
                    notesContainer.addView(tv)
                }
            }
    }
}
