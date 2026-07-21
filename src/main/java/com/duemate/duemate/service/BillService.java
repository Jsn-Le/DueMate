package com.duemate.duemate.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.duemate.duemate.exception.BillNotFoundException;
import com.duemate.duemate.model.Bill;
import com.duemate.duemate.model.BillStatus;
import com.duemate.duemate.repository.BillRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BillService {
    
    private final BillRepository billRepository;

    // POST - Create a bill
    public Bill createBill(String name, BigDecimal amount, LocalDate dueDate) {
        BillStatus status = BillStatus.PENDING;

        if (dueDate.isBefore(LocalDate.now())) {
            status = BillStatus.OVERDUE;
        }

        Bill bill = new Bill(name, amount, dueDate, status);

        return billRepository.save(bill);
    }

    // GET - Get all bills
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
    
    // GET - Get a bill by ID
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill with id " + id + " not found."));
    }

    // UPDATE - Update a bill
    public Bill updateBill(Long id, String updatedName, BigDecimal updatedAmount, LocalDate updatedDueDate) {
        Bill bill = getBillById(id);
        bill.setName(updatedName);
        bill.setAmount(updatedAmount);
        bill.setDueDate(updatedDueDate);
        return billRepository.save(bill);
    }

    // UPDATE - Mark a bill as paid
    public Bill markBillPaid(Long id) {
        Bill bill = getBillById(id);
        bill.setStatus(BillStatus.PAID);
        return billRepository.save(bill);
    }

    // UPDATE - Mark bills as overdue (Scheduled Task)
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Chicago")
    public void updateOverdueBills() {
        List<Bill> bills = billRepository.findAll();

        for (Bill bill : bills) {
            if (bill.getDueDate().isBefore(LocalDate.now()) && bill.getStatus() != BillStatus.PAID) {
                bill.setStatus(BillStatus.OVERDUE);
                billRepository.save(bill);
            }
        }
    }

    // DELETE - Delete a bill
    public void deleteBill(Long id) {
        Bill bill = getBillById(id);
        billRepository.delete(bill);
    }

}
