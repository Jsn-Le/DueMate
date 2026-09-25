package com.duemate.duemate.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.duemate.duemate.dto.BillResponse;
import com.duemate.duemate.dto.CreateBillRequest;
import com.duemate.duemate.dto.UpdateBillRequest;
import com.duemate.duemate.exception.BillNotFoundException;
import com.duemate.duemate.exception.ForbiddenException;
import com.duemate.duemate.mapper.BillMapper;
import com.duemate.duemate.model.Bill;
import com.duemate.duemate.model.BillStatus;
import com.duemate.duemate.model.User;
import com.duemate.duemate.repository.BillRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BillService {

    private final BillMapper billMapper;
    private final BillRepository billRepository;
    private final CurrentUserService currentUserService;

    // POST - Create a bill
    public BillResponse createBill(CreateBillRequest request) {
        User user = currentUserService.getCurrentUser();

        BillStatus status = BillStatus.PENDING;
        LocalDate dueDate = request.getDueDate();
        if (dueDate.isBefore(LocalDate.now())) {
            status = BillStatus.OVERDUE;
        }

        Bill bill = billMapper.convertRequestToBill(request);
        bill.setUser(user);
        bill.setStatus(status);
        billRepository.save(bill);

        return billMapper.convertBillToResponse(bill);
    }

    // GET - Get a bill by ID
    public BillResponse getBillById(Long id) {
        User currentUser = currentUserService.getCurrentUser();
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill with id " + id + " not found."));
        if (!currentUser.getEmail().equals(bill.getUser().getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
        return billMapper.convertBillToResponse(bill);
    }

    // GET - Get all bills by User
    public List<BillResponse> getBillsByUser() {
        User user = currentUserService.getCurrentUser();
        List<Bill> bills = billRepository.getBillsByUser(user);
        return billMapper.convertBillListToResponse(bills);
    }

    // UPDATE - Update a bill
    public BillResponse updateBill(UpdateBillRequest request, Long id) {
        User user = currentUserService.getCurrentUser();
        Bill bill = getBillEntityById(id);
        if (!user.getEmail().equals(bill.getUser().getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
        bill.setName(request.getName());
        bill.setAmount(request.getAmount());
        bill.setDueDate(request.getDueDate());
        Bill updatedBill = billRepository.save(bill);
        return billMapper.convertBillToResponse(updatedBill);
    }

    // UPDATE - Mark a bill as paid
    public BillResponse markBillPaid(Long id) {
        User user = currentUserService.getCurrentUser();
        Bill bill = getBillEntityById(id);
        if (!user.getEmail().equals(bill.getUser().getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
        bill.setStatus(BillStatus.PAID);
        Bill updatedBill = billRepository.save(bill);
        return billMapper.convertBillToResponse(updatedBill);
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
    public String deleteBill(Long id) {
        User user = currentUserService.getCurrentUser();
        Bill bill = getBillEntityById(id);
        if (!user.getEmail().equals(bill.getUser().getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
        billRepository.delete(bill);
        return "Successfully deleted";
    }

    // Fetch Bill Entity (Private Helper Method)
    private Bill getBillEntityById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill with id " + id + " not found."));
        return bill;
    }

}
