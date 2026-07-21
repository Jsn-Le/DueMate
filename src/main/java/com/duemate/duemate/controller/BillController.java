package com.duemate.duemate.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duemate.duemate.model.Bill;
import com.duemate.duemate.service.BillService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    // POST - Create a bill
    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
        return billService.createBill(bill.getName(), bill.getAmount(), bill.getDueDate());
    }

    // GET - Get all bills
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    //  GET - Get a bill by ID
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    // UPDATE - Update a bill
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        return billService.updateBill(id, bill.getName(), bill.getAmount(), bill.getDueDate());
    }

    // UPDATE - Mark a bill as paid
    @PutMapping("/{id}/paid")
    public Bill markBillPaid(@PathVariable Long id) {
        return billService.markBillPaid(id);
    }


    // DELETE - Delete a bill
    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
    }

}
