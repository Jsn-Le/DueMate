package com.duemate.duemate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duemate.duemate.model.Bill;
import com.duemate.duemate.model.User;

public interface BillRepository extends JpaRepository<Bill, Long> {

    public List<Bill> getBillsByUser(User user);

}
