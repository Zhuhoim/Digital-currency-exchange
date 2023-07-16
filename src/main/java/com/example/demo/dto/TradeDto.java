package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TradeDto {
    private int user_id;
    private String password;
    private String symbol;
    private double amount;
    private double price;
    private String direction;
}
