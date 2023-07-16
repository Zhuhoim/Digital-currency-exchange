package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.CoinEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoinMapper extends BaseMapper<CoinEntity> {
}
