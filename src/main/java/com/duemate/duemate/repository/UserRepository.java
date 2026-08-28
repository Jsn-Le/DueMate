package com.duemate.duemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duemate.duemate.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);

    public boolean existsByEmailAndIdNot(String email, Long id);

    public User getUserByEmail(String email);

}
