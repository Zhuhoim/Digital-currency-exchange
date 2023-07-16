package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("OrderList")
public class OrderEntity implements Serializable {
    private int id;
    private int user_id;
    private String symbol;
    private String direction;
    private double trade_amount;
    private double trade_price;
    private Timestamp trade_time;
}
