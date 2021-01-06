package com.example.movielingo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlashCard {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
   private String sourceWord;
    @Column
   private String translatedWord;

//    //WORKINGg
//    @JoinColumn(name = "user_flash_card_id")
//    @ManyToOne(cascade=CascadeType.ALL)
//    private UserFlashCard userFlashCard;



    public String getSourceWord() {
        return sourceWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }






    public Long getFlashCardId() {
        return id;
    }

    public void setFlashCardId(Long flashCardId) {
        this.id = flashCardId;
    }

    public void setSourceWord(String sourceWord) {
        this.sourceWord = sourceWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "id=" + id +
                ", sourceWord='" + sourceWord + '\'' +
                ", translatedWord='" + translatedWord + '\'' +
                '}';
    }
}
