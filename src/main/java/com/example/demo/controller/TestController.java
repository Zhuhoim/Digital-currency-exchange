package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.TestEntity;
import com.example.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/hello")
    public String test0(){
        QueryWrapper<TestEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "李红");
        List<TestEntity> people = testMapper.selectList(queryWrapper);
        System.out.println(people.get(0).getAge());
        return people.toString();
    }

    @RequestMapping(value = "/bye")
    public List<TestEntity> test1(){
        List<TestEntity> people = testMapper.selectList(null);
        System.out.println(people.get(0).getAge());
        return people;
    }
}
