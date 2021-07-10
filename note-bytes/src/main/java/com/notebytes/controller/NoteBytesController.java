package com.notebytes.controller;

import com.notebytes.exception.ResourceNotFoundException;
import com.notebytes.model.Note;
import com.notebytes.repository.NoteBytesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/note-bytes")
public class NoteBytesController {

    private final NoteBytesRepository noteBytesRepository;

    @Autowired
    public NoteBytesController(NoteBytesRepository noteBytesRepository) {
        this.noteBytesRepository = noteBytesRepository;
    }

    // Get all notes
    @GetMapping("/notes/all")
    public List<Note> getAllNoteBytes() {
        return noteBytesRepository.findAll();
    }

    // Create a new note
    @PostMapping("/note/create")
    public Note createNoteByte(@Valid @RequestBody Note note) {
        return noteBytesRepository.save(note);
    }

    // Get a single note
    @GetMapping("/notes/{id}")
    public Note getNoteByteById(@PathVariable("id") final Long noteId) {
        return noteBytesRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "note_id", noteId));
    }

    // Update a note
    @PutMapping("/notes/{id}")
    public Note updateNoteByteById(@PathVariable("id") final Long noteId, @Valid @RequestBody Note noteDetails) {
        Note note = noteBytesRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "note_id", noteId));
        note.setTitle(noteDetails.getTitle());
        note.setDescription(noteDetails.getDescription());

        return noteBytesRepository.save(note);
    }

    // Delete a note
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNoteByte(@PathVariable("id") final Long noteId) {
        Note note = noteBytesRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "note_id", noteId));
        noteBytesRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
