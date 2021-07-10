package com.notebytes.repository;

import com.notebytes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteBytesRepository extends JpaRepository<Note, Long> {

}
