package com.notebytes.model;

import com.notebytes.model.audit.DateAudit;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "notes")
public class Note extends DateAudit {

    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notes_seq")
    @SequenceGenerator(name = "notes_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String description;

}
