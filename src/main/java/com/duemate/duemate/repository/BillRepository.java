package com.duemate.duemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duemate.duemate.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
