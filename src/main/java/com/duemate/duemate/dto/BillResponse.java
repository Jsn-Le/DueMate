package com.duemate.duemate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.duemate.duemate.model.BillStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillResponse {

    private Long id;
    private String name;
    private BigDecimal amount;
    private LocalDate dueDate;
    private BillStatus status;

}
