package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "exchange_trade_EOS/USDT")
public class EOS_CoinEntity {
    @Id //自动生成的主键ID 主键 不可重复 自带索引
    private String _id;
    private String symbol;
    private String price;
    private String amount;
    private String buyTurnover;
    private String sellTurnover;
    private String direction;
    private String buyOrderId;
    private String sellOrderId;
    private String time;
    private String increase;
}
