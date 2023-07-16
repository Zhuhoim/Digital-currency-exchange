package com.example.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class UserRegisterDto {
    private String name;
    private String phone;
    private String password;
    private String gender;
    private String province;
    private String country;
    private String real_name;
    private String id_card;

}
