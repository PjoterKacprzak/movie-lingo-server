package com.example.movielingo.respository;
import com.example.movielingo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {

     @Query(value = "Select p from User p where p.email =?1")
     @Transactional
     User findByEmail(String email);


}
