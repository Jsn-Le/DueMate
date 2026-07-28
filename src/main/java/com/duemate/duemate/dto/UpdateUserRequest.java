package com.duemate.duemate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {

    private String email;
    private String password;
    private String defaultCurrency;

}
