package com.iflytek.bank.controller;


import com.alibaba.fastjson.JSONObject;
import com.iflytek.bank.pojo.BranchInfoPojo;
import com.iflytek.bank.pojo.standard.ResponseResult;
import com.iflytek.bank.service.BankQueryService;
import com.iflytek.bank.utils.JsonUtil;
import com.iflytek.bank.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/query")
public class BankQueryController {
    private Logger logger = LoggerFactory.getLogger(BankQueryController.class);

    @Autowired
    private BankQueryService bankQueryService;


    @RequestMapping(name = "加载网点详情excel入内存", value = "/branchReload", method = RequestMethod.POST, produces = "application/json;charset=utf-8", consumes = "application/json")
    @ResponseBody
    public ResponseResult branchReload() throws IOException {
        JSONObject jsonObject =  bankQueryService.getExcelToMem();
        return ResponseResult.getResult(jsonObject);
    }

    @RequestMapping(name = "支行网点信息查询", value = "/branchInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8", consumes = "application/json")
    @ResponseBody
    public ResponseResult fuzzymatch(@RequestBody String input) throws IOException {
        logger.info("请求参数为：{}",input);
        JSONObject inputObj = JSONObject.parseObject(input);
        if(null !=inputObj &&!StringUtil.isNullOrEmpry(inputObj.getString("branchNamDesc"))){
            JSONObject jsonObject =  bankQueryService.getBranchInfo(inputObj.getString("branchNamDesc"));
            return ResponseResult.getResult(jsonObject);

        }else{
            return ResponseResult.error("入参为空，不允许查询！");
        }

    }










}
