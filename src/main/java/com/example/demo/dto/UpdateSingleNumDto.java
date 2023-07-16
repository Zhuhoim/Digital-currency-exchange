package com.example.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateSingleNumDto {
    private String symbol;
    private String amount;
    private String direction;
    private String id;
    private String password;
}
