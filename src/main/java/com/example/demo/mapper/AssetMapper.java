package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.AssetEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetMapper extends BaseMapper<AssetEntity> {
}
