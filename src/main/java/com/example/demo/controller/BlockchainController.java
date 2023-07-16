package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dto.AddWalletDto;
import com.example.demo.dto.GetAllNumDto;
import com.example.demo.dto.GetSingleNumDto;
import com.example.demo.dto.UpdateSingleNumDto;
import com.example.demo.entity.BlockchainUserEntity;
import com.example.demo.mapper.BlockchainUserMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@RestController
public class BlockchainController {

    @Autowired
    private BlockchainUserMapper blockchainUserMapper;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/addWallet")
    public String addWallet(AddWalletDto addWalletDto) {
        try {
            JSONObject json = new JSONObject();
            if(addWalletDto == null){
                return "操作失败";
            }
            if(addWalletDto.getId()==null || addWalletDto.getPassword()==null) {
                return "操作失败";
            }

            BlockchainUserEntity blockchainUserEntity = new BlockchainUserEntity();
            blockchainUserEntity.setId(addWalletDto.getId());
            blockchainUserEntity.setPassword(addWalletDto.getPassword());

            String url = "http://127.0.0.1:8001/wallet/new";
//            BTC
            String btcRes = restTemplate.getForObject(url, String.class);
            if(btcRes!=null) {
                int indexOfAddr = btcRes.indexOf("address");
                int indexOfPub = btcRes.indexOf("pubKey");
                String addr = btcRes.substring(indexOfAddr+10, indexOfPub-3);
                blockchainUserEntity.setBtc_addr(addr);
            }
            else {
                json.put("flag", 0);
                return json.toString();
            }
//            ETH
            String ethRes = restTemplate.getForObject(url, String.class);
            if(ethRes!=null) {
                int indexOfAddr = ethRes.indexOf("address");
                int indexOfPub = ethRes.indexOf("pubKey");
                String addr = ethRes.substring(indexOfAddr+10, indexOfPub-3);
                blockchainUserEntity.setEth_addr(addr);
            }
            else {
                json.put("flag", 0);
                return json.toString();
            }
//            LTC
            String ltcRes = restTemplate.getForObject(url, String.class);
            if(ltcRes!=null) {
                int indexOfAddr = ltcRes.indexOf("address");
                int indexOfPub = ltcRes.indexOf("pubKey");
                String addr = ltcRes.substring(indexOfAddr+10, indexOfPub-3);
                blockchainUserEntity.setLtc_addr(addr);
            }
            else {
                json.put("flag", 0);
                return json.toString();
            }

//            BCH
            String bchRes = restTemplate.getForObject(url, String.class);
            if(bchRes!=null) {
                int indexOfAddr = bchRes.indexOf("address");
                int indexOfPub = bchRes.indexOf("pubKey");
                String addr = bchRes.substring(indexOfAddr+10, indexOfPub-3);
                blockchainUserEntity.setBch_addr(addr);
            }
            else {
                json.put("flag", 0);
                return json.toString();
            }

            if(blockchainUserMapper.insert(blockchainUserEntity)==1) {
                json.put("flag", 1);
            }
            else {
                json.put("flag", 0);
            }
            return json.toString();

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @RequestMapping("/getSingleNum")
    public String getSingleNum(GetSingleNumDto getSingleNumDto) {
        try {
//            验证身份
            com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
            QueryWrapper<BlockchainUserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", getSingleNumDto.getId());
            queryWrapper.eq("password", getSingleNumDto.getPassword());
            List<BlockchainUserEntity> currentUser = blockchainUserMapper.selectList(queryWrapper);
            if(currentUser.isEmpty()) {
                json.put("flag",0);
                return json.toString();
            }
            else {
                BlockchainUserEntity user = currentUser.get(0);
                String symbol = getSingleNumDto.getSymbol();
                String addr = "";
                switch (symbol) {
                    case "BTC/USDT":
                        addr = user.getBtc_addr();
                        break;
                    case "ETH/USDT":
                        addr = user.getEth_addr();
                        break;
                    case "LTC/USDT":
                        addr = user.getLtc_addr();
                        break;
                    case "BCH/USDT":
                        addr = user.getBch_addr();
                        break;
                }
                String url = "http://127.0.0.1:8001/wallet/balance/" + addr;
                String res = restTemplate.getForObject(url, String.class);
                System.out.println(res);
                assert res != null;
                int dataBegin = res.indexOf("data");
                int okBegin = res.indexOf("ok");
                String balance = res.substring(dataBegin+6, okBegin-2);
                BigDecimal balanceDecimal = new BigDecimal(balance);
                json.put("num", balanceDecimal.divide(new BigDecimal(100)));
                json.put("flag", 1);
                return json.toString();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @RequestMapping("/getAllNum")
    public String getAllNum(GetAllNumDto getAllNumDto) {
        try {
            com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
            QueryWrapper<BlockchainUserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", getAllNumDto.getId());
            queryWrapper.eq("password", getAllNumDto.getPassword());
            List<BlockchainUserEntity> currentUser = blockchainUserMapper.selectList(queryWrapper);
            if(currentUser.isEmpty()) {
                json.put("flag",0);
                return json.toString();
            }
            else {
                BlockchainUserEntity user = currentUser.get(0);
                String btcAddr = user.getBtc_addr();
                String ethAddr = user.getEth_addr();
                String ltcAddr = user.getLtc_addr();
                String bchAddr = user.getBch_addr();
                String[] addrs = {btcAddr, ethAddr, ltcAddr, bchAddr};
                String[] symbols = {"btc", "eth", "ltc", "bch"};
                for (int i = 0; i < addrs.length; i++) {
                    String url = "http://127.0.0.1:8001/wallet/balance/" + addrs[i];
                    String res = restTemplate.getForObject(url, String.class);
                    assert res != null;
                    System.out.println(res);
                    int dataBegin = res.indexOf("data");
                    int okBegin = res.indexOf("ok");
                    String balance = res.substring(dataBegin+6, okBegin-2);
                    BigDecimal balanceDecimal = new BigDecimal(balance);
                    BigDecimal balanceFinal = balanceDecimal.divide(new BigDecimal(100));
                    json.put(symbols[i], balanceFinal);
                }
                json.put("flag", 1);
                return json.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/updateSingleNum")
    public String updateSingleNum(UpdateSingleNumDto updateSingleNumDto){
        try {
            com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
            QueryWrapper<BlockchainUserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", updateSingleNumDto.getId());
            queryWrapper.eq("password", updateSingleNumDto.getPassword());
            List<BlockchainUserEntity> currentUser = blockchainUserMapper.selectList(queryWrapper);
            if(currentUser.isEmpty()) {
                json.put("flag",0);
                return json.toString();
            }
            else {
                BlockchainUserEntity user = currentUser.get(0);
                String symbol = updateSingleNumDto.getSymbol();
                String addr = "";
                switch (symbol) {
                    case "BTC/USDT":
                        addr = user.getBtc_addr();
                        break;
                    case "ETH/USDT":
                        addr = user.getEth_addr();
                        break;
                    case "LTC/USDT":
                        addr = user.getLtc_addr();
                        break;
                    case "BCH/USDT":
                        addr = user.getBch_addr();
                        break;
                }
                String url = "http://127.0.0.1:8001/message/send";
                LinkedMultiValueMap<String, String> requst = new LinkedMultiValueMap<>();
                String value = new BigDecimal(updateSingleNumDto.getAmount()).multiply(new BigDecimal(100)).toString();
                requst.set("value", value);
                if (Objects.equals(updateSingleNumDto.getDirection(), "BUY")) {
                    requst.set("from", "0x393d683e49f17876155b4de24e957b79e36119fa");
                    requst.set("to", addr);
                    restTemplate.postForObject(url, requst, String.class);
                } else if (Objects.equals(updateSingleNumDto.getDirection(), "SELL")) {
                    requst.set("to", "0x393d683e49f17876155b4de24e957b79e36119fa");
                    requst.set("from", addr);
                    restTemplate.postForObject(url, requst, String.class);
                }
                json.put("flag", 1);
                return json.toString();
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
