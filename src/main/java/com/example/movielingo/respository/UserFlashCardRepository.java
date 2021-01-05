package com.example.movielingo.respository;



import com.example.movielingo.model.UserFlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFlashCardRepository extends JpaRepository<UserFlashCard,Long> {


    @Query("select userFlashCard FROM UserFlashCard userFlashCard where userFlashCard.email = ?1")
    List<UserFlashCard> findFlashCardByEmail(String email);


}
