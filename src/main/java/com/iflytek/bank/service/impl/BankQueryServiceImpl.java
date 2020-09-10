package com.iflytek.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.iflytek.bank.configuration.BranchInfoInit;
import com.iflytek.bank.configuration.ExcelPathConfig;
import com.iflytek.bank.service.BankQueryService;
import com.iflytek.bank.utils.CommonUtils;
import com.iflytek.bank.utils.ConstantsUtil;
import com.iflytek.bank.utils.ExcelUtil;
import com.iflytek.bank.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class BankQueryServiceImpl implements BankQueryService {
    private Logger logger = LoggerFactory.getLogger(BankQueryServiceImpl.class);
    @Autowired
    ExcelPathConfig excelPathConfig;
    @Autowired
    CommonUtils commonUtils;
    @Autowired
    ExcelUtil excelUtil;
    @Autowired
    BranchInfoInit branchInfoInit;
    /**
     * 加载excel到内存
     * @return
     * @throws IOException
     */
    @Override
    public JSONObject getExcelToMem() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",ConstantsUtil.SUCCESS);
        jsonObject.put("message",ConstantsUtil.SUCCESS_MESSAGE);

        String excelFilePath =excelPathConfig.getBranchFilePath();
        excelFilePath = URLDecoder.decode(excelFilePath, "UTF-8");

        if(!StringUtil.isNullOrEmpry(excelFilePath)){
            logger.info("银行网点信息excel路径及名称为：{}",excelFilePath);
            ArrayList<Map<String, String>> excelInfo =excelUtil.readExcelToObj(excelFilePath);
            Map<String,Map<String,Object>> excelInfoMap = new HashMap<>();
            Map<String,Object> excelRowInfo = new HashMap<>();
            if(null!=excelInfo){
                Map<String,Map<String,String>> branchMap = commonUtils.HandleExcelData(excelInfo);
                branchInfoInit.setBranchInfoMap(branchMap);
                logger.info("初始化银行网点名称信息成功,接口返回参数：{}",jsonObject.toJSONString());
                return jsonObject;
            }else{
                logger.info("解析网点信息excel异常,请检查");
                jsonObject.put("code",ConstantsUtil.ERROR);
                jsonObject.put("message","解析网点信息excel异常,请检查");
                return jsonObject;
            }

        }else{
            logger.error("银行网点信息excel对应文件路径为空,请检查");
            jsonObject.put("code",ConstantsUtil.ERROR);
            jsonObject.put("message","银行网点信息excel对应文件路径为空,请检查");
            return jsonObject;
        }
    }

    /**
     * 根据网点说法，查询支行网点详情
     * @param branchNamDesc
     * @return
     * @throws IOException
     */
    @Override
    public JSONObject getBranchInfo(String branchNamDesc) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",ConstantsUtil.SUCCESS);
        jsonObject.put("message",ConstantsUtil.SUCCESS_MESSAGE);

        String queryKey = branchNamDesc ;
        Map<String,Map<String,String>> branchNameInfo = branchInfoInit.getBranchInfoInit();
        if(branchNameInfo.containsKey(queryKey)){
            logger.info("营业网点详情表格中存在待查询数据,待查询数据为：{}",queryKey);
            jsonObject.put("outParams",branchNameInfo.get(queryKey));
        }else{
            logger.info("营业网点详情表格中不存在待查询数据,待查询数据为：{},请检查",queryKey);
            jsonObject.put("code",ConstantsUtil.ERROR);
            jsonObject.put("message","营业网点详情表格中不存在待查询数据,请检查");
        }
        logger.info("查询支行信息接口返回参数：{}",jsonObject.toJSONString());
        return jsonObject;
    }
}
