package com.duemate.duemate.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.duemate.duemate.dto.BillResponse;
import com.duemate.duemate.dto.CreateBillRequest;
import com.duemate.duemate.model.Bill;

@Component
public class BillMapper {

    public Bill convertRequestToBill(CreateBillRequest request) {
        Bill bill = new Bill();
        bill.setName(request.getName());
        bill.setAmount(request.getAmount());
        bill.setDueDate(request.getDueDate());

        return bill;
    }

    public BillResponse convertBillToResponse(Bill bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setName(bill.getName());
        response.setAmount(bill.getAmount());
        response.setDueDate(bill.getDueDate());
        response.setStatus(bill.getStatus());

        return response;
    }

    public List<BillResponse> convertBillListToResponse(List<Bill> bills) {
        List<BillResponse> responseList = new ArrayList<>();
        for (Bill bill : bills) {
            responseList.add(convertBillToResponse(bill));
        }

        return responseList;
    }

}
