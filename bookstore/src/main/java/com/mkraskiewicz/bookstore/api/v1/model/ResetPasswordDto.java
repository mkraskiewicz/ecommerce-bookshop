package com.mkraskiewicz.bookstore.api.v1.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResetPasswordDto {

    private String token;
    private String password;
}
