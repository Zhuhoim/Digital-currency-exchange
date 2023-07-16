package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Asset")
public class AssetEntity implements Serializable {
    private int id;
    private int user_id;
    private double initial_asset;
}
