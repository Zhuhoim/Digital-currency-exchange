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
@TableName("OptionalCoin")
public class OptionalCoinEntity implements Serializable {
    private int id;
    private int user_id;
    private int pair_id;
    private String symbol;
    private Timestamp create_time;
}
