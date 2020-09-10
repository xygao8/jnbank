package com.iflytek.bank.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="bank.config")
@PropertySource("classpath:application.yml")
public class ExcelPathConfig {
    /**
     * 银行网点对应excel文件放置路径
     */
    String branchFilePath;

    public String getBranchFilePath() {
        return branchFilePath;
    }
    public void setBranchFilePath(String branchFilePath) {
        this.branchFilePath = branchFilePath;
    }



}
