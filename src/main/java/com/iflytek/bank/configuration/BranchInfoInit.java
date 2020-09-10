package com.iflytek.bank.configuration;


import com.iflytek.bank.utils.CommonUtils;
import com.iflytek.bank.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class BranchInfoInit {

    public static Logger logger = LoggerFactory.getLogger(BranchInfoInit.class);
    public static Map<String,Map<String,String>> branchInfoMap = null;
    @Autowired
    ExcelPathConfig excelPathConfig;
    @Autowired
    ExcelUtil excelUtil;
    @Autowired
    CommonUtils commonUtils;
    /**
     * 构造方法
     */
    private BranchInfoInit() {}

    /**
     * 初始化营业网点信息内存数据
     * @return
     */
    public Map<String,Map<String,String>> getBranchInfoInit () {
        if (null != branchInfoMap) {
            logger.info("营业网点信息有数据，使用已有内存数据");
            return branchInfoMap;
        } else {
            logger.info("营业网点信息无数据，开始重新初始化");
            String excelFilePath =excelPathConfig.getBranchFilePath();
            ArrayList<Map<String, String>> excelInfo = excelUtil.readExcelToObj(excelFilePath);
            branchInfoMap = commonUtils.HandleExcelData(excelInfo);
            return branchInfoMap;
        }
    }
    public void setBranchInfoMap(Map<String,Map<String,String>> branchInfoMap){
        this.branchInfoMap =branchInfoMap;
    }

}
