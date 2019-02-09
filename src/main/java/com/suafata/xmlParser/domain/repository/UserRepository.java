package com.suafata.xmlParser.domain.repository;

import com.suafata.xmlParser.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    @Query("SELECT count(1) FROM User u")
    Integer countAll();
}
