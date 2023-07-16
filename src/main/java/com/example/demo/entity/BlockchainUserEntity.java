package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Java数据和数据表的映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("BlockchainUser")

public class BlockchainUserEntity {
    private String id;
    private String password;
    private String btc_addr;
    private String eth_addr;
    private String ltc_addr;
    private String bch_addr;
}
