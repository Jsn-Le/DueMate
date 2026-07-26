package com.duemate.duemate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBillRequest {

    private String name;
    private BigDecimal amount;
    private LocalDate dueDate; 

}
