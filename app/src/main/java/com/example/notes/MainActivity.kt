package com.example.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityMainBinding  // Ensure this import is present

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding  // Use view binding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate using view binding
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("NoteData", Context.MODE_PRIVATE)

        binding.saveNoteButton.setOnClickListener {  // Accessing saveNoteButton
            val note = binding.notesEditText.text.toString()
            if (note.isNotEmpty()) {
                saveNoteToSharedPreferences(note)
                Toast.makeText(this, "Note Saved Successfully", Toast.LENGTH_SHORT).show()
                binding.notesEditText.text.clear()
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            }
        }

        binding.viewNoteButton.setOnClickListener {  // Accessing displayNoteButton
            // Navigate to DisplayNotesActivity
            val intent = Intent(this, DisplayNotes::class.java)
            startActivity(intent)
        }
    }

    private fun saveNoteToSharedPreferences(note: String) {
        val notesList = getNotesFromSharedPreferences().toMutableList()
        notesList.add(note)
        val sharedEdit = sharedPreferences.edit()
        sharedEdit.putStringSet("notes", notesList.toSet())
        sharedEdit.apply()
    }

    private fun getNotesFromSharedPreferences(): Set<String> {
        return sharedPreferences.getStringSet("notes", setOf()) ?: setOf()
    }
}
