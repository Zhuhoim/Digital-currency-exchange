package com.example.demo.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.AssetEntity;
import com.example.demo.mapper.AssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AssetController {
    @Autowired
    private AssetMapper assetMapper;

    @RequestMapping("/get/asset")
    public String getAsset(int user_id) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", "0");

            QueryWrapper<AssetEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user_id);
            List<AssetEntity> asset = assetMapper.selectList(queryWrapper);

            json.put("code", "1");
            json.put("msg", "success");
            json.put("data", asset.get(0));

            return json.toString();
        }
        catch (JSONException e) {

        }
        return null;
    }
}
