package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.dto.TradeDto;
import com.example.demo.dto.UpdateSingleNumDto;
import com.example.demo.entity.*;
import com.example.demo.mapper.AssetMapper;
import com.example.demo.mapper.OrderMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONException;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
public class TradeController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BlockchainController blockchainController;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AssetMapper assetMapper;

    @RequestMapping("/trade")
    public String trade(TradeDto tradeDto) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            UpdateSingleNumDto updateSingleNumDto = new UpdateSingleNumDto();
            updateSingleNumDto.setId(String.valueOf(tradeDto.getUser_id()));
            updateSingleNumDto.setSymbol(tradeDto.getSymbol());
            updateSingleNumDto.setPassword(tradeDto.getPassword());
            updateSingleNumDto.setAmount(String.valueOf(tradeDto.getAmount()));
            updateSingleNumDto.setDirection(tradeDto.getDirection());
            JSONObject res = JSONObject.parseObject(blockchainController.updateSingleNum(updateSingleNumDto));
            if(!res.get("flag").equals(1)) {
                json.put("msg", "fail");
                return json.toString();
            }

            double volume = tradeDto.getAmount() * tradeDto.getPrice();

            OrderEntity order = new OrderEntity();
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            order.setId(Integer.parseInt(RandomStringUtils.randomNumeric(6)));
            order.setTrade_time(ts);
            order.setTrade_price(tradeDto.getPrice());
            order.setUser_id(tradeDto.getUser_id());
            order.setDirection(tradeDto.getDirection());
            order.setSymbol(tradeDto.getSymbol());
            order.setTrade_amount(tradeDto.getAmount());
            Integer result1 = orderMapper.insert(order);

            QueryWrapper<AssetEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", tradeDto.getUser_id());
            List<AssetEntity> asset = assetMapper.selectList(queryWrapper);

            UpdateWrapper<AssetEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", tradeDto.getUser_id());
            if(Objects.equals(tradeDto.getDirection(), "BUY")) {
                updateWrapper.set("initial_asset", asset.get(0).getInitial_asset() - volume);
            }
            else {
                updateWrapper.set("initial_asset", asset.get(0).getInitial_asset() + volume);
            }
            Integer result2 = assetMapper.update(null,updateWrapper);

            if (result1 == 1 && result2 == 1) {
                json.put("code", "1");
                json.put("msg", "success");
                return json.toString();
            }
        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/get/buyRecord")
    public String getBuyRecord(String symbol) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            Sort sort = Sort.by(Sort.Direction.DESC,"time");
            Query query = new Query();
            query.limit(5);
            query.with(sort);
            query.addCriteria(Criteria.where("direction").is("BUY"));
            if(symbol == null) {
                return "操作失败";
            }
            List list = null;
            if(symbol.equals("BTC/USDT")) {
                list = mongoTemplate.find(query, BTC_CoinEntity.class);
            }
            if(symbol.equals("BCH/USDT")) {
                list = mongoTemplate.find(query, BCH_CoinEntity.class);
            }
            if(symbol.equals("ETH/USDT")) {
                list = mongoTemplate.find(query, ETH_CoinEntity.class);
            }
            if(symbol.equals("LTC/USDT")) {
                list = mongoTemplate.find(query, LTC_CoinEntity.class);
            }
            if(symbol.equals("XRP/USDT")) {
                list = mongoTemplate.find(query, XRP_CoinEntity.class);
            }
            if(symbol.equals("EOS/USDT")) {
                list = mongoTemplate.find(query, EOS_CoinEntity.class);
            }
            if(symbol.equals("DASH/USDT")) {
                list = mongoTemplate.find(query, DASH_CoinEntity.class);
            }
            if(list.isEmpty()) {
                json.put("msg","this coin no buy or sell record");
                return json.toString();
            }
            json.put("code","1");
            json.put("msg","success");
            json.put("data",list);
            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/get/sellRecord")
    public String getSellRecord(String symbol) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            Sort sort = Sort.by(Sort.Direction.DESC,"time");
            Query query = new Query();
            query.limit(5);
            query.with(sort);
            query.addCriteria(Criteria.where("direction").is("SELL"));
            if(symbol == null) {
                return "操作失败";
            }
            List list = null;
            if(symbol.equals("BTC/USDT")) {
                list = mongoTemplate.find(query, BTC_CoinEntity.class);
            }
            if(symbol.equals("BCH/USDT")) {
                list = mongoTemplate.find(query, BCH_CoinEntity.class);
            }
            if(symbol.equals("ETH/USDT")) {
                list = mongoTemplate.find(query, ETH_CoinEntity.class);
            }
            if(symbol.equals("LTC/USDT")) {
                list = mongoTemplate.find(query, LTC_CoinEntity.class);
            }
            if(symbol.equals("XRP/USDT")) {
                list = mongoTemplate.find(query, XRP_CoinEntity.class);
            }
            if(symbol.equals("EOS/USDT")) {
                list = mongoTemplate.find(query, EOS_CoinEntity.class);
            }
            if(symbol.equals("DASH/USDT")) {
                list = mongoTemplate.find(query, DASH_CoinEntity.class);
            }
            if(list.isEmpty()) {
                json.put("msg","this coin no buy or sell record");
                return json.toString();
            }
            json.put("code","1");
            json.put("msg","success");
            json.put("data",list);
            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }
}
