package com.example.movielingo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFlashCard {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String flashCardName;
    @Column
    private String sourceLanguage;
    @Column
    private String targetLanguage;

    //WORKING
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "flashcard_id",referencedColumnName = "id")
    private List<FlashCard> flashCards;






    public String getEmail() {
        return email;
    }

    public String getFlashCardName() {
        return flashCardName;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserFlashCard{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", flashCardName='" + flashCardName + '\'' +
                ", sourceLanguage='" + sourceLanguage + '\'' +
                ", targetLanguage='" + targetLanguage + '\'' +
                ", flashCards=" + flashCards +
                '}'+"\n";
    }

    public Long getId() {
        return id;
    }

    public List<FlashCard> getFlashCards() {
        return flashCards;
    }
}
