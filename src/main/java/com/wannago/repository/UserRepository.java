package com.wannago.repository;

import com.wannago.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsEmail(String usEmail);
}
