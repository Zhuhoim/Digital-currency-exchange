package com.example.demo.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.OrderEntity;
import com.example.demo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/get/order")
    public String getOrder(int user_id) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",user_id);
            List<OrderEntity> order = orderMapper.selectList(queryWrapper);

            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", order);

            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/get/lastBuyRecord")
    public String getLastBuyRecord(int user_id, String symbol, String direction) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user_id);
            queryWrapper.eq("symbol", symbol);
            queryWrapper.eq("direction", direction);
            List<OrderEntity> order = orderMapper.selectList(queryWrapper);

            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", order);

            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/get/lastSellRecord")
    public String getLastSellRecord(int user_id, String symbol, String direction) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user_id);
            queryWrapper.eq("symbol", symbol);
            queryWrapper.eq("direction", direction);
            List<OrderEntity> order = orderMapper.selectList(queryWrapper);

            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", order);

            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }
}
