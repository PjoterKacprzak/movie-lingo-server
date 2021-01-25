package com.example.movielingo.model;


import javax.persistence.*;

@Entity
public class WordFrequency {


    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;
    @Column(name = "word_frequency")
    private Long frequencyValue;

    public WordFrequency(String word, Long frequencyValue) {

        this.word = word;
        this.frequencyValue = frequencyValue;
    }

    public WordFrequency() {
        this.word="none";
        this.frequencyValue = 0L;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public Long getFrequencyValue() {
        return frequencyValue;
    }

    @Override
    public String toString() {
        return "WordFrequency{" +
                "word='" + word + '\'' +
                ", frequencyValue=" + frequencyValue +
                '}';
    }
}
