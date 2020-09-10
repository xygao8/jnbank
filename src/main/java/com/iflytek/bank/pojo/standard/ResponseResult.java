package com.iflytek.bank.pojo.standard;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.logging.Logger;

public class ResponseResult implements Serializable{


    private int code = 0;
    private Object outParams;
    private String message;

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() { return code; }

    public void setCode(int code) { this.code = code; }

    public Object getOutParams() { return outParams; }

    public void setOutParams(Object outParams) { this.outParams = outParams; }

    public static ResponseResult success() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(0);
        return responseResult;
    }

    public static ResponseResult success(String message) {
        ResponseResult responseResult = success();
        responseResult.setMessage(message);
        return responseResult;
    }
    
    public static ResponseResult success(Object data, String message) {
        ResponseResult responseResult = success();
        responseResult.setOutParams(data);
        responseResult.setMessage(message);
        return responseResult;
    }

    public  ResponseResult success(Object data, String message,String str) {
        ResponseResult responseResult = success();
        responseResult.setOutParams(data);
        responseResult.setMessage(message);
        return responseResult;
    }




    public static ResponseResult error() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(1);
        return responseResult;
    }

    public static ResponseResult error(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(1);
        responseResult.setMessage(message);
        return responseResult;
    }

    public  ResponseResult error(String message,String str) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(1);
        responseResult.setMessage(message);
        return responseResult;
    }

    public  static ResponseResult error(Object data, String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(1);
        responseResult.setMessage(message);
        responseResult.setOutParams(data);
        return responseResult;
    }


    public static ResponseResult getResult(JSONObject data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(data.getInteger("code"));
        responseResult.setMessage(data.getString("message"));
        data.remove("code");
        data.remove("message");
        if(data.containsKey("outParams")){
            responseResult.setOutParams(data.get("outParams"));
        }else{
            responseResult.setOutParams(data);
        }

        return responseResult;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + 0 +
                ", outParams=" + outParams +
                ", message='" + message + '\'' +
                '}';
    }


}
