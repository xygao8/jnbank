package com.iflytek.bank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommonUtils {
    private Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 从指定的excel中读取内容写入内存
     * @param excelFilePath
     * @return
     */
//    public JSONObject readExcelInfo(String excelFilePath){
//        JSONObject excelInfo = new JSONObject();  //将读到的excel表信息放在excelInfo中
//        try {
//            File excel = new File(excelFilePath);
//            if (excel.isFile() && excel.exists()) {   //判断文件是否存在
//                logger.info("excel文件存在，开始解析文件名");
//
//                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
//                Workbook wb;
//                //根据文件后缀（xls/xlsx）进行判断
//                if ( "xls".equals(split[split.length-1])){
//                    logger.info("文件格式为：xls");
//                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
//                    wb = new HSSFWorkbook(fis);
//                }else if ("xlsx".equals(split[split.length-1])){
//                    logger.info("文件格式为：xlsx");
//                    wb = new XSSFWorkbook(excel);
//                }else {
//                    logger.error("文件类型错误");
//                    return null;
//                }
//
//                //开始解析
//                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
//
//                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
//                int lastRowIndex = sheet.getLastRowNum();
//                logger.info("文件总行数为：{}，第一行不计数，有效数据起始行为：{}",lastRowIndex+1,firstRowIndex+1);
//
//                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
//                    logger.debug("开始解析第{}行: " , rIndex );
//                    Row row = sheet.getRow(rIndex);
//                    if (row != null) {
//                        int firstCellIndex = row.getFirstCellNum();
//                        int lastCellIndex = row.getLastCellNum();
//                        //存储每一行的所有列信息
//                        JSONObject cellInfo =new JSONObject();
//                        for(int i = firstCellIndex; i <=lastCellIndex; i++){
//                            logger.debug("开始解析第{}行，第{}列",rIndex,i);
//                            Cell cell = row.getCell(i);
//                            if (cell != null) {
//                                cell.setCellType(CellType.STRING);
//                                cellInfo.put("cell"+i,cell.toString());
//                            }
//                        }
//                        excelInfo.put(row.getCell(2).toString(),cellInfo);
//                    }
//                }
//                logger.debug("整合后的行数据为：{}",excelInfo.toJSONString());
//                return excelInfo;
//
//            } else {
//                logger.error("找不到指定的文件");
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("读取网点信息excel程序异常");
//            return null;
//        }
//    }


    /**
     *
     * @param excelInfoList
     * @return
     */
    public Map<String,Map<String,String>> HandleExcelData(ArrayList<Map<String, String>> excelInfoList){
        Map<String,Map<String,String>> excelInfoMap = new HashMap<>();
        if(null!=excelInfoList) {
            for (int i = 0; i < excelInfoList.size(); i++) {
                Map<String, String> excelRowNew = excelInfoList.get(i);
                String branchNameDesc=excelRowNew.get("branchNameDesc");
                logger.debug("当前行数据: 网点名称和说法：{}",branchNameDesc);
                String[] branchNameDescStr = branchNameDesc.split("\\/");
                if(branchNameDescStr.length>0){
                    for(int j = 0;j<branchNameDescStr.length;j++){
                        String queryKey =branchNameDescStr[j];
                        if (excelInfoMap.containsKey(queryKey)) {
                            logger.debug("网点名称和说法key【{}】已存在,此数据无需处理",queryKey);
                        }else{
                            logger.debug("网点名称和说法key【{}】不存在,新增全量数据",queryKey);
                            excelInfoMap.put(queryKey,excelRowNew);
                        }
                    }
                }
            }
        }
        logger.info("读取到的表格数据，整合后为：{}",excelInfoMap.toString());
        return excelInfoMap;
    }


}
