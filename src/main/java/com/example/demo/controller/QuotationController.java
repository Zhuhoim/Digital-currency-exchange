package com.example.demo.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.CoinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.query.Query;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuotationController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CoinMapper coinMapper;

    @RequestMapping("/get/coins")
    public String getAllInfo(){
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            Sort sort = Sort.by(Direction.DESC,"time");  //Springboot2.2.1以上的版本不能再用new来构造Sort对象
            Query query = new Query();
            query.limit(1);
            query.with(sort);

            List<BCH_CoinEntity> bch_list = mongoTemplate.find(query, BCH_CoinEntity.class);
            List<BTC_CoinEntity> btc_list = mongoTemplate.find(query, BTC_CoinEntity.class);
            List<LTC_CoinEntity> ltc_list = mongoTemplate.find(query, LTC_CoinEntity.class);
            List<ETH_CoinEntity> eth_list = mongoTemplate.find(query, ETH_CoinEntity.class);
            List<XRP_CoinEntity> xrp_list = mongoTemplate.find(query, XRP_CoinEntity.class);
            List<EOS_CoinEntity> eos_list = mongoTemplate.find(query, EOS_CoinEntity.class);
            List<DASH_CoinEntity> dash_list = mongoTemplate.find(query, DASH_CoinEntity.class);
            List list = new ArrayList<>();
            list.add(bch_list.get(0));
            list.add(btc_list.get(0));
            list.add(ltc_list.get(0));
            list.add(eth_list.get(0));
            list.add(xrp_list.get(0));
            list.add(eos_list.get(0));
            list.add(dash_list.get(0));

            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", list);

            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }

    @RequestMapping("/get/coinDetail")
    public String getCoinDetail(String symbol) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<CoinEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("symbol", symbol);
            List<CoinEntity> coinDetail = coinMapper.selectList(queryWrapper);
            System.out.println(coinDetail);
            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", coinDetail.get(0));

            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }

}