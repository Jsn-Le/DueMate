package com.duemate.duemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duemate.duemate.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
