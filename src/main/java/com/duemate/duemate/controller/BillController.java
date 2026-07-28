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

import com.duemate.duemate.dto.BillResponse;
import com.duemate.duemate.dto.CreateBillRequest;
import com.duemate.duemate.dto.UpdateBillRequest;
import com.duemate.duemate.service.BillService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    // POST - Create a bill
    @PostMapping
    public BillResponse createBill(@RequestBody CreateBillRequest request) {
        return billService.createBill(request);
    }

    // GET - Get all bills
    @GetMapping
    public List<BillResponse> getAllBills() {
        return billService.getAllBills();
    }

    //  GET - Get a bill by ID
    @GetMapping("/{id}")
    public BillResponse getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    // GET - Get all bills by User
    @GetMapping("/user/{userId}")
    public List<BillResponse> getBillsByUser(@PathVariable Long userId) {
        return billService.getBillsByUser(userId);
    }

    // UPDATE - Update a bill
    @PutMapping("/{id}")
    public BillResponse updateBill(@RequestBody UpdateBillRequest request, @PathVariable Long id) {
        return billService.updateBill(request, id);
    }

    // UPDATE - Mark a bill as paid
    @PutMapping("/{id}/paid")
    public BillResponse markBillPaid(@PathVariable Long id) {
        return billService.markBillPaid(id);
    }

    // DELETE - Delete a bill
    @DeleteMapping("/{id}")
    public String deleteBill(@PathVariable Long id) {
        return billService.deleteBill(id);
    }

}
