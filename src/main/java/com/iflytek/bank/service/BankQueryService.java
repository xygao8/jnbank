package com.iflytek.bank.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public interface BankQueryService {

    /**
     * 加载excel内容到内存
     * @return
     * @throws IOException
     */
    public JSONObject getExcelToMem() throws IOException;

    /**
     * 根据网点说法查询支行详情
     * @param branchNamDesc
     * @return
     * @throws IOException
     */
    public JSONObject getBranchInfo(String branchNamDesc) throws IOException;
}
