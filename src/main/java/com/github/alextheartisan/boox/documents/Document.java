package com.github.alextheartisan.boox.documents;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "documents")
@Getter
@ToString
class Document {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @NotBlank(message = "Title could not be empty")
    @Size(max = 128)
    @Column
    private String title;

    @Setter
    @NotBlank(message = "Author could not be empty")
    @Size(max = 128)
    @Column
    private String author;

    public Document() {}

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
