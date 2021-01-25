package com.example.movielingo.respository;


import com.example.movielingo.model.User;
import com.example.movielingo.model.WordFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface WordFrequencyRepository  extends JpaRepository<WordFrequency,Long> {

    @Query(value = "Select p from WordFrequency p where p.word =?1")
    @Transactional
    Long findWordFrequencyByFrequencyValueIs(String email);

}
