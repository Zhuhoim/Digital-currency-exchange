package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.dto.*;
import com.example.demo.entity.AssetEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.AssetMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.TokenUtil;
import org.apache.commons.lang3.RandomStringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AssetMapper assetMapper;
    @Autowired
    private BlockchainController blockchainController;

    @RequestMapping("/register")
    public String register(UserRegisterDto userRegisterDto){
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            if(userRegisterDto ==null){
                return "操作失败";
            }
            if(userRegisterDto.getName()==null|| userRegisterDto.getPassword()==null|| userRegisterDto.getPhone()==null || userRegisterDto.getGender()==null || userRegisterDto.getCountry()==null ||
                    userRegisterDto.getProvince()==null || userRegisterDto.getReal_name()==null || userRegisterDto.getId_card()== null){
                return "操作失败";
            }

            UserEntity user = new UserEntity();
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            user.setName(userRegisterDto.getName());
            user.setPassword(userRegisterDto.getPassword());
            user.setCountry(userRegisterDto.getCountry());
            user.setGender(userRegisterDto.getGender());
            user.setPhone(userRegisterDto.getPhone());
            user.setId(Integer.parseInt(RandomStringUtils.randomNumeric(6)));
            user.setProvince(userRegisterDto.getProvince());
            user.setReal_name(userRegisterDto.getReal_name());
            user.setAvatar("empty");
            user.setCreate_time(ts);
            user.setDevice_id("empty");
            user.setOpen_id("empty");
            user.setPerson_summary("empty");
            user.setStatus(1);
            user.setUnion_id("empty");
            user.setId_card(userRegisterDto.getId_card());

            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", user.getName());
            List<UserEntity> list = userMapper.selectList(queryWrapper);
            if(!list.isEmpty()) {
                json.put("code","5000");
                json.put("msg","username is existed");
                return json.toString();
            }

            AddWalletDto addWalletDto = new AddWalletDto();
            addWalletDto.setId(String.valueOf(user.getId()));
            addWalletDto.setPassword(user.getPassword());
            JSONObject res = JSONObject.parseObject(blockchainController.addWallet(addWalletDto));
            json.put("test", res);
            if(!res.get("flag").equals(1)) {
                json.put("msg", "fail");
                return json.toString();
            }

            AssetEntity asset = new AssetEntity();
            asset.setId(Integer.parseInt(RandomStringUtils.randomNumeric(6)));
            asset.setUser_id(user.getId());
            asset.setInitial_asset(10000);

            Integer result = userMapper.insert(user);
            Integer result2 = assetMapper.insert(asset);
            if(result == 1 && result2 == 1) {
                json.put("code", "1");
                json.put("msg", "success");
            }
            else {
                json.put("code", "0");
                json.put("msg", "false");
                json.put("data", "insert errors");
            }
            return json.toString();

        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/login")
    public String login(UserLoginDto userLoginDto) {
        try {
            JSONObject json = new JSONObject();
            json.put("code","0");
            if (userLoginDto==null||userLoginDto.getPassword()==null||userLoginDto.getName()==null) {
                json.put("msg","username or password is empty");
                return json.toString();
            }
            UserEntity user = new UserEntity();
            user.setName(userLoginDto.getName());
            user.setPassword(userLoginDto.getPassword());
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", user.getName());
            queryWrapper.eq("password", user.getPassword());
            List<UserEntity> currentUser = userMapper.selectList(queryWrapper);
            System.out.println(currentUser);
            if(currentUser.isEmpty()) {
                json.put("msg","username or password is wrong");
                return json.toString();
            }
            String token = TokenUtil.generateToken(user);
            json.put("code","1");
            json.put("msg","success");
            json.put("user_id", currentUser.get(0).getId());
            json.put("password", currentUser.get(0).getPassword());
            json.put("token",token);
            return  json.toString();
        }
        catch (JSONException e) {
        }
        return null;
    }

    @RequestMapping("/forgotPwd")
    public String forgotPwd(UserForgotPwdDto userForgotPwdDto) {
        try {
            JSONObject json = new JSONObject();
            json.put("code","0");
            if (userForgotPwdDto==null||userForgotPwdDto.getReal_name()==null||userForgotPwdDto.getId_card()==null) {
                json.put("msg","Real_Name or Id_Card is empty");
                return json.toString();
            }
            UserEntity user = new UserEntity();
            user.setReal_name(userForgotPwdDto.getReal_name());
            user.setId_card(userForgotPwdDto.getId_card());
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("real_name", user.getReal_name());
            queryWrapper.eq("id_card", user.getId_card());
            List<UserEntity> currentUser = userMapper.selectList(queryWrapper);
            if(currentUser.isEmpty()) {
                json.put("msg","real_name or id_card is wrong");
                return json.toString();
            }
            json.put("code","1");
            json.put("msg","success");
            json.put("password", currentUser.get(0).getPassword());
            return  json.toString();
        }
        catch (JSONException e) {
        }
        return null;
    }

    @RequestMapping("/updateUser")
    public String updateUser(UserUpdateDto userUpdateDto) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            if(userUpdateDto ==null){
                return "操作失败";
            }
            if(userUpdateDto.getId() == 0){
                return "操作失败";
            }

            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", userUpdateDto.getName());
            List<UserEntity> list = userMapper.selectList(queryWrapper);
            if(!list.isEmpty() && list.get(0).getId() != userUpdateDto.getId()) {
                json.put("code","5000");
                json.put("msg","username is existed");
                return json.toString();
            }

            UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", userUpdateDto.getId());
            updateWrapper.set("name", userUpdateDto.getName());
            updateWrapper.set("password", userUpdateDto.getPassword());
            updateWrapper.set("country", userUpdateDto.getCountry());
            updateWrapper.set("province", userUpdateDto.getProvince());
            updateWrapper.set("gender", userUpdateDto.getGender());
            updateWrapper.set("phone", userUpdateDto.getPhone());
            updateWrapper.set("id_card", userUpdateDto.getId_card());
            updateWrapper.set("real_name", userUpdateDto.getReal_name());
            Integer result = userMapper.update(null, updateWrapper);

            if(result == 1) {
                json.put("code", "1");
                json.put("msg", "success");
            }
            else {
                json.put("code", "0");
                json.put("msg", "false");
                json.put("data", "update errors");
            }
            return json.toString();

        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/getUser")
    public String getUser(int id) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            List<UserEntity> list = userMapper.selectList(queryWrapper);

            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", list.get(0));

            return json.toString();

        }
        catch (JSONException e) {

        }
        return null;
    }

}
