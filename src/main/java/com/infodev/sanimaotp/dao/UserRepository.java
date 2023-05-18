package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.entities.User;
import com.infodev.sanimaotp.pojo.UserPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user_credentials where username=?1", nativeQuery = true)
    User findByName(String username);

    Optional<User> findByUsername(final String username);

}
