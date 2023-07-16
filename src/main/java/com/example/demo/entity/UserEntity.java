package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Java数据和数据表的映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("User")

public class UserEntity implements Serializable{
    private int id;
    private String name;
    private String phone;
    private String password;
    private String gender;
    private String device_id;
    private int status;
    private String avatar;
    private String province;
    private String country;
    private String open_id;
    private String union_id;
    private String person_summary;
    private String real_name;
    private Timestamp create_time;
    private String id_card;

}
