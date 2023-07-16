package com.example.demo.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.OptionalCoinEntity;
import com.example.demo.mapper.OptionalCoinMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@RestController
public class OptionalCoinController {
    @Autowired
    private OptionalCoinMapper optionalCoinMapper;

    @RequestMapping("/insert/optionalCoin")
    public String insertOptionalCoin(int user_id, int pair_id, String symbol) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            OptionalCoinEntity optionalCoin = new OptionalCoinEntity();
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            optionalCoin.setId(Integer.parseInt(RandomStringUtils.randomNumeric(6)));
            optionalCoin.setUser_id(user_id);
            optionalCoin.setPair_id(pair_id);
            optionalCoin.setSymbol(symbol);
            optionalCoin.setCreate_time(ts);

            QueryWrapper<OptionalCoinEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("symbol", optionalCoin.getSymbol());
            queryWrapper.eq("user_id", optionalCoin.getUser_id());
            List<OptionalCoinEntity> list = optionalCoinMapper.selectList(queryWrapper);
            if (!list.isEmpty()) {
                json.put("code", "5000");
                json.put("msg", "this coin is existed");
                return json.toString();
            }

            Integer result = optionalCoinMapper.insert(optionalCoin);
            if (result == 1) {
                json.put("code", "1");
                json.put("msg", "success");
            } else {
                json.put("code", "0");
                json.put("msg", "false");
                json.put("data", "insert errors");
            }
            return json.toString();
        } catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/get/optionalCoin")
    public String getOptionalCoin(int user_id) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<OptionalCoinEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user_id);
            List<OptionalCoinEntity> list = optionalCoinMapper.selectList(queryWrapper);
            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", list);
            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/delete/optionalCoin")
    public String deleteOptionalCoin(int user_id, String symbol) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<OptionalCoinEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user_id);
            queryWrapper.eq("symbol", symbol);
            Integer result = optionalCoinMapper.delete(queryWrapper);
            if (result == 1) {
                json.put("code", "1");
                json.put("msg", "success");
            } else {
                json.put("code", "0");
                json.put("msg", "false");
                json.put("data", "delete errors");
            }
            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }
}
