package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Coin")
public class CoinEntity implements Serializable {
    @Id
    private int id;
    private int pair_id;
    private String symbol;
    private String chinese_name;
    private String english_name;
    private String publish_time;
    private String publish_num;
    private String turnover_num;
    private String market_value;
    private String introduction;
    private int market_value_rank;
    private String turnover_rate;
    private String market_value_rate;
}
