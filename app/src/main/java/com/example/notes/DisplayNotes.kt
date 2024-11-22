package com.example.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DisplayNotes : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notesListView: ListView
    private lateinit var notesList: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_notes)

        // Initialize SharedPreferences and ListView
        sharedPreferences = getSharedPreferences("NoteData", Context.MODE_PRIVATE)
        notesListView = findViewById(R.id.notesListView)

        // Fetch stored notes
        notesList = getNotesFromSharedPreferences().toMutableList()

        // Use the custom list_item_note.xml layout for each item
        adapter = ArrayAdapter(this, R.layout.list_item_note, R.id.noteTextView, notesList)
        notesListView.adapter = adapter

        // Set click listeners for update and delete actions
        notesListView.setOnItemClickListener { parent, view, position, id ->
            val updateButton: ImageView = view.findViewById(R.id.updateButton)
            val deleteButton: ImageView = view.findViewById(R.id.deleteButton)

            updateButton.setOnClickListener {
                showEditDialog(position)
            }

            deleteButton.setOnClickListener {
                showDeleteDialog(position)
            }
        }

        // Navigate to MainActivity when AddButton is clicked
        val addButton: Button = findViewById(R.id.AddButton)
        addButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getNotesFromSharedPreferences(): Set<String> {
        return sharedPreferences.getStringSet("notes", setOf()) ?: setOf()
    }

    private fun saveNotesToSharedPreferences(notes: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet("notes", notes)
        editor.apply()
    }

    // Show dialog to confirm deletion
    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { _, _ ->
                notesList.removeAt(position)
                saveNotesToSharedPreferences(notesList.toSet())
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // Show dialog to edit a note
    private fun showEditDialog(position: Int) {
        val editText = EditText(this)
        editText.setText(notesList[position])

        AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(editText)
            .setPositiveButton("Save") { dialog, _ ->
                val newNote = editText.text.toString()
                notesList[position] = newNote
                saveNotesToSharedPreferences(notesList.toSet())
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
